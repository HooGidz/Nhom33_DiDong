package com.example.nhom33.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom33.R;
import com.example.nhom33.Database.FoodDB;
import com.example.nhom33.DataEntity.OrdersEntity;

public class Main_track_order extends AppCompatActivity {
    private TextView tvStoreName, tvOrderTime, tvOrderDetails;
    private FoodDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_order);

        db = FoodDB.getInstance(this);

        // Ánh xạ View
        ImageButton btnBack = findViewById(R.id.btnBack);
        tvStoreName = findViewById(R.id.tvStoreName);
        tvOrderTime = findViewById(R.id.tvOrderTime);
        tvOrderDetails = findViewById(R.id.tvOrderDetails);

        // Nhận dữ liệu từ Intent
        String orderIdStr = getIntent().getStringExtra("orderId");
        if (orderIdStr != null) {
            try {
                // Loại bỏ ký tự # nếu có để lấy ID số
                int id = Integer.parseInt(orderIdStr.replace("#", ""));
                loadOrderData(id);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quay lại trang trước đó (MainOnOrder)
                finish();
            }
        });
    }

    private void loadOrderData(int orderId) {
        OrdersEntity order = db.ordersDAO().getOrderById(orderId);
        if (order != null) {
            tvStoreName.setText("Cửa hàng NHOM33"); // Hoặc lấy từ liên kết bảng Foods
            tvOrderTime.setText("Đặt lúc: " + order.getOrderDate());
            
            String statusInfo = "Trạng thái: " + order.getStatus();
            if (order.getStatus().equals("Đang giao hàng")) {
                statusInfo += "\nShipper đang trên đường giao đến bạn.";
            } else {
                statusInfo += "\nĐơn hàng đang được chuẩn bị.";
            }
            tvOrderDetails.setText(statusInfo);
        }
    }
}
