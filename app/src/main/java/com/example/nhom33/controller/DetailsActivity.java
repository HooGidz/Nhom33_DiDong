package com.example.nhom33.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nhom33.DataEntity.CartEntity;
import com.example.nhom33.DataEntity.FavoritesEntity;
import com.example.nhom33.DataEntity.FoodsEntity;
import com.example.nhom33.DataEntity.ProductReviewWithDetails;
import com.example.nhom33.Database.FoodDB;
import com.example.nhom33.R;
import com.example.nhom33.adapter.ReviewAdapter;

import java.util.List;
import java.util.Locale;

public class DetailsActivity extends AppCompatActivity {

    private ImageView imgProduct;
    private TextView tvMainTitle, tvDescription, tvPrice, tvSizeLabel, tvQuantity;
    private RecyclerView rcvReviews;
    private ReviewAdapter reviewAdapter;

    private ImageButton btnFavorite;
    private FoodDB db;
    private int foodId;
    private int quantity = 1;
    private double currentPrice = 0;
    private int userId;
    private boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        db = FoodDB.getInstance(this);

        // Khởi tạo View
        imgProduct = findViewById(R.id.imgProduct);
        tvMainTitle = findViewById(R.id.tvMainTitle);
        tvDescription = findViewById(R.id.tvDescription);
        tvPrice = findViewById(R.id.tvPrice);
        tvSizeLabel = findViewById(R.id.tvSizeLabel);
        rcvReviews = findViewById(R.id.rcvReviews);
        tvQuantity = findViewById(R.id.tvQuantity);
        btnFavorite = findViewById(R.id.btnFavorite);

        ImageButton btnBack = findViewById(R.id.btnBack);
        ImageButton btnPlus = findViewById(R.id.btnPlus);
        ImageButton btnMinus = findViewById(R.id.btnMinus);
        Button btnAddToCart = findViewById(R.id.btn_add_to_cart);

        // Cấu hình RecyclerView cho đánh giá
        rcvReviews.setLayoutManager(new LinearLayoutManager(this));

        // Lấy FOOD_ID từ Intent
        foodId = getIntent().getIntExtra("FOOD_ID", -1);

        // Lấy userId từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);

        if (foodId != -1) {
            loadFoodDetails();
            loadReviews();
            if (userId != -1) {
                checkIsFavorite();
            }
        }

        // Sự kiện nút Back
        btnBack.setOnClickListener(v -> finish());

        // Tăng số lượng
        btnPlus.setOnClickListener(v -> {
            quantity++;
            tvQuantity.setText(String.valueOf(quantity));
        });

        // Giảm số lượng
        btnMinus.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                tvQuantity.setText(String.valueOf(quantity));
            }
        });

        // Nút Yêu thích
        btnFavorite.setOnClickListener(v -> toggleFavorite());

        // Nút Thêm vào giỏ hàng
        btnAddToCart.setOnClickListener(v -> addToCart());
    }

    private void checkIsFavorite() {
        new Thread(() -> {
            FavoritesEntity fav = db.favoritesDAO().getFavorite(userId, foodId);
            isFavorite = (fav != null);
            runOnUiThread(this::updateFavoriteUI);
        }).start();
    }

    private void toggleFavorite() {
        if (userId == -1) {
            Toast.makeText(this, "Vui lòng đăng nhập để thực hiện", Toast.LENGTH_SHORT).show();
            return;
        }
        new Thread(() -> {
            try {
                if (isFavorite) {
                    db.favoritesDAO().deleteFavorite(userId, foodId);
                } else {
                    db.favoritesDAO().insertFavorite(new FavoritesEntity(userId, foodId));
                }
                isFavorite = !isFavorite;
                runOnUiThread(() -> {
                    updateFavoriteUI();
                    Toast.makeText(this, isFavorite ? "Đã thêm vào yêu thích" : "Đã xóa khỏi yêu thích", Toast.LENGTH_SHORT).show();
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void updateFavoriteUI() {
        if (isFavorite) {
            btnFavorite.setColorFilter(Color.RED);
        } else {
            btnFavorite.setColorFilter(Color.parseColor("#828282"));
        }
    }

    private void loadFoodDetails() {
        new Thread(() -> {
            FoodsEntity food = db.foodsDAO().getFoodById(foodId);
            if (food != null) {
                currentPrice = food.getPrice();
                runOnUiThread(() -> {
                    tvMainTitle.setText(food.getFoodName());
                    tvDescription.setText(food.getDescription());

                    // Hiển thị giá (ưu tiên giá khuyến mãi nếu có)
                    double price = (food.getPriceSale() != null && food.getPriceSale() > 0) ? food.getPriceSale() : food.getPrice();
                    tvPrice.setText(String.format(Locale.getDefault(), "%,.0f VNĐ", price));

                    // Cập nhật thông tin Size
                    if (food.getSize() != null && !food.getSize().isEmpty()) {
                        tvSizeLabel.setText("Kích thước: " + food.getSize());
                    } else {
                        tvSizeLabel.setText("Kích thước: Cả ngày");
                    }

                    // Load ảnh từ assets bằng Glide
                    String imgName = food.getImageUrl();
                    String fullPath = "file:///android_asset/img_product/" + imgName;

                    Glide.with(this)
                            .load(fullPath)
                            .placeholder(R.drawable.pizza_img)
                            .error(R.drawable.pizza_img)
                            .into(imgProduct);
                });
            }
        }).start();
    }

    private void addToCart() {
        if (userId == -1) {
            Toast.makeText(this, "Vui lòng đăng nhập để mua hàng", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Login.class));
            return;
        }

        new Thread(() -> {
            CartEntity existingItem = db.cartDAO().getCartItem(userId, foodId);
            if (existingItem != null) {
                existingItem.setQuantity(existingItem.getQuantity() + quantity);
                db.cartDAO().update(existingItem);
            } else {
                CartEntity newItem = new CartEntity(userId, foodId, quantity, currentPrice);
                db.cartDAO().insert(newItem);
            }
            runOnUiThread(() -> {
                Toast.makeText(this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(DetailsActivity.this, Cart.class));
                finish();
            });
        }).start();
    }

    private void loadReviews() {
        new Thread(() -> {
            List<ProductReviewWithDetails> reviews = db.productReviewDAO().getReviewsByFoodId(foodId);
            runOnUiThread(() -> {
                reviewAdapter = new ReviewAdapter(reviews);
                rcvReviews.setAdapter(reviewAdapter);
            });
        }).start();
    }
}