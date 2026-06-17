package com.example.nhom33.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom33.DAO.OrderDetailsDAO;
import com.example.nhom33.DataEntity.OrdersEntity;
import com.example.nhom33.DataEntity.ProductReviewEntity;
import com.example.nhom33.R;
import com.example.nhom33.adapter.ProductReviewAdapter;
import com.example.nhom33.Database.FoodDB;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UserReviewOrderActivity extends AppCompatActivity {
    private ImageView btnClose;
    private TextView tvReceiverName, tvAddress, tvDeliveryTime;
    private RecyclerView rvProductReviews;
    private com.google.android.material.button.MaterialButton btnSubmitReview;
    private FoodDB db;
    private int orderId;
    private ProductReviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_review_order);

        db = FoodDB.getInstance(this);

        // Nhận Order ID từ Intent
        String orderIdStr = getIntent().getStringExtra("ORDER_ID");
        if (orderIdStr != null) {
            if (orderIdStr.startsWith("#")) {
                orderId = Integer.parseInt(orderIdStr.substring(1));
            } else {
                try {
                    orderId = Integer.parseInt(orderIdStr);
                } catch (NumberFormatException e) {
                    orderId = 0;
                }
            }
        }

        initViews();
        loadOrderData();
        setupEvents();
    }

    private void initViews() {
        btnClose = findViewById(R.id.btnClose);
        tvReceiverName = findViewById(R.id.tvReceiverName);
        tvAddress = findViewById(R.id.tvAddress);
        tvDeliveryTime = findViewById(R.id.tvDeliveryTime);
        rvProductReviews = findViewById(R.id.rvProductReviews);
        btnSubmitReview = findViewById(R.id.btnSubmitReview);

        rvProductReviews.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadOrderData() {
        OrdersEntity order = db.ordersDAO().getOrderById(orderId);
        if (order != null) {
            tvReceiverName.setText(order.getCustomerName());
            tvAddress.setText(order.getDeliveryAddress());
            tvDeliveryTime.setText(order.getOrderDate());
        }

        List<OrderDetailsDAO.OrderDetailWithFood> productList = db.orderDetailsDAO().getDetailsWithFoodByOrderId(orderId);
        adapter = new ProductReviewAdapter(this, productList);
        rvProductReviews.setAdapter(adapter);
    }

    private void setupEvents() {
        btnClose.setOnClickListener(v -> finish());

        btnSubmitReview.setOnClickListener(v -> {
            saveReviewsToDatabase();
        });
    }

    private void saveReviewsToDatabase() {
        if (adapter == null) return;

        // Lấy userId từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        int currentUserId = sharedPreferences.getInt("userId", -1);

        if (currentUserId == -1) {
            Toast.makeText(this, "Vui lòng đăng nhập để đánh giá", Toast.LENGTH_SHORT).show();
            return;
        }

        List<OrderDetailsDAO.OrderDetailWithFood> productList = adapter.getProductList();
        String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

        try {
            for (int i = 0; i < productList.size(); i++) {
                OrderDetailsDAO.OrderDetailWithFood product = productList.get(i);
                int rating = (int) adapter.getRating(i);
                String comment = adapter.getComment(i);

                ProductReviewEntity review = new ProductReviewEntity(
                        currentUserId,
                        product.food_id,
                        orderId,
                        rating,
                        comment,
                        null, // imageUrl
                        currentDate
                );

                db.productReviewDAO().insertReview(review);
            }

            Toast.makeText(this, "Cảm ơn bạn đã đánh giá đơn hàng!", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi khi lưu đánh giá: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}