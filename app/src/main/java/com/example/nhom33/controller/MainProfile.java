package com.example.nhom33.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.nhom33.R;
import com.example.nhom33.Database.FoodDB;
import com.example.nhom33.DataEntity.UsersEntity;

public class MainProfile extends AppCompatActivity {
    private View btnInfo, btnAddress, btnOrderHistory, btnCart, btnLogout, btnFavorite;
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
        if (btnInfo != null) {
            btnInfo.setOnClickListener(v -> {
                Intent intent = new Intent(MainProfile.this, EditProfile.class);
                startActivity(intent);
            });
        }

        // Địa chỉ
        btnAddress = findViewById(R.id.btn_address_container);
        if (btnAddress != null) {
            btnAddress.setOnClickListener(v -> {
                Intent intent = new Intent(MainProfile.this, AddressActivity.class);
                startActivity(intent);
            });
        }

        // Yêu thích (Món ăn > 4 sao)
        btnFavorite = findViewById(R.id.btn_favorite_container);
        if (btnFavorite != null) {
            btnFavorite.setOnClickListener(v -> {
                Intent intent = new Intent(MainProfile.this, DanhSachBurgerActivity.class);
                intent.putExtra("FILTER_HIGH_RATING", true);
                startActivity(intent);
            });
        }

        // Giỏ hàng
        btnCart = findViewById(R.id.btn_cart_container);
        if (btnCart != null) {
            btnCart.setOnClickListener(v -> {
                Intent intent = new Intent(MainProfile.this, Cart.class);
                startActivity(intent);
            });
        }

        // Lịch sử giao hàng
        btnOrderHistory = findViewById(R.id.btn_order_history_container);
        if (btnOrderHistory != null) {
            btnOrderHistory.setOnClickListener(v -> {
                Intent intent = new Intent(MainProfile.this, MainOnOrder.class);
                startActivity(intent);
            });
        }

        // Đăng xuất
        btnLogout = findViewById(R.id.btn_logout);
        if (btnLogout != null) {
            btnLogout.setOnClickListener(v -> logoutUser());
        }

        // Nút quay lại
        CardView btn_back = findViewById(R.id.btn_back);
        if (btn_back != null) {
            btn_back.setOnClickListener(v -> finish());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Cập nhật lại thông tin nếu vừa mới sửa ở trang EditProfile
        loadUserProfile();
    }

    private void loadUserProfile() {
        new Thread(() -> {
            UsersEntity user = null;
            if (userId != -1) {
                user = db.usersDAO().getUserById(userId);
            }

            // Dự phòng nếu chưa đăng nhập hoặc session lỗi (Lấy user mặc định id=1)
            if (user == null) {
                user = db.usersDAO().getUserById(1);
            }

            if (user != null) {
                final String name = user.getFullName();
                final String address = user.getAddress();
                runOnUiThread(() -> {
                    if (txtUsername != null) txtUsername.setText(name);
                    if (txtBio != null && address != null && !address.isEmpty()) {
                        txtBio.setText(address);
                    }
                });
            }
        }).start();
    }

    private void logoutUser() {
        // Xóa thông tin đăng nhập trong SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Toast.makeText(this, "Đã đăng xuất", Toast.LENGTH_SHORT).show();

        // Chuyển về màn hình đăng nhập và xóa stack các activity trước đó
        Intent intent = new Intent(MainProfile.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}