package com.example.nhom33.controller;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

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
    private List<OrdersEntity> orderList = new ArrayList<>();
    private FoodDB db;

    private ImageButton btnBack;
    private Button btnFilterAll, btnFilterPending, btnFilterShipping, btnFilterCompleted, btnFilterCancelled;
    private ImageButton navDashboard, navMenu, navAdd, navNotification, navProfile;
    
    // Biến lưu trữ bộ lọc hiện tại (Mặc định là "All")
    private String currentFilter = "All";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_all_order);

        db = FoodDB.getInstance(this);

        // Kiểm tra xem có yêu cầu lọc cụ thể từ Intent không
        String filter = getIntent().getStringExtra("FILTER_STATUS");
        if (filter != null) {
            currentFilter = filter;
        }

        initViews();
        setupRecyclerView();
        setupListeners();
    }

    // Hàm onResume sẽ chạy mỗi khi bạn quay lại màn hình này
    @Override
    protected void onResume() {
        super.onResume();
        loadOrders(currentFilter); // Tải lại danh sách theo bộ lọc hiện tại
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
            // Chuyển sang màn hình sửa và cập nhật đơn hàng
            Intent intent = new Intent(Admin_All_Order.this, Admin_Edit_Order.class);
            intent.putExtra("order_id", order.getOrderId());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);
    }

    private void loadOrders(String status) {
        this.currentFilter = status; // Cập nhật bộ lọc hiện tại
        orderList.clear();
        if (status.equalsIgnoreCase("All")) {
            orderList.addAll(db.ordersDAO().getAllOrders());
        } else {
            // Lọc theo trạng thái chính xác từ Database
            orderList.addAll(db.ordersDAO().getOrdersByStatus(status));
        }
        adapter.notifyDataSetChanged();
        updateTabUI(status);
    }

    private void updateTabUI(String activeStatus) {
        int activeColor = Color.parseColor("#FF7622");
        int inactiveColor = Color.parseColor("#D2D3DC");

        btnFilterAll.setTextColor(inactiveColor);
        btnFilterPending.setTextColor(inactiveColor);
        btnFilterShipping.setTextColor(inactiveColor);
        btnFilterCompleted.setTextColor(inactiveColor);
        btnFilterCancelled.setTextColor(inactiveColor);

        if (activeStatus.equalsIgnoreCase("All")) {
            btnFilterAll.setTextColor(activeColor);
        } else if (activeStatus.equalsIgnoreCase("Chờ xác nhận")) {
            btnFilterPending.setTextColor(activeColor);
        } else if (activeStatus.equalsIgnoreCase("Đang giao hàng")) {
            btnFilterShipping.setTextColor(activeColor);
        } else if (activeStatus.equalsIgnoreCase("Hoàn thành")) {
            btnFilterCompleted.setTextColor(activeColor);
        } else if (activeStatus.equalsIgnoreCase("Đã huỷ")) {
            btnFilterCancelled.setTextColor(activeColor);
        }
    }

    private void setupListeners() {
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // Cập nhật bộ lọc khi nhấn nút
        btnFilterAll.setOnClickListener(v -> loadOrders("All"));
        btnFilterPending.setOnClickListener(v -> loadOrders("Chờ xác nhận"));
        btnFilterShipping.setOnClickListener(v -> loadOrders("Đang giao hàng"));
        btnFilterCompleted.setOnClickListener(v -> loadOrders("Hoàn thành"));
        btnFilterCancelled.setOnClickListener(v -> loadOrders("Đã huỷ"));

        navDashboard.setOnClickListener(v -> {
            startActivity(new Intent(this, Admin_Dashboard.class));
            finish();
        });

        navMenu.setOnClickListener(v -> startActivity(new Intent(this, Admin_MyFoodList.class)));
        navAdd.setOnClickListener(v -> startActivity(new Intent(this, Admin_Add_Food.class)));
        navNotification.setOnClickListener(v -> startActivity(new Intent(this, Admin_Notification.class)));
        navProfile.setOnClickListener(v -> startActivity(new Intent(this, MainAdProfile.class)));
    }
}
