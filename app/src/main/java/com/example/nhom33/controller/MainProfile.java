package com.example.nhom33.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.nhom33.R;
import com.example.nhom33.Database.FoodDB;
import com.example.nhom33.DataEntity.UsersEntity;

public class MainProfile extends AppCompatActivity {
    private View btnInfo, btnAddress, btnOrderHistory, btnFavorite;
    private TextView txtUsername, txtBio;
    private FoodDB db;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        db = FoodDB.getInstance(this);
        txtUsername = findViewById(R.id.txt_username);
        txtBio = findViewById(R.id.txt_bio);

        // Lấy userId từ phiên đăng nhập
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);

        loadUserProfile();

        // Thông tin cá nhân
        btnInfo = findViewById(R.id.btn_info_container);
        btnInfo.setOnClickListener(v -> {
            Intent intent = new Intent(MainProfile.this, EditProfile.class);
            startActivity(intent);
        });

        // Địa chỉ
        btnAddress = findViewById(R.id.btn_address_container);
        btnAddress.setOnClickListener(v -> {
            Intent intent = new Intent(MainProfile.this, AddressActivity.class);
            startActivity(intent);
        });

        // Yêu thích (Món ăn > 4 sao)
        btnFavorite = findViewById(R.id.btn_favorite_container);
        if (btnFavorite != null) {
            btnFavorite.setOnClickListener(v -> {
                Intent intent = new Intent(MainProfile.this, DanhSachBurgerActivity.class);
                intent.putExtra("FILTER_HIGH_RATING", true);
                startActivity(intent);
            });
        }

        // Lịch sử giao hàng
        btnOrderHistory = findViewById(R.id.btn_order_history_container);
        btnOrderHistory.setOnClickListener(v -> {
            Intent intent = new Intent(MainProfile.this, MainOnOrder.class);
            startActivity(intent);
        });

        CardView btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(v -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Cập nhật lại tên nếu vừa mới sửa ở trang EditProfile
        loadUserProfile();
    }

    private void loadUserProfile() {
        new Thread(() -> {
            UsersEntity user = null;
            if (userId != -1) {
                user = db.usersDAO().getUserById(userId);
            }
            
            // Dự phòng nếu chưa đăng nhập hoặc session lỗi
            if (user == null) {
                user = db.usersDAO().getUserById(1);
            }

            if (user != null) {
                final String name = user.getFullName();
                final String address = user.getAddress();
                runOnUiThread(() -> {
                    txtUsername.setText(name);
                    if (address != null && !address.isEmpty()) {
                        txtBio.setText(address);
                    }
                });
            }
        }).start();
    }
}
