package com.example.nhom33.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom33.DAO.UsersDAO;
import com.example.nhom33.Database.FoodDB;
import com.example.nhom33.DataEntity.UsersEntity;
import com.example.nhom33.R;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FoodDB db;
    private EditText etUsername, etFullName, etEmail, etPhone, etAddress, etPassword, etConfirmPassword;
    private Button btnSignUp;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        mAuth = FirebaseAuth.getInstance();
        db = FoodDB.getInstance(this);

        etUsername = findViewById(R.id.etUsername);
        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAddress);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnBack = findViewById(R.id.btnback);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    private void signUp() {
        String username = etUsername.getText().toString().trim();
        String fullName = etFullName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        if (username.isEmpty() || fullName.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Mật khẩu xác nhận không khớp", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 3) {
            Toast.makeText(this, "Mật khẩu phải có ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
            return;
        }
        saveUserToDatabase(username, password, fullName, email, phone, address);

//        // Thực hiện đăng ký Firebase (giữ nguyên logic cũ nếu cần) và lưu vào SQLite
//        mAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, task -> {
//                    if (task.isSuccessful()) {
//                        saveUserToDatabase(username, password, fullName, email, phone, address);
//                    } else {
//                        Toast.makeText(SignUpActivity.this, "Đăng ký Firebase thất bại: " + task.getException().getMessage(),
//                                Toast.LENGTH_LONG).show();
//                        // Nếu Firebase lỗi vẫn cho phép lưu SQLite để test nếu muốn, hoặc dừng lại
//                    }
//                });
    }

    private void saveUserToDatabase(String username, String password, String fullName, String email, String phone, String address) {
        new Thread(() -> {
            String createdAt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(new Date());
            
            // role = 1: Khách hàng, status = 1: Hoạt động
            UsersEntity newUser = new UsersEntity(username, password, fullName, email, phone, address, 1, "", 1, createdAt);
            
            try {
                db.usersDAO().insertUser(newUser);
                runOnUiThread(() -> {
                    Toast.makeText(SignUpActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, Login.class);
                    intent.putExtra("email", email);
                    intent.putExtra("password", password);
                    startActivity(intent);
                    finish();
                });
            } catch (Exception e) {
                runOnUiThread(() -> {
                    Toast.makeText(SignUpActivity.this, "Lỗi lưu dữ liệu: Tên đăng nhập có thể đã tồn tại", Toast.LENGTH_LONG).show();
                });
            }
        }).start();
    }
}