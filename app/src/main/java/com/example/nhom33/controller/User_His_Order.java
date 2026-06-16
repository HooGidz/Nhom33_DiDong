package com.example.nhom33.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom33.R;
import com.example.nhom33.Database.FoodDB;
import com.example.nhom33.DataEntity.OrdersEntity;
import com.example.nhom33.DAO.OrderDetailsDAO;
import com.example.nhom33.adapter.HisOrderAdapter;
import com.example.nhom33.db.hisOrder;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class User_His_Order extends AppCompatActivity {
    private RecyclerView recyclerView;
    private HisOrderAdapter orderAdapter;
    private List<hisOrder> orderList;
    private TabLayout tabLayout;
    private View btn_back; // Đổi thành View để tránh ClassCastException với MaterialCardView trong XML
    private FoodDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_order_list);

        db = FoodDB.getInstance(this);

        recyclerView = findViewById(R.id.recyclerView);
        tabLayout = findViewById(R.id.tabLayout);
        btn_back = findViewById(R.id.btn_back);

        if (btn_back != null) {
            btn_back.setOnClickListener(v -> finish());
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    Intent intent = new Intent(User_His_Order.this, User_Order_On.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                }
            }
            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab) {}
        });

        tabLayout.post(() -> {
            TabLayout.Tab tab = tabLayout.getTabAt(1);
            if (tab != null) tab.select();
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderList = new ArrayList<>();
        
        loadHistoryDataFromDB();

        orderAdapter = new HisOrderAdapter(this, orderList);
        recyclerView.setAdapter(orderAdapter);
    }

    private void loadHistoryDataFromDB() {
        // Lấy userId thực tế từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        int currentUserId = sharedPreferences.getInt("userId", -1);

        if (currentUserId == -1) {
            return; // Chưa đăng nhập hoặc không tìm thấy ID
        }

        // Lấy TẤT CẢ đơn hàng của user này (không phân biệt trạng thái)
        List<OrdersEntity> entities = db.ordersDAO().getOrdersByUserId(currentUserId);
        
        for (OrdersEntity entity : entities) {
            // Lấy chi tiết liên kết để có Danh mục và Tên món
            List<OrderDetailsDAO.OrderDetailWithFood> details = db.orderDetailsDAO().getDetailsWithFoodByOrderId(entity.getOrderId());
            
            String category = "N/A";
            String displayName = "Đơn hàng trống";
            int totalItems = 0;

            if (details != null && !details.isEmpty()) {
                category = details.get(0).category_name;
                displayName = details.get(0).food_name;
                if (details.size() > 1) {
                    displayName += " + " + (details.size() - 1) + " món khác";
                }
                for (OrderDetailsDAO.OrderDetailWithFood d : details) {
                    totalItems += d.quantity;
                }
            }

            // Chuyển status (kiểu int) thành chuỗi mô tả để phù hợp với constructor hisOrder
            String statusText;
            switch (entity.getStatus()) {
                case 1:
                    statusText = "Đang giao hàng";
                    break;
                case 2:
                    statusText = "Đã giao";
                    break;
                case 3:
                    statusText = "Đã hủy";
                    break;
                default:
                    statusText = "Đang chuẩn bị";
            }

            orderList.add(new hisOrder(
                    category,
                    statusText,
                    displayName,
                    "#" + entity.getOrderId(),
                    String.format(Locale.getDefault(), "%,.0f VNĐ", entity.getTotalAmount()),
                    entity.getOrderDate(),
                    totalItems + " món",
                    R.mipmap.ic_launcher
            ));
        }
    }
}
