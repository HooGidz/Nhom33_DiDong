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
import com.example.nhom33.adapter.OnOrderAdapter;
import com.example.nhom33.db.OnOrder;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainOnOrder extends AppCompatActivity {
    private RecyclerView recyclerView;
    private OnOrderAdapter orderAdapter;
    private List<OnOrder> orderList;
    private TabLayout tabLayout;
    private View btn_back;
    private FoodDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hisorder_activity);

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
                if (tab.getPosition() == 1) {
                    Intent intent = new Intent(MainOnOrder.this, HisOrderMain.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                }
            }
            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab) {}
        });

        tabLayout.post(() -> {
            TabLayout.Tab tab = tabLayout.getTabAt(0);
            if (tab != null) tab.select();
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderList = new ArrayList<>();

        loadOngoingOrdersFromDB();

        OnOrderAdapter orderAdapter = new OnOrderAdapter(this, orderList);
        recyclerView.setAdapter(orderAdapter);
    }

    private void loadOngoingOrdersFromDB() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", -1);

        if (userId == -1) {
            return;
        }

        List<OrdersEntity> orders = db.ordersDAO().getOngoingOrdersByUserId(userId);

        for (OrdersEntity order : orders) {
            List<OrderDetailsDAO.OrderDetailWithFood> details = db.orderDetailsDAO().getDetailsWithFoodByOrderId(order.getOrderId());

            String category = "N/A";
            String displayName = "Đơn hàng trống";
            int totalQty = 0;

            if (details != null && !details.isEmpty()) {
                category = details.get(0).category_name;
                displayName = details.get(0).food_name;
                if (details.size() > 1) {
                    displayName += " + " + (details.size() - 1) + " món khác";
                }
                for (OrderDetailsDAO.OrderDetailWithFood d : details) {
                    totalQty += d.quantity;
                }
            }

            // Map int status to String description
            String statusText;
            switch (order.getStatus()) {
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

            orderList.add(new OnOrder(
                    category,
                    displayName,
                    "#" + order.getOrderId(),
                    String.format(Locale.getDefault(), "%,.0f VNĐ", order.getTotalAmount()),
                    totalQty + " món",
                    statusText,
                    order.getOrderDate(),
                    R.mipmap.ic_launcher
            ));
        }
    }
}