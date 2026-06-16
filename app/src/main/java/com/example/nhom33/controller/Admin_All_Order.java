package com.example.nhom33.controller;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom33.Database.FoodDB;
import com.example.nhom33.DataEntity.OrdersEntity;
import com.example.nhom33.R;
import com.example.nhom33.adapter.Admin_Order_Adapter;

import java.util.ArrayList;
import java.util.List;

public class Admin_All_Order extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Admin_Order_Adapter adapter;
    private final List<OrdersEntity> orderList = new ArrayList<>();
    private FoodDB db;

    private ImageButton btnBack;
    private Button btnFilterAll, btnFilterPending, btnFilterShipping, btnFilterCompleted, btnFilterCancelled;
    private ImageButton navDashboard, navMenu, navAdd, navNotification, navProfile;

    private String currentFilter = "All";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_all_order);

        db = FoodDB.getInstance(this);

        String filter = getIntent().getStringExtra("FILTER_STATUS");
        if (filter != null) {
            currentFilter = filter;
        }

        initViews();
        setupRecyclerView();
        setupListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadOrders(currentFilter);
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        btnBack = findViewById(R.id.btn_back);

        btnFilterAll = findViewById(R.id.btn_filter_all);
        btnFilterPending = findViewById(R.id.btn_filter_pending);
        btnFilterShipping = findViewById(R.id.btn_filter_shipping);
        btnFilterCompleted = findViewById(R.id.btn_filter_completed);
        btnFilterCancelled = findViewById(R.id.btn_filter_cancelled);

        navDashboard = findViewById(R.id.nav_dashboard);
        navMenu = findViewById(R.id.nav_menu);
        navAdd = findViewById(R.id.nav_add);
        navNotification = findViewById(R.id.nav_notification);
        navProfile = findViewById(R.id.nav_profile);
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Admin_Order_Adapter(orderList, order -> {
            Intent intent = new Intent(Admin_All_Order.this, Admin_Edit_Order.class);
            intent.putExtra("order_id", order.getOrderId());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);
    }

    private void loadOrders(String status) {
        this.currentFilter = status;

        // Chạy truy vấn DB trong luồng nền để tránh crash (ANR/MainThreadQueries)
        new Thread(() -> {
            List<OrdersEntity> resultList = new ArrayList<>();
            try {
                if (status.equalsIgnoreCase("All")) {
                    List<OrdersEntity> allOrders = db.ordersDAO().getAllOrders();
                    if (allOrders != null) resultList.addAll(allOrders);
                } else {
                    int statusCode = mapStatusStringToCode(status);
                    List<OrdersEntity> filteredOrders = db.ordersDAO().getOrdersByStatus(statusCode);
                    if (filteredOrders != null) resultList.addAll(filteredOrders);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Cập nhật giao diện trên luồng chính
            new Handler(Looper.getMainLooper()).post(() -> {
                orderList.clear();
                orderList.addAll(resultList);
                if (adapter != null) {
                    adapter.notifyDataSetChanged(); // Sử dụng notifyDataSetChanged để an toàn hơn khi thay đổi toàn bộ list
                }
                updateTabUI(status);
            });
        }).start();
    }

    private int mapStatusStringToCode(String statusText) {
        if (statusText.equalsIgnoreCase("Đang giao hàng")) {
            return 1;
        } else if (statusText.equalsIgnoreCase("Hoàn thành") || statusText.equalsIgnoreCase("Đã giao")) {
            return 2;
        } else if (statusText.equalsIgnoreCase("Đã huỷ")) {
            return 3;
        }
        return 0; // Chờ xác nhận
    }

    private void updateTabUI(String activeStatus) {
        int activeColor = Color.parseColor("#FF7622");
        int inactiveColor = Color.parseColor("#D2D3DC");

        if (btnFilterAll != null) btnFilterAll.setTextColor(inactiveColor);
        if (btnFilterPending != null) btnFilterPending.setTextColor(inactiveColor);
        if (btnFilterShipping != null) btnFilterShipping.setTextColor(inactiveColor);
        if (btnFilterCompleted != null) btnFilterCompleted.setTextColor(inactiveColor);
        if (btnFilterCancelled != null) btnFilterCancelled.setTextColor(inactiveColor);

        if (activeStatus.equalsIgnoreCase("All")) {
            if (btnFilterAll != null) btnFilterAll.setTextColor(activeColor);
        } else if (activeStatus.equalsIgnoreCase("Chờ xác nhận")) {
            if (btnFilterPending != null) btnFilterPending.setTextColor(activeColor);
        } else if (activeStatus.equalsIgnoreCase("Đang giao hàng")) {
            if (btnFilterShipping != null) btnFilterShipping.setTextColor(activeColor);
        } else if (activeStatus.equalsIgnoreCase("Hoàn thành")) {
            if (btnFilterCompleted != null) btnFilterCompleted.setTextColor(activeColor);
        } else if (activeStatus.equalsIgnoreCase("Đã huỷ")) {
            if (btnFilterCancelled != null) btnFilterCancelled.setTextColor(activeColor);
        }
    }

    private void setupListeners() {
        if (btnBack != null) btnBack.setOnClickListener(v -> finish());

        if (btnFilterAll != null) btnFilterAll.setOnClickListener(v -> loadOrders("All"));
        if (btnFilterPending != null) btnFilterPending.setOnClickListener(v -> loadOrders("Chờ xác nhận"));
        if (btnFilterShipping != null) btnFilterShipping.setOnClickListener(v -> loadOrders("Đang giao hàng"));
        if (btnFilterCompleted != null) btnFilterCompleted.setOnClickListener(v -> loadOrders("Hoàn thành"));
        if (btnFilterCancelled != null) btnFilterCancelled.setOnClickListener(v -> loadOrders("Đã huỷ"));

        if (navDashboard != null) {
            navDashboard.setOnClickListener(v -> {
                startActivity(new Intent(this, Admin_Dashboard.class));
                finish();
            });
        }
        if (navMenu != null) navMenu.setOnClickListener(v -> startActivity(new Intent(this, Admin_MyFoodList.class)));
        if (navAdd != null) navAdd.setOnClickListener(v -> startActivity(new Intent(this, Admin_Add_Food.class)));
        if (navNotification != null) navNotification.setOnClickListener(v -> startActivity(new Intent(this, Admin_Notification.class)));
        if (navProfile != null) navProfile.setOnClickListener(v -> startActivity(new Intent(this, MainAdProfile.class)));
    }
}