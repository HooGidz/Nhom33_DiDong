package com.example.nhom33.controller;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom33.R;
import com.example.nhom33.adapter.ReviewAdapter;
import com.example.nhom33.Database.FoodDB;
import com.example.nhom33.DataEntity.ProductReviewEntity;
import com.example.nhom33.DataEntity.ProductReviewWithDetails;

import java.util.ArrayList;
import java.util.List;

public class ReviewActivity extends AppCompatActivity {

    private RecyclerView recyclerViewReview;
    private ReviewAdapter reviewAdapter;
    private List<ProductReviewWithDetails> reviewList; 
    private FoodDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        // Ánh xạ RecyclerView
        recyclerViewReview = findViewById(R.id.recyclerViewReview);
        reviewList = new ArrayList<>();

        // Xử lý nút Back
        androidx.cardview.widget.CardView btnBackHeader = findViewById(R.id.btnBackHeader);
        if (btnBackHeader != null) {
            btnBackHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        // Khởi tạo Database
        db = FoodDB.getInstance(this);

        // Chạy luồng phụ để lấy dữ liệu
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 1. Lấy dữ liệu kèm thông tin Tên người dùng và Tên món ăn
                List<ProductReviewWithDetails> entities = db.productReviewDAO().getAllReviewsWithDetails();

                // 2. Xử lý nếu danh sách trống (hiển thị dữ liệu mặc định)
                if (entities == null || entities.isEmpty()) {
                    entities = new ArrayList<>();
                    // Tạo một thực thể mẫu
                    ProductReviewWithDetails mock = new ProductReviewWithDetails();
                    mock.review = new ProductReviewEntity(0, 0, 0, 5, 
                            "Món ăn này hiện tại chưa nhận được phản hồi nào từ người dùng.", 
                            null, "14/06/2026");
                    mock.fullName = "Hệ thống";
                    mock.foodName = "Sản phẩm";
                    entities.add(mock);
                }

                final List<ProductReviewWithDetails> resultList = entities;

                // 3. Cập nhật giao diện trên UI Thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        reviewList.clear();
                        reviewList.addAll(resultList);

                        // Khởi tạo adapter với danh sách ReviewWithDetails và cho phép hiển thị options (Admin)
                        reviewAdapter = new ReviewAdapter(reviewList, true);

                        if (recyclerViewReview != null) {
                            recyclerViewReview.setLayoutManager(new LinearLayoutManager(ReviewActivity.this));
                            recyclerViewReview.setAdapter(reviewAdapter);
                        }
                    }
                });
            }
        }).start();
    }
}
