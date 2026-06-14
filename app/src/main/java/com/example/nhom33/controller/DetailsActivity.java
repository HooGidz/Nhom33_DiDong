package com.example.nhom33.controller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom33.DataEntity.FoodsEntity;
import com.example.nhom33.Database.FoodDB;
import com.example.nhom33.R;

public class DetailsActivity extends AppCompatActivity {

    private ImageView imgProduct;
    private TextView tvMainTitle, tvDescription, tvPrice;
    private FoodDB db;
    private int foodId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Khởi tạo View
        imgProduct = findViewById(R.id.imgProduct);
        tvMainTitle = findViewById(R.id.tvMainTitle);
        tvDescription = findViewById(R.id.tvDescription);
        tvPrice = findViewById(R.id.tvPrice);
        ImageButton btnBack = findViewById(R.id.btnBack);
        Button btnAddToCart = findViewById(R.id.btn_add_to_cart);

        db = FoodDB.getInstance(this);

        // Lấy FOOD_ID từ Intent
        foodId = getIntent().getIntExtra("FOOD_ID", -1);

        if (foodId != -1) {
            loadFoodDetails();
        }

        btnBack.setOnClickListener(v -> finish());

        btnAddToCart.setOnClickListener(v -> {
            // Logic thêm vào giỏ hàng (nếu bạn đã có class Cart hoặc Add_Cart)
            // Intent intent = new Intent(DetailsActivity.this, Add_Cart.class);
            // intent.putExtra("FOOD_ID", foodId);
            // startActivity(intent);
        });
    }

    private void loadFoodDetails() {
        new Thread(() -> {
            FoodsEntity food = db.foodsDAO().getFoodById(foodId);
            if (food != null) {
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
                            imgProduct.setImageResource(R.drawable.pizza_img); // Ảnh mặc định
                        }
                    }
                });
            }
        }).start();
    }
}
