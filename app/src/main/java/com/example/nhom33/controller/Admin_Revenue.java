package com.example.nhom33.controller;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom33.Database.FoodDB;
import com.example.nhom33.DataEntity.OrdersEntity;
import com.example.nhom33.R;
import com.example.nhom33.adapter.Admin_Order_Adapter;
import com.google.android.material.button.MaterialButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Admin_Revenue extends AppCompatActivity {

    private TextView tvTotalRevenue;
    private RecyclerView rvRecentOrders;
    private MaterialButton btnDay, btnWeek, btnMonth, btnYear;
    private View btnBack;
    
    private FoodDB db;
    private Admin_Order_Adapter adapter;
    private List<OrdersEntity> allOrders = new ArrayList<>();
    private List<OrdersEntity> filteredOrders = new ArrayList<>();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_revenue);

        db = FoodDB.getInstance(this);
        initViews();
        setupRecyclerView();
        loadData();
        setupListeners();
    }

    private void initViews() {
        tvTotalRevenue = findViewById(R.id.tvTotalRevenue);
        rvRecentOrders = findViewById(R.id.rvRecentOrders);
        btnDay = findViewById(R.id.btnDay);
        btnWeek = findViewById(R.id.btnWeek);
        btnMonth = findViewById(R.id.btnMonth);
        btnYear = findViewById(R.id.btnYear);
        btnBack = findViewById(R.id.btnBack);
    }

    private void setupRecyclerView() {
        rvRecentOrders.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Admin_Order_Adapter(filteredOrders, order -> {
            // Xem chi tiết đơn hàng
            Toast.makeText(this, "Đơn hàng #" + order.getOrderId(), Toast.LENGTH_SHORT).show();
        });
        rvRecentOrders.setAdapter(adapter);
    }

    private void loadData() {
        new Thread(() -> {
            allOrders = db.ordersDAO().getAllOrders();
            runOnUiThread(() -> {
                updateFilterUI("day");
            });
        }).start();
    }

    private void setupListeners() {
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        btnDay.setOnClickListener(v -> updateFilterUI("day"));
        btnWeek.setOnClickListener(v -> updateFilterUI("week"));
        btnMonth.setOnClickListener(v -> updateFilterUI("month"));
        btnYear.setOnClickListener(v -> updateFilterUI("year"));

        TextView tvSeeAllOrders = findViewById(R.id.tvSeeAllOrders);
        if (tvSeeAllOrders != null) {
            tvSeeAllOrders.setOnClickListener(v -> {
                // Chuyển sang trang quản lý đơn hàng
                Toast.makeText(this, "Xem tất cả đơn hàng", Toast.LENGTH_SHORT).show();
            });
        }
    }

    private void updateFilterUI(String type) {
        resetButtonStyles();
        
        MaterialButton selectedBtn;
        switch (type) {
            case "week": selectedBtn = btnWeek; break;
            case "month": selectedBtn = btnMonth; break;
            case "year": selectedBtn = btnYear; break;
            default: selectedBtn = btnDay; break;
        }
        
        selectedBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF7622")));
        selectedBtn.setTextColor(Color.WHITE);
        selectedBtn.setStrokeWidth(0);
        
        filterData(type);
    }

    private void resetButtonStyles() {
        MaterialButton[] buttons = {btnDay, btnWeek, btnMonth, btnYear};
        for (MaterialButton btn : buttons) {
            btn.setBackgroundTintList(ColorStateList.valueOf(Color.TRANSPARENT));
            btn.setTextColor(Color.parseColor("#A0A5BA"));
            btn.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#E8EAED")));
            btn.setStrokeWidth(1);
        }
    }

    private void filterData(String type) {
        filteredOrders.clear();
        double totalRevenue = 0;
        Calendar now = Calendar.getInstance();
        
        for (OrdersEntity order : allOrders) {
            try {
                // Lấy phần ngày từ orderDate (giả sử có thể chứa giờ: "dd/MM/yyyy HH:mm")
                String datePart = order.getOrderDate().split(" ")[0];
                Date orderDate = dateFormat.parse(datePart);
                if (orderDate == null) continue;
                
                Calendar orderCal = Calendar.getInstance();
                orderCal.setTime(orderDate);
                
                boolean isMatch = false;
                switch (type) {
                    case "day":
                        isMatch = isSameDay(now, orderCal);
                        break;
                    case "week":
                        isMatch = isSameWeek(now, orderCal);
                        break;
                    case "month":
                        isMatch = isSameMonth(now, orderCal);
                        break;
                    case "year":
                        isMatch = now.get(Calendar.YEAR) == orderCal.get(Calendar.YEAR);
                        break;
                }
                
                if (isMatch) {
                    filteredOrders.add(order);
                    // Cộng doanh thu cho đơn hàng hoàn thành (status = 2)
                    if (order.getStatus() == 2) {
                        totalRevenue += order.getTotalAmount();
                    }
                }
            } catch (ParseException | ArrayIndexOutOfBoundsException e) {
                // Fallback nếu parse lỗi
                if (type.equals("day")) continue; 
                filteredOrders.add(order);
            }
        }
        
        tvTotalRevenue.setText(String.format(Locale.getDefault(), "%,.0f VND", totalRevenue));
        adapter.notifyDataSetChanged();
    }

    private boolean isSameDay(Calendar cal1, Calendar cal2) {
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
               cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

    private boolean isSameWeek(Calendar cal1, Calendar cal2) {
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
               cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR);
    }

    private boolean isSameMonth(Calendar cal1, Calendar cal2) {
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
               cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
    }
}
