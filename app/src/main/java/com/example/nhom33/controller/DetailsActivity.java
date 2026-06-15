package com.example.nhom33.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom33.DataEntity.CartEntity;
import com.example.nhom33.DataEntity.FavoritesEntity;
import com.example.nhom33.DataEntity.FoodsEntity;
import com.example.nhom33.Database.FoodDB;
import com.example.nhom33.R;

public class DetailsActivity extends AppCompatActivity {

    private ImageView imgProduct;
    private TextView tvMainTitle, tvDescription, tvPrice, tvQuantity;

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

        // Khởi tạo View
        imgProduct = findViewById(R.id.imgProduct);
        tvMainTitle = findViewById(R.id.tvMainTitle);
        tvDescription = findViewById(R.id.tvDescription);
        tvPrice = findViewById(R.id.tvPrice);
        tvQuantity = findViewById(R.id.tvQuantity);
        btnFavorite = findViewById(R.id.btnFavorite);
        ImageButton btnBack = findViewById(R.id.btnBack);
        ImageButton btnPlus = findViewById(R.id.btnPlus);
        ImageButton btnMinus = findViewById(R.id.btnMinus);
        Button btnAddToCart = findViewById(R.id.btn_add_to_cart);

        db = FoodDB.getInstance(this);

        // Lấy FOOD_ID từ Intent
        foodId = getIntent().getIntExtra("FOOD_ID", -1);

        // Lấy userId từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);
        if (userId == -1) userId = 1; // Mặc định cho người dùng đầu tiên để test nếu chưa đăng nhập

        if (foodId != -1) {
            loadFoodDetails();
            checkIsFavorite();
        }

        btnBack.setOnClickListener(v -> finish());

        btnPlus.setOnClickListener(v -> {
            quantity++;
            tvQuantity.setText(String.valueOf(quantity));
        });

        btnMinus.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                tvQuantity.setText(String.valueOf(quantity));
            }
        });

        btnFavorite.setOnClickListener(v -> toggleFavorite());

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
                    if (isFavorite) {
                        Toast.makeText(DetailsActivity.this, "Đã thêm vào yêu thích", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DetailsActivity.this, "Đã xóa khỏi yêu thích", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void updateFavoriteUI() {
        if (isFavorite) {
            btnFavorite.setColorFilter(Color.RED); // Chuyển sang màu đỏ
        } else {
            btnFavorite.setColorFilter(Color.parseColor("#828282")); // Màu xám mặc định
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
                    tvPrice.setText(String.format("%,.0f VNĐ", food.getPrice()));

                    // Load ảnh từ drawable
                    String imgName = food.getImageUrl();
                    if (imgName != null && !imgName.isEmpty()) {
                        if (imgName.contains(".")) {
                            imgName = imgName.substring(0, imgName.lastIndexOf("."));
                        }
                        int resId = getResources().getIdentifier(imgName, "drawable", getPackageName());
                        if (resId != 0) {
                            imgProduct.setImageResource(resId);
                        } else {
                            imgProduct.setImageResource(R.drawable.pizza_img);
                        }
                    }
                });
            }
        }).start();
    }

    private void addToCart() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", -1);

        if (userId == -1) {
            Toast.makeText(this, "Vui lòng đăng nhập để thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
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
                // Chuyển sang màn hình giỏ hàng
                Intent intent = new Intent(DetailsActivity.this, Cart.class);
                startActivity(intent);
                finish(); // Đóng màn hình chi tiết
            });
        }).start();
    }
}
