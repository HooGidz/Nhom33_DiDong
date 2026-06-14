package com.example.nhom33.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom33.Database.FoodDB;
import com.example.nhom33.DataEntity.FoodsEntity;
import com.example.nhom33.DataEntity.CategoriesEntity;
import com.example.nhom33.R;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;
import java.util.List;

public class AdminEditFoodActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private ImageView imgFoodPreview;
    private EditText edtFoodImageUrl, edtFoodName, edtFoodPrice, edtServingTime;
    private EditText edtFoodDescription, edtFoodPriceSale;
    private Spinner spnCategory;
    private SwitchMaterial swIsAvailable;
    private Button btnUpdateFood;

    private FoodDB db;
    private FoodsEntity currentFood;
    private int foodId = -1;
    private List<CategoriesEntity> categoryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_food);

        // Khởi tạo Room Database instance
        db = FoodDB.getInstance(this);

        // Ánh xạ View từ XML
        btnBack = findViewById(R.id.btn_back);
        imgFoodPreview = findViewById(R.id.img_food_preview);
        edtFoodImageUrl = findViewById(R.id.edt_food_image_url);
        edtFoodName = findViewById(R.id.edt_food_name);
        edtFoodPrice = findViewById(R.id.edt_food_price);
        edtServingTime = findViewById(R.id.edt_serving_time);
        
        edtFoodDescription = findViewById(R.id.edt_food_description);
        edtFoodPriceSale = findViewById(R.id.edt_food_price_sale);
        spnCategory = findViewById(R.id.spn_category);
        swIsAvailable = findViewById(R.id.sw_is_available);
        
        btnUpdateFood = findViewById(R.id.btn_update_food);

        // Tải danh sách danh mục vào Spinner
        loadCategories();

        // 1. Nhận food_id truyền từ Adapter sang
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("FOOD_ID")) {
            foodId = intent.getIntExtra("FOOD_ID", -1);

            // 2. Lấy dữ liệu món ăn gốc từ Room Database
            new Thread(() -> {
                currentFood = db.foodsDAO().getFoodById(foodId);

                if (currentFood != null) {
                    runOnUiThread(() -> {
                        edtFoodName.setText(currentFood.getFoodName());
                        edtFoodPrice.setText(String.valueOf((int) currentFood.getPrice()));
                        edtFoodImageUrl.setText(currentFood.getImageUrl());
                        edtServingTime.setText(currentFood.getMealType());
                        edtFoodDescription.setText(currentFood.getDescription());
                        
                        if (currentFood.getPriceSale() != null) {
                            edtFoodPriceSale.setText(String.valueOf(currentFood.getPriceSale().intValue()));
                        }
                        
                        swIsAvailable.setChecked(currentFood.getIsAvailable() == 1);
                        
                        // Chọn danh mục tương ứng trong Spinner
                        setSpinnerSelection();
                        
                        updateImagePreview(currentFood.getImageUrl());
                    });
                }
            }).start();
        }

        btnBack.setOnClickListener(v -> finish());

        // 3. Xử lý logic khi bấm nút CẬP NHẬT MÓN ĂN
        btnUpdateFood.setOnClickListener(v -> {
            String updatedName = edtFoodName.getText().toString().trim();
            String updatedPriceStr = edtFoodPrice.getText().toString().trim();
            String updatedDescription = edtFoodDescription.getText().toString().trim();
            String updatedPriceSaleStr = edtFoodPriceSale.getText().toString().trim();
            String updatedTime = edtServingTime.getText().toString().trim();
            String updatedUrl = edtFoodImageUrl.getText().toString().trim();
            int updatedIsAvailable = swIsAvailable.isChecked() ? 1 : 0;

            // Kiểm tra danh mục đã được chọn chưa
            if (categoryList.isEmpty() || spnCategory.getSelectedItemPosition() == Spinner.INVALID_POSITION) {
                Toast.makeText(this, "Vui lòng chọn danh mục!", Toast.LENGTH_SHORT).show();
                return;
            }
            int updatedCategoryId = categoryList.get(spnCategory.getSelectedItemPosition()).getCategoryId();

            // Kiểm tra không để trống dữ liệu cốt lõi
            if (updatedName.isEmpty() || updatedPriceStr.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ tên và giá!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (currentFood != null) {
                try {
                    double updatedPrice = Double.parseDouble(updatedPriceStr);
                    Double updatedPriceSale = updatedPriceSaleStr.isEmpty() ? null : Double.parseDouble(updatedPriceSaleStr);

                    currentFood.setFoodName(updatedName);
                    currentFood.setPrice(updatedPrice);
                    currentFood.setPriceSale(updatedPriceSale);
                    currentFood.setCategoryId(updatedCategoryId);
                    currentFood.setDescription(updatedDescription);
                    currentFood.setMealType(updatedTime);
                    currentFood.setImageUrl(updatedUrl);
                    currentFood.setIsAvailable(updatedIsAvailable);

                    new Thread(() -> {
                        db.foodsDAO().updateFood(currentFood);
                        runOnUiThread(() -> {
                            Toast.makeText(AdminEditFoodActivity.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                            finish();
                        });
                    }).start();

                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Dữ liệu số không hợp lệ!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadCategories() {
        new Thread(() -> {
            categoryList = db.categoriesDAO().getAllCategories();
            List<String> categoryNames = new ArrayList<>();
            for (CategoriesEntity cat : categoryList) {
                categoryNames.add(cat.getCategoryName());
            }

            runOnUiThread(() -> {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, 
                        android.R.layout.simple_spinner_item, categoryNames);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnCategory.setAdapter(adapter);
                
                // Sau khi nạp xong danh mục, thử chọn lại đúng danh mục của món ăn
                setSpinnerSelection();
            });
        }).start();
    }

    private void setSpinnerSelection() {
        if (currentFood == null || categoryList.isEmpty()) return;
        for (int i = 0; i < categoryList.size(); i++) {
            if (categoryList.get(i).getCategoryId() == currentFood.getCategoryId()) {
                spnCategory.setSelection(i);
                break;
            }
        }
    }

    private void updateImagePreview(String imgName) {
        if (imgName != null && !imgName.isEmpty()) {
            String fileName = imgName.contains(".") ? imgName.substring(0, imgName.lastIndexOf(".")) : imgName;
            int resId = getResources().getIdentifier(fileName, "drawable", getPackageName());
            if (resId != 0) {
                imgFoodPreview.setImageResource(resId);
            }
        }
    }
}