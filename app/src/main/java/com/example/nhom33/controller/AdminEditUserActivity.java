package com.example.nhom33.controller;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.nhom33.Database.FoodDB;
import com.example.nhom33.DataEntity.UsersEntity;
import com.example.nhom33.R;
import com.google.android.material.button.MaterialButton;

public class AdminEditUserActivity extends AppCompatActivity {

    private EditText etFullName, etEmail, etPhone, etAddress;
    private MaterialButton btnSave;
    private FoodDB db;
    private int userId;
    private UsersEntity user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_edit_user);

        // Ánh xạ
        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAddress);
        btnSave = findViewById(R.id.btnSave);
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        db = FoodDB.getInstance(this);

        // Lấy userId từ Intent
        userId = getIntent().getIntExtra("USER_ID", -1);
        if (userId != -1) {
            loadUserData();
        } else {
            Toast.makeText(this, "Không tìm thấy người dùng", Toast.LENGTH_SHORT).show();
            finish();
        }

        btnSave.setOnClickListener(v -> saveUserChanges());
    }

    private void loadUserData() {
        new Thread(() -> {
            user = db.usersDAO().getUserById(userId);
            if (user != null) {
                runOnUiThread(() -> {
                    etFullName.setText(user.getFullName());
                    etEmail.setText(user.getEmail());
                    etPhone.setText(user.getPhone());
                    etAddress.setText(user.getAddress());
                });
            }
        }).start();
    }

    private void saveUserChanges() {
        String fullName = etFullName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String address = etAddress.getText().toString().trim();

        if (fullName.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đủ Tên và Email", Toast.LENGTH_SHORT).show();
            return;
        }

        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhone(phone);
        user.setAddress(address);

        new Thread(() -> {
            db.usersDAO().updateUser(user);
            runOnUiThread(() -> {
                Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            });
        }).start();
    }
}
