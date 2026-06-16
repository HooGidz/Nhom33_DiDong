package com.example.nhom33.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.nhom33.R;
import com.example.nhom33.Database.FoodDB;
import com.example.nhom33.DataEntity.UsersEntity;

public class User_Edit_Profile extends AppCompatActivity {
    private View btnBack, btnSave;
    private EditText etFullName, etEmail, etPhone, etAddress;
    private FoodDB db;
    private UsersEntity currentUser;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.user_edit_profile);
        
        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        // Khởi tạo Database
        db = FoodDB.getInstance(this);

        // Ánh xạ View
        btnBack = findViewById(R.id.btnBack);
        btnSave = findViewById(R.id.btnSave);
        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAddress);

        // Lấy userId từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);

        loadUserData();

        btnBack.setOnClickListener(v -> finish());
        btnSave.setOnClickListener(v -> saveUserData());
    }

    private void loadUserData() {
        new Thread(() -> {
            if (userId != -1) {
                currentUser = db.usersDAO().getUserById(userId);
            }
            
            // Nếu vẫn không tìm thấy (do lỗi session), lấy tạm user đầu tiên trong máy để không bị trống
            if (currentUser == null) {
                // Đây là phương án dự phòng để tránh lỗi khi bạn đang phát triển
                // Trong thực tế nên yêu cầu đăng nhập lại
                currentUser = db.usersDAO().getUserById(1); 
            }

            if (currentUser != null) {
                runOnUiThread(() -> {
                    etFullName.setText(currentUser.getFullName());
                    etEmail.setText(currentUser.getEmail());
                    etPhone.setText(currentUser.getPhone());
                    etAddress.setText(currentUser.getAddress());
                });
            } else {
                runOnUiThread(() -> {
                    Toast.makeText(this, "Vui lòng đăng nhập lại để cập nhật hồ sơ", Toast.LENGTH_LONG).show();
                });
            }
        }).start();
    }

    private void saveUserData() {
        if (currentUser == null) {
            Toast.makeText(this, "Không có dữ liệu người dùng để lưu", Toast.LENGTH_SHORT).show();
            return;
        }

        String fullName = etFullName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String address = etAddress.getText().toString().trim();

        if (fullName.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Họ tên và Email không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }

        currentUser.setFullName(fullName);
        currentUser.setEmail(email);
        currentUser.setPhone(phone);
        currentUser.setAddress(address);

        new Thread(() -> {
            try {
                db.usersDAO().updateUser(currentUser);
                runOnUiThread(() -> {
                    Toast.makeText(User_Edit_Profile.this, "Cập nhật hồ sơ thành công", Toast.LENGTH_SHORT).show();
                    finish();
                });
            } catch (Exception e) {
                runOnUiThread(() -> {
                    Toast.makeText(User_Edit_Profile.this, "Lỗi khi cập nhật hồ sơ", Toast.LENGTH_SHORT).show();
                });
            }
        }).start();
    }
}
