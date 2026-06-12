package com.example.nhom33.controller;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom33.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import com.example.nhom33.adapter.OnOrderAdapter;
import com.example.nhom33.db.OnOrder;

public class MainOnOrder extends AppCompatActivity {
    private RecyclerView recyclerView;
    private OnOrderAdapter orderAdapter;
    private List<OnOrder> orderList;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hisorder_activity);

        // 1. Ánh xạ View
        recyclerView = findViewById(R.id.recyclerView);
        tabLayout = findViewById(R.id.tabLayout);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 1) { // Bấm sang Lịch sử
                    Intent intent = new Intent(MainOnOrder.this, HisOrderMain.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0); // Không hiệu ứng để mượt hơn
                    finish();
                }
            }
            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab) {}
        });

        tabLayout.post(() -> {
            TabLayout.Tab tab = tabLayout.getTabAt(0); // Đang giao là index 0
            if (tab != null) tab.select();
        });

        // 3. Thiết lập RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Tạo danh sách dữ liệu mẫu theo Model mới đã tách TextView
        orderList = new ArrayList<>();
        prepareData();

        // 4. Gán Adapter
        orderAdapter = new OnOrderAdapter(this, orderList);
        recyclerView.setAdapter(orderAdapter);
    }

    private void prepareData() {
        // Dữ liệu mẫu 1: Pizza Hut - Completed
        orderList.add(new OnOrder("Food",
                "Pizza Hut",
                "#162432",
                "$35.25",
                "03 Items",
                R.mipmap.ic_launcher // Thay bằng ảnh thật của bạn trong drawable
        ));

        // Dữ liệu mẫu 2: McDonald's - Completed
        orderList.add(new OnOrder(
                "Food",
                "McDonald's",
                "#242432",
                "$40.15",
                "02 Items",
                R.mipmap.ic_launcher
        ));

        // Dữ liệu mẫu 3: Starbucks - Canceled
        orderList.add(new OnOrder(
                "Drink",
                "Starbucks",
                "#240112",
                "$10.20",
                "01 Items",
                R.mipmap.ic_launcher
        ));
    }
}
