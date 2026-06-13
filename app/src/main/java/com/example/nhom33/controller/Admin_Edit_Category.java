package com.example.nhom33.controller;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom33.Database.FoodDB;
import com.example.nhom33.DataEntity.CategoriesEntity;
import com.example.nhom33.R;

public class Admin_Edit_Category extends AppCompatActivity {

    private EditText edtCategoryName, edtDescription, edtImageUrl;
    private Button btnUpdateCategory;
    private ImageButton btnBack;
    private int categoryId;
    private CategoriesEntity currentCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_edit_category);

        // Get Category ID from Intent
        categoryId = getIntent().getIntExtra("category_id", -1);
        if (categoryId == -1) {
            Toast.makeText(this, "Không tìm thấy mã danh mục!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        initViews();
        loadCategoryData();

        btnBack.setOnClickListener(v -> finish());

        btnUpdateCategory.setOnClickListener(v -> {
            updateCategory();
        });
    }

    private void initViews() {
        edtCategoryName = findViewById(R.id.edt_category_name);
        edtDescription = findViewById(R.id.edt_description);
        edtImageUrl = findViewById(R.id.edt_image_url);
        btnUpdateCategory = findViewById(R.id.btn_update_category);
        btnBack = findViewById(R.id.btn_back);
    }

    private void loadCategoryData() {
        // Room configured with allowMainThreadQueries in FoodDB
        currentCategory = FoodDB.getInstance(this).categoriesDAO().getCategoryById(categoryId);
        if (currentCategory != null) {
            edtCategoryName.setText(currentCategory.getCategoryName());
            edtDescription.setText(currentCategory.getDescription());
            edtImageUrl.setText(currentCategory.getImageUrl());
        } else {
            Toast.makeText(this, "Không tìm thấy danh mục!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void updateCategory() {
        String name = edtCategoryName.getText().toString().trim();
        String description = edtDescription.getText().toString().trim();
        String imageUrl = edtImageUrl.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            edtCategoryName.setError("Tên danh mục không được để trống");
            return;
        }

        try {
            currentCategory.setCategoryName(name);
            currentCategory.setDescription(description);
            currentCategory.setImageUrl(imageUrl);

            FoodDB.getInstance(this).categoriesDAO().updateCategory(currentCategory);

            Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi cập nhật: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
