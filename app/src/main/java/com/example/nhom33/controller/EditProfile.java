package com.example.nhom33.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom33.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import com.example.nhom33.adapter.AddressAdapter;
import com.example.nhom33.adapter.HisOrderAdapter;
import com.example.nhom33.database.Address;
import com.example.nhom33.database.hisOrder;

public class EditProfile extends AppCompatActivity {
    View btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
    }




/// /////////////////////////////////////////////////////////
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.hisorder_activity);
//
//        // 1. Ánh xạ View
//        recyclerView = findViewById(R.id.recyclerView);
//        tabLayout = findViewById(R.id.tabLayout);
//
//        // 2. Thiết lập Listener TRƯỚC khi chọn Tab mặc định
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                if (tab.getPosition() == 0) { // Bấm sang Đang giao
//                    Intent intent = new Intent(MainActivity.this, MainOnOrder.class);
//                    startActivity(intent);
//                    overridePendingTransition(0, 0);
//                    finish();
//                }
//            }
//            @Override public void onTabUnselected(TabLayout.Tab tab) {}
//            @Override public void onTabReselected(TabLayout.Tab tab) {}
//        });
//
//        tabLayout.post(() -> {
//            TabLayout.Tab tab = tabLayout.getTabAt(1); // Lịch sử là index 1
//            if (tab != null) tab.select();
//        });
//
//        // 3. Thiết lập RecyclerView
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        // Tạo danh sách dữ liệu mẫu theo Model mới đã tách TextView
//        orderList = new ArrayList<>();
//        prepareData();
//
//        // 4. Gán Adapter
//        orderAdapter = new HisOrderAdapter(this, orderList);
//        recyclerView.setAdapter(orderAdapter);
//    }
//
//    private void prepareData() {
//        // Dữ liệu mẫu 1: Pizza Hut - Completed
//        orderList.add(new hisOrder("Food", "Completed",
//                "Pizza Hut",
//                "#162432",
//                "$35.25",
//                "29 JAN, 12:30",
//                "03 Items",
//                R.mipmap.ic_launcher // Thay bằng ảnh thật của bạn trong drawable
//        ));
//
//        // Dữ liệu mẫu 2: McDonald's - Completed
//        orderList.add(new hisOrder(
//                "Food",
//                "Completed",
//                "McDonald's",
//                "#242432",
//                "$40.15",
//                "30 JAN, 12:30",
//                "02 Items",
//                R.mipmap.ic_launcher
//        ));
//
//        // Dữ liệu mẫu 3: Starbucks - Canceled
//        orderList.add(new hisOrder(
//                "Drink",
//                "Canceled",
//                "Starbucks",
//                "#240112",
//                "$10.20",
//                "30 JAN, 12:30",
//                "01 Items",
//                R.mipmap.ic_launcher
//        ));
//    }
}