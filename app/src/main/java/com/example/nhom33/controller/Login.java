package com.example.nhom33.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom33.R;
import com.example.nhom33.Database.FoodDB;
import com.example.nhom33.DataEntity.UsersEntity;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText edtEmail, edtPassword;
    private Button btnLogin;
    private TextView txtSignUp;
    private FoodDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        db = FoodDB.getInstance(this);
        mAuth = FirebaseAuth.getInstance();

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtSignUp = findViewById(R.id.txtSignUp);

        btnLogin.setOnClickListener(v -> {
            loginUser();
        });
        Intent intent = getIntent();
        if (intent!=null) {
            Bundle ex = intent.getExtras();
            if (ex!=null){
                edtEmail.setText(ex.getString("email"));
                edtPassword.setText(ex.getString("password"));
            }}

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loginUser() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra đăng nhập với database local
        UsersEntity user = db.usersDAO().getUserByEmailAndPassword(email, password);
        if (user != null) {
            // Lưu userId vào SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("userId", user.getUserId());
            editor.apply();

            Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
            
            Intent intent;
            if ("merchant".equalsIgnoreCase(user.getRole())) {
                // Nếu là merchant thì vào trang admin
                intent = new Intent(Login.this, Admin_Dashboard.class);
            } else {
                // Nếu là customer hoặc các role khác thì vào trang người dùng
                intent = new Intent(Login.this, TrangChuActivity.class);
            }

            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Sai email hoặc mật khẩu", Toast.LENGTH_SHORT).show();
        }
    }
}