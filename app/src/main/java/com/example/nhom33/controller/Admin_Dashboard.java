package com.example.nhom33.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.nhom33.R;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.navigation.NavigationView;

public class Admin_Dashboard extends AppCompatActivity {

    View btn_MenuHome;
    View btnXemTatCaReview;
    MaterialCardView card_running_orders, card_delivery_orders, card_review, card_revenue;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.admin_dashboard);

        // Thiết lập padding cho system bars (Status bar, Navigation bar)
        View mainView = findViewById(R.id.drawer_layout);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        // Ánh xạ các View từ XML
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        btn_MenuHome = findViewById(R.id.btn_MenuHome);

        card_running_orders = findViewById(R.id.card_running_orders);
        card_delivery_orders = findViewById(R.id.card_delivery_orders);
        card_revenue = findViewById(R.id.card_revenue);
        card_review = findViewById(R.id.card_review);
        btnXemTatCaReview = findViewById(R.id.btnXemTatCaReview);

        // Xử lý nút Back thiết bị
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    setEnabled(false);
                    getOnBackPressedDispatcher().onBackPressed();
                    setEnabled(true);
                }
            }
        });

        // Mở menu Sidebar
        if (btn_MenuHome != null) {
            btn_MenuHome.setOnClickListener(v -> {
                if (drawerLayout != null) {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            });
        }

        // Xử lý các mục trong Menu Sidebar
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(item -> {
                int id = item.getItemId();
                if (id == R.id.nav_home) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (id == R.id.nav_order) {
                    startActivity(new Intent(Admin_Dashboard.this, Admin_All_Order.class));
                } else if (id == R.id.nav_food_list) {
                    startActivity(new Intent(Admin_Dashboard.this, Admin_MyFoodList.class));
                } else if (id == R.id.nav_all_category) {
                    startActivity(new Intent(Admin_Dashboard.this, Admin_All_Category.class));
                } else if (id == R.id.nav_all_productreview) {
                    startActivity(new Intent(Admin_Dashboard.this, Admin_All_Review.class));
                } else if (id == R.id.nav_manage_users) {
                    startActivity(new Intent(Admin_Dashboard.this, UserManagementActivity.class));
                } else if (id == R.id.nav_notifications) {
                    startActivity(new Intent(Admin_Dashboard.this, Admin_Notification.class));
                } else if (id == R.id.nav_profile) {
                    startActivity(new Intent(Admin_Dashboard.this, Admin_Profile.class));
                } else if (id == R.id.nav_logout) {
                    logoutUser();
                }

                if (drawerLayout != null) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                return true;
            });
        }

        // Sự kiện click cho các Card thống kê
        setupCardListeners();
    }

    private void setupCardListeners() {
        if (card_running_orders != null) {
            card_running_orders.setOnClickListener(v -> {
                Intent intent = new Intent(Admin_Dashboard.this, Admin_All_Order.class);
                intent.putExtra("FILTER_STATUS", "Chờ xác nhận");
                startActivity(intent);
            });
        }

        if (card_delivery_orders != null) {
            card_delivery_orders.setOnClickListener(v -> {
                Intent intent = new Intent(Admin_Dashboard.this, Admin_All_Order.class);
                intent.putExtra("FILTER_STATUS", "Đang giao hàng");
                startActivity(intent);
            });
        }

        if (card_revenue != null) {
            card_revenue.setOnClickListener(v -> {
                Toast.makeText(this, "Tính năng chi tiết doanh thu đang được cập nhật", Toast.LENGTH_SHORT).show();
            });
        }

        View.OnClickListener reviewClick = v -> {
             Intent intent = new Intent(Admin_Dashboard.this, Admin_All_Review.class);
             startActivity(intent);
        };

        if (card_review != null) card_review.setOnClickListener(reviewClick);
        if (btnXemTatCaReview != null) btnXemTatCaReview.setOnClickListener(reviewClick);
    }

    private void logoutUser() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
        Toast.makeText(this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Admin_Dashboard.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}