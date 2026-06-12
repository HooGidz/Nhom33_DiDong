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

public class Admin_Add_Category extends AppCompatActivity {

    private EditText edtCategoryName, edtDescription, edtImageUrl;
    private Button btnAddCategory;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_add_category);

        initViews();

        btnBack.setOnClickListener(v -> finish());

        btnAddCategory.setOnClickListener(v -> {
            saveCategory();
        });
    }

    private void initViews() {
        edtCategoryName = findViewById(R.id.edt_category_name);
        edtDescription = findViewById(R.id.edt_description);
        edtImageUrl = findViewById(R.id.edt_image_url);
        btnAddCategory = findViewById(R.id.btn_add_category);
        btnBack = findViewById(R.id.btn_back);
    }

    private void saveCategory() {
        String name = edtCategoryName.getText().toString().trim();
        String description = edtDescription.getText().toString().trim();
        String imageUrl = edtImageUrl.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            edtCategoryName.setError("Vui lòng nhập tên danh mục");
            return;
        }

        try {
            // Tạo đối tượng Category mới
            CategoriesEntity category = new CategoriesEntity(name, description, imageUrl);

            // Lưu vào database
            FoodDB.getInstance(this).categoriesDAO().insertCategory(category);

            Toast.makeText(this, "Thêm danh mục thành công!", Toast.LENGTH_SHORT).show();
            finish(); // Quay lại màn hình trước

        } catch (Exception e) {
            Toast.makeText(this, "Lỗi khi thêm: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
