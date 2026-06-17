package com.example.nhom33.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.example.nhom33.Database.FoodDB;
import com.example.nhom33.DataEntity.OrdersEntity;
import com.example.nhom33.DataEntity.ProductReviewEntity;
import com.example.nhom33.DataEntity.UsersEntity;
import com.example.nhom33.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Admin_Dashboard extends AppCompatActivity {

    View btn_MenuHome;
    View btnXemTatCaReview, tvSeeDetails;
    MaterialCardView card_running_orders, card_delivery_orders, card_review, card_revenue;
    TextView tvRunningOrdersCount, tvDeliveryOrdersCount, tvRevenueAmount, tvAverageRating, tvTotalReviews;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    LineChart revenueChart;
    private FoodDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.admin_dashboard);

        db = FoodDB.getInstance(this);

        // Ánh xạ các View từ XML
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        btn_MenuHome = findViewById(R.id.btn_MenuHome);

        tvRunningOrdersCount = findViewById(R.id.tvRunningOrdersCount);
        tvDeliveryOrdersCount = findViewById(R.id.tvDeliveryOrdersCount);
        tvRevenueAmount = findViewById(R.id.tvRevenueAmount);
        tvAverageRating = findViewById(R.id.tvAverageRating);
        tvTotalReviews = findViewById(R.id.tvTotalReviews);

        card_running_orders = findViewById(R.id.card_running_orders);
        card_delivery_orders = findViewById(R.id.card_delivery_orders);
        card_revenue = findViewById(R.id.card_revenue);
        tvSeeDetails = findViewById(R.id.tvSeeDetails);
        card_review = findViewById(R.id.card_review);
        btnXemTatCaReview = findViewById(R.id.btnXemTatCaReview);
        revenueChart = findViewById(R.id.revenueChart);

        setupRevenueChart();

        // Load thông tin Admin vào Header của Sidebar
        loadAdminInfo();

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
                    startActivity(new Intent(Admin_Dashboard.this, Admin_All_User.class));
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

    private void setupRevenueChart() {
        if (revenueChart == null) return;
        
        revenueChart.getDescription().setEnabled(false);
        revenueChart.setTouchEnabled(false);
        revenueChart.setDrawGridBackground(false);
        revenueChart.getLegend().setEnabled(false);
        
        XAxis xAxis = revenueChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(Color.parseColor("#A0A5BA"));
        xAxis.setGranularity(1f);
        
        revenueChart.getAxisLeft().setEnabled(false);
        revenueChart.getAxisRight().setEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDashboardStats();
    }

    private void loadDashboardStats() {
        new Thread(() -> {
            List<OrdersEntity> allOrders = db.ordersDAO().getAllOrders();
            List<ProductReviewEntity> allReviews = db.productReviewDAO().getAllReviews();

            int runningCount = 0;
            int deliveryCount = 0;
            double todayRevenue = 0;
            String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

            float[] hourlyData = new float[7]; // 08, 10, 12, 14, 16, 18, 20
            
            for (OrdersEntity order : allOrders) {
                if (order.getStatus() == 0) runningCount++; // Chờ xác nhận
                if (order.getStatus() == 1) deliveryCount++; // Đang giao

                // Tính doanh thu hôm nay (Chỉ đơn hoàn thành - Status = 2)
                if (order.getStatus() == 2 && order.getOrderDate() != null && order.getOrderDate().contains(today)) {
                    todayRevenue += order.getTotalAmount();
                    
                    try {
                        // Giả sử định dạng "yyyy-MM-dd HH:mm:ss"
                        String[] parts = order.getOrderDate().split(" ");
                        if (parts.length > 1) {
                            int hour = Integer.parseInt(parts[1].split(":")[0]);
                            if (hour >= 8 && hour < 10) hourlyData[0] += order.getTotalAmount();
                            else if (hour >= 10 && hour < 12) hourlyData[1] += order.getTotalAmount();
                            else if (hour >= 12 && hour < 14) hourlyData[2] += order.getTotalAmount();
                            else if (hour >= 14 && hour < 16) hourlyData[3] += order.getTotalAmount();
                            else if (hour >= 16 && hour < 18) hourlyData[4] += order.getTotalAmount();
                            else if (hour >= 18 && hour < 20) hourlyData[5] += order.getTotalAmount();
                            else if (hour >= 20) hourlyData[6] += order.getTotalAmount();
                        }
                    } catch (Exception ignored) {}
                }
            }

            double totalRating = 0;
            for (ProductReviewEntity review : allReviews) {
                totalRating += review.getRating();
            }
            double avgRating = allReviews.isEmpty() ? 0 : totalRating / allReviews.size();

            final double finalRevenue = todayRevenue;
            final int finalRunning = runningCount;
            final int finalDelivery = deliveryCount;
            final double finalAvg = avgRating;
            final int finalReviewCount = allReviews.size();
            final float[] finalHourlyData = hourlyData;

            runOnUiThread(() -> {
                if (tvRunningOrdersCount != null) tvRunningOrdersCount.setText(String.valueOf(finalRunning));
                if (tvDeliveryOrdersCount != null) tvDeliveryOrdersCount.setText(String.valueOf(finalDelivery));
                if (tvRevenueAmount != null) tvRevenueAmount.setText(String.format(Locale.getDefault(), "%,.0f VND", finalRevenue));
                if (tvAverageRating != null) tvAverageRating.setText(String.format(Locale.getDefault(), "%.1f", finalAvg));
                if (tvTotalReviews != null) tvTotalReviews.setText("Tổng " + finalReviewCount + " đánh giá");
                
                updateChart(finalHourlyData);
            });
        }).start();
    }

    private void updateChart(float[] data) {
        if (revenueChart == null) return;

        ArrayList<Entry> entries = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            entries.add(new Entry(i, data[i]));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Doanh thu");
        dataSet.setColor(Color.parseColor("#FF7622"));
        dataSet.setLineWidth(3f);
        dataSet.setCircleColor(Color.parseColor("#FF7622"));
        dataSet.setCircleRadius(5f);
        dataSet.setDrawValues(false);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet.setDrawFilled(true);
        dataSet.setFillColor(Color.parseColor("#FF7622"));
        dataSet.setFillAlpha(50);

        LineData lineData = new LineData(dataSet);
        revenueChart.setData(lineData);

        String[] labels = {"08h", "10h", "12h", "14h", "16h", "18h", "20h"};
        revenueChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        
        revenueChart.invalidate();
        revenueChart.animateY(1000);
    }

    private void loadAdminInfo() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", -1);

        if (userId != -1) {
            new Thread(() -> {
                UsersEntity admin = db.usersDAO().getUserById(userId);
                if (admin != null) {
                    runOnUiThread(() -> {
                        View headerView = navigationView.getHeaderView(0);
                        if (headerView != null) {
                            ImageView imgAvatar = headerView.findViewById(R.id.img_admin_avatar);
                            TextView tvName = headerView.findViewById(R.id.txt_admin_name);
                            TextView tvEmail = headerView.findViewById(R.id.txt_admin_email);

                            if (tvName != null) tvName.setText(admin.getFullName());
                            if (tvEmail != null) tvEmail.setText(admin.getEmail());

                            String avatar = admin.getAvatar();
                            if (imgAvatar != null) {
                                if (!TextUtils.isEmpty(avatar)) {
                                    Object loadTarget;
                                    if (avatar.startsWith("http") || avatar.startsWith("https") || avatar.startsWith("file://")) {
                                        loadTarget = avatar;
                                    } else {
                                        loadTarget = "file:///android_asset/img_user/" + avatar;
                                    }

                                    Glide.with(Admin_Dashboard.this)
                                            .load(loadTarget)
                                            .placeholder(R.drawable.fb)
                                            .error(R.drawable.fb)
                                            .circleCrop()
                                            .into(imgAvatar);
                                } else {
                                    imgAvatar.setImageResource(R.drawable.fb);
                                }
                            }
                        }
                    });
                }
            }).start();
        }
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

        View.OnClickListener revenueClick = v -> {
            Intent intent = new Intent(Admin_Dashboard.this, Admin_Revenue.class);
            startActivity(intent);
        };

        if (card_revenue != null) card_revenue.setOnClickListener(revenueClick);
        if (tvSeeDetails != null) tvSeeDetails.setOnClickListener(revenueClick);

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