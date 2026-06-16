package com.example.nhom33.controller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
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
    private EditText edtFoodImageUrl, edtFoodName, edtFoodPrice, edtFoodSize;
    private EditText edtFoodDescription, edtFoodPriceSale;
    private Spinner spnCategory;
    private CheckBox chkIsNew, chkIsBest;
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

        db = FoodDB.getInstance(this);

        btnBack = findViewById(R.id.btn_back);
        imgFoodPreview = findViewById(R.id.img_food_preview);
        edtFoodImageUrl = findViewById(R.id.edt_food_image_url);
        edtFoodName = findViewById(R.id.edt_food_name);
        edtFoodPrice = findViewById(R.id.edt_food_price);
        edtFoodSize = findViewById(R.id.edt_food_size); // Cập nhật từ edt_serving_time
        
        edtFoodDescription = findViewById(R.id.edt_food_description);
        edtFoodPriceSale = findViewById(R.id.edt_food_price_sale);
        spnCategory = findViewById(R.id.spn_category);
        
        chkIsNew = findViewById(R.id.chk_is_new);
        chkIsBest = findViewById(R.id.chk_is_best);
        swIsAvailable = findViewById(R.id.sw_is_available);
        
        btnUpdateFood = findViewById(R.id.btn_update_food);

        loadCategories();

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("FOOD_ID")) {
            foodId = intent.getIntExtra("FOOD_ID", -1);

            new Thread(() -> {
                currentFood = db.foodsDAO().getFoodById(foodId);

                if (currentFood != null) {
                    runOnUiThread(() -> {
                        edtFoodName.setText(currentFood.getFoodName());
                        edtFoodPrice.setText(String.valueOf((int) currentFood.getPrice()));
                        edtFoodImageUrl.setText(currentFood.getImageUrl());
                        edtFoodSize.setText(currentFood.getSize());
                        edtFoodDescription.setText(currentFood.getDescription());
                        
                        if (currentFood.getPriceSale() != null) {
                            edtFoodPriceSale.setText(String.valueOf(currentFood.getPriceSale().intValue()));
                        }
                        
                        chkIsNew.setChecked(currentFood.getIsNew() == 1);
                        chkIsBest.setChecked(currentFood.getIsBest() == 1);
                        swIsAvailable.setChecked(currentFood.getIsAvailable() == 1);
                        
                        setSpinnerSelection();
                        updateImagePreview(currentFood.getImageUrl());
                    });
                }
            }).start();
        }

        btnBack.setOnClickListener(v -> finish());

        btnUpdateFood.setOnClickListener(v -> {
            String updatedName = edtFoodName.getText().toString().trim();
            String updatedPriceStr = edtFoodPrice.getText().toString().trim();
            String updatedDescription = edtFoodDescription.getText().toString().trim();
            String updatedPriceSaleStr = edtFoodPriceSale.getText().toString().trim();
            String updatedSize = edtFoodSize.getText().toString().trim();
            String updatedUrl = edtFoodImageUrl.getText().toString().trim();
            
            int updatedIsNew = chkIsNew.isChecked() ? 1 : 0;
            int updatedIsBest = chkIsBest.isChecked() ? 1 : 0;
            int updatedIsAvailable = swIsAvailable.isChecked() ? 1 : 0;

            if (categoryList.isEmpty() || spnCategory.getSelectedItemPosition() == Spinner.INVALID_POSITION) {
                Toast.makeText(this, "Vui lòng chọn danh mục!", Toast.LENGTH_SHORT).show();
                return;
            }
            int updatedCategoryId = categoryList.get(spnCategory.getSelectedItemPosition()).getCategoryId();

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
                    currentFood.setSize(updatedSize);
                    currentFood.setImageUrl(updatedUrl);
                    currentFood.setIsNew(updatedIsNew);
                    currentFood.setIsBest(updatedIsBest);
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
            String fullPath = "file:///android_asset/imgg_product/" + imgName;
            Glide.with(this)
                    .load(fullPath)
                    .placeholder(R.drawable.fb)
                    .error(R.drawable.fb)
                    .into(imgFoodPreview);
        }
    }
}
