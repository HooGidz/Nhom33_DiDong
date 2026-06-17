package com.example.nhom33.controller;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom33.Database.FoodDB;
import com.example.nhom33.DataEntity.OrdersEntity;
import com.example.nhom33.R;
import com.example.nhom33.adapter.Admin_Order_Adapter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class Admin_Revenue extends AppCompatActivity {

    private TextView tvTotalRevenue, tvOrderCount, tvDateRangeDisplay;
    private RecyclerView rvRecentOrders;
    private LineChart revenueChart;
    private MaterialButton btnDay, btnWeek, btnMonth, btnYear, btnCustomRange;
    private View btnBack;
    
    private FoodDB db;
    private Admin_Order_Adapter adapter;
    private List<OrdersEntity> allOrders = new ArrayList<>();
    private List<OrdersEntity> filteredOrders = new ArrayList<>();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private final SimpleDateFormat displayFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_revenue);

        db = FoodDB.getInstance(this);
        initViews();
        setupRevenueChart();
        setupRecyclerView();
        loadData();
        setupListeners();
    }

    private void initViews() {
        tvTotalRevenue = findViewById(R.id.tvTotalRevenue);
        tvOrderCount = findViewById(R.id.tvOrderCount);
        tvDateRangeDisplay = findViewById(R.id.tvDateRangeDisplay);
        rvRecentOrders = findViewById(R.id.rvRecentOrders);
        revenueChart = findViewById(R.id.revenueChart);
        btnDay = findViewById(R.id.btnDay);
        btnWeek = findViewById(R.id.btnWeek);
        btnMonth = findViewById(R.id.btnMonth);
        btnYear = findViewById(R.id.btnYear);
        btnCustomRange = findViewById(R.id.btnCustomRange);
        btnBack = findViewById(R.id.btnBack);
    }

    private void setupRevenueChart() {
        if (revenueChart == null) return;
        
        revenueChart.getDescription().setEnabled(false);
        revenueChart.setTouchEnabled(true);
        revenueChart.setDragEnabled(true);
        revenueChart.setScaleEnabled(true);
        revenueChart.setPinchZoom(true);
        revenueChart.setDrawGridBackground(false);
        revenueChart.getLegend().setEnabled(false);
        
        XAxis xAxis = revenueChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(Color.parseColor("#A0A5BA"));
        xAxis.setGranularity(1f);
        
        revenueChart.getAxisLeft().setEnabled(true);
        revenueChart.getAxisLeft().setDrawGridLines(true);
        revenueChart.getAxisLeft().setGridColor(Color.parseColor("#E8EAED"));
        revenueChart.getAxisLeft().setTextColor(Color.parseColor("#A0A5BA"));
        
        revenueChart.getAxisRight().setEnabled(false);
    }

    private void setupRecyclerView() {
        rvRecentOrders.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Admin_Order_Adapter(filteredOrders, order -> {
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
        btnCustomRange.setOnClickListener(v -> showDateRangePicker());

        TextView tvSeeAllOrders = findViewById(R.id.tvSeeAllOrders);
        if (tvSeeAllOrders != null) {
            tvSeeAllOrders.setOnClickListener(v -> {
                Toast.makeText(this, "Xem tất cả đơn hàng", Toast.LENGTH_SHORT).show();
            });
        }
    }

    private void showDateRangePicker() {
        MaterialDatePicker<Pair<Long, Long>> dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Chọn khoảng thời gian")
                .setSelection(new Pair<>(MaterialDatePicker.todayInUtcMilliseconds(), MaterialDatePicker.todayInUtcMilliseconds()))
                .build();

        dateRangePicker.show(getSupportFragmentManager(), "DATE_RANGE_PICKER");

        dateRangePicker.addOnPositiveButtonClickListener(selection -> {
            Calendar startCal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            startCal.setTimeInMillis(selection.first);
            
            Calendar endCal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            endCal.setTimeInMillis(selection.second);
            
            updateFilterCustom(startCal, endCal);
        });
    }

    private void updateFilterUI(String type) {
        resetButtonStyles();
        
        MaterialButton selectedBtn;
        String displayText;
        switch (type) {
            case "week": 
                selectedBtn = btnWeek; 
                displayText = "Đang hiển thị: Tuần này";
                break;
            case "month": 
                selectedBtn = btnMonth; 
                displayText = "Đang hiển thị: Tháng này";
                break;
            case "year": 
                selectedBtn = btnYear; 
                displayText = "Đang hiển thị: Năm này";
                break;
            default: 
                selectedBtn = btnDay; 
                displayText = "Đang hiển thị: Hôm nay";
                break;
        }
        
        applySelectedStyle(selectedBtn);
        tvDateRangeDisplay.setText(displayText);
        filterData(type, null, null);
    }

    private void updateFilterCustom(Calendar start, Calendar end) {
        resetButtonStyles();
        applySelectedStyle(btnCustomRange);
        
        String startStr = displayFormat.format(start.getTime());
        String endStr = displayFormat.format(end.getTime());
        tvDateRangeDisplay.setText("Từ " + startStr + " đến " + endStr);
        
        filterData("custom", start, end);
    }

    private void applySelectedStyle(MaterialButton btn) {
        btn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF7622")));
        btn.setTextColor(Color.WHITE);
        btn.setStrokeWidth(0);
        if (btn == btnCustomRange) {
            btn.setIconTint(ColorStateList.valueOf(Color.WHITE));
        }
    }

    private void resetButtonStyles() {
        MaterialButton[] buttons = {btnDay, btnWeek, btnMonth, btnYear, btnCustomRange};
        for (MaterialButton btn : buttons) {
            btn.setBackgroundTintList(ColorStateList.valueOf(Color.TRANSPARENT));
            btn.setTextColor(Color.parseColor("#A0A5BA"));
            btn.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#E8EAED")));
            btn.setStrokeWidth(2);
            if (btn == btnCustomRange) {
                btn.setIconTint(ColorStateList.valueOf(Color.parseColor("#A0A5BA")));
            }
        }
    }

    private void filterData(String type, Calendar customStart, Calendar customEnd) {
        filteredOrders.clear();
        double totalRevenue = 0;
        int completedCount = 0;
        
        double[] chartData;
        String[] chartLabels;
        
        Calendar now = Calendar.getInstance();
        Calendar orderCal = Calendar.getInstance();

        switch (type) {
            case "day":
                chartData = new double[6]; // 4-hour segments
                chartLabels = new String[]{"4h", "8h", "12h", "16h", "20h", "24h"};
                break;
            case "month":
                chartData = new double[4]; // Weeks
                chartLabels = new String[]{"T1", "T2", "T3", "T4"};
                break;
            case "year":
                chartData = new double[12]; // Months
                chartLabels = new String[]{"T1", "T2", "T3", "T4", "T5", "T6", "T7", "T8", "T9", "T10", "T11", "T12"};
                break;
            default: // week or custom
                chartData = new double[7];
                chartLabels = new String[]{"T2", "T3", "T4", "T5", "T6", "T7", "CN"};
                break;
        }

        if (type.equals("custom") && customStart != null && customEnd != null) {
            long diff = customEnd.getTimeInMillis() - customStart.getTimeInMillis();
            long days = diff / (24 * 60 * 60 * 1000) + 1;
            if (days <= 31) {
                chartData = new double[(int)days];
                chartLabels = new String[(int)days];
                Calendar temp = (Calendar) customStart.clone();
                for (int i = 0; i < days; i++) {
                    chartLabels[i] = String.valueOf(temp.get(Calendar.DAY_OF_MONTH));
                    temp.add(Calendar.DAY_OF_YEAR, 1);
                }
            }
        }

        for (OrdersEntity order : allOrders) {
            try {
                String dateStr = order.getOrderDate();
                Date orderDate;
                if (dateStr.contains(":")) {
                    orderDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(dateStr);
                } else {
                    orderDate = dateFormat.parse(dateStr);
                }
                
                if (orderDate == null) continue;
                orderCal.setTime(orderDate);
                
                boolean isMatch = false;
                int chartIndex = -1;

                switch (type) {
                    case "day":
                        if (isSameDay(now, orderCal)) {
                            isMatch = true;
                            chartIndex = orderCal.get(Calendar.HOUR_OF_DAY) / 4;
                        }
                        break;
                    case "week":
                        if (isSameWeek(now, orderCal)) {
                            isMatch = true;
                            int dayOfWeek = orderCal.get(Calendar.DAY_OF_WEEK);
                            chartIndex = (dayOfWeek == Calendar.SUNDAY) ? 6 : dayOfWeek - 2;
                        }
                        break;
                    case "month":
                        if (isSameMonth(now, orderCal)) {
                            isMatch = true;
                            chartIndex = Math.min((orderCal.get(Calendar.DAY_OF_MONTH) - 1) / 7, 3);
                        }
                        break;
                    case "year":
                        if (now.get(Calendar.YEAR) == orderCal.get(Calendar.YEAR)) {
                            isMatch = true;
                            chartIndex = orderCal.get(Calendar.MONTH);
                        }
                        break;
                    case "custom":
                        if (!orderCal.before(customStart) && !orderCal.after(customEnd)) {
                            isMatch = true;
                            if (chartData.length <= 31) {
                                long d = (orderCal.getTimeInMillis() - customStart.getTimeInMillis()) / (24 * 60 * 60 * 1000);
                                chartIndex = (int) d;
                            }
                        }
                        break;
                }
                
                if (isMatch) {
                    filteredOrders.add(order);
                    if (order.getStatus() == 2) { // Completed
                        totalRevenue += order.getTotalAmount();
                        completedCount++;
                        if (chartIndex >= 0 && chartIndex < chartData.length) {
                            chartData[chartIndex] += order.getTotalAmount();
                        }
                    }
                }
            } catch (Exception e) {
                Log.e("Admin_Revenue", "Error: " + e.getMessage());
            }
        }
        
        tvTotalRevenue.setText(String.format(Locale.getDefault(), "%,.0f VND", totalRevenue));
        tvOrderCount.setText("Số đơn hàng hoàn thành: " + completedCount);
        adapter.notifyDataSetChanged();
        
        updateLineChart(chartData, chartLabels);
    }

    private void updateLineChart(double[] data, String[] labels) {
        if (revenueChart == null) return;

        ArrayList<Entry> entries = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            entries.add(new Entry(i, (float) data[i]));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Doanh thu");
        dataSet.setColor(Color.parseColor("#FF7622"));
        dataSet.setLineWidth(3f);
        dataSet.setCircleColor(Color.parseColor("#FF7622"));
        dataSet.setCircleRadius(4f);
        dataSet.setDrawCircleHole(true);
        dataSet.setCircleHoleColor(Color.WHITE);
        dataSet.setCircleHoleRadius(2f);
        dataSet.setDrawValues(false);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        
        // Fill area
        dataSet.setDrawFilled(true);
        dataSet.setFillColor(Color.parseColor("#FF7622"));
        dataSet.setFillAlpha(40);

        LineData lineData = new LineData(dataSet);
        revenueChart.setData(lineData);

        revenueChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        revenueChart.getXAxis().setLabelCount(labels.length);
        
        revenueChart.invalidate();
        revenueChart.animateY(1000);
    }

    private boolean isSameDay(Calendar cal1, Calendar cal2) {
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
               cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

    private boolean isSameWeek(Calendar cal1, Calendar cal2) {
        Calendar c1 = (Calendar) cal1.clone();
        c1.setFirstDayOfWeek(Calendar.MONDAY);
        int week1 = c1.get(Calendar.WEEK_OF_YEAR);
        
        Calendar c2 = (Calendar) cal2.clone();
        c2.setFirstDayOfWeek(Calendar.MONDAY);
        int week2 = c2.get(Calendar.WEEK_OF_YEAR);
        
        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && week1 == week2;
    }

    private boolean isSameMonth(Calendar cal1, Calendar cal2) {
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
               cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
    }
}