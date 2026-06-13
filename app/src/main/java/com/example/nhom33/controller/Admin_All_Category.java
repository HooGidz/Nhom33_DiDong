package com.example.nhom33.controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom33.adapter.Admin_Category_Adapter;
import com.example.nhom33.Database.FoodDB;
import com.example.nhom33.DataEntity.CategoriesEntity;
import com.example.nhom33.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Admin_All_Category extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<CategoriesEntity> categoryList;
    private Admin_Category_Adapter categoryAdapter;
    private ImageButton btnBack;
    private FloatingActionButton fabAddCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.admin_all_category);
        
        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        initViews();
        setupRecyclerView();
        
        btnBack.setOnClickListener(v -> finish());
        
        // Sự kiện khi nhấn nút thêm mới
        fabAddCategory.setOnClickListener(v -> {
            Intent intent = new Intent(Admin_All_Category.this, Admin_Add_Category.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCategoriesFromDatabase();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btn_back);
        recyclerView = findViewById(R.id.recyclerView);
        fabAddCategory = findViewById(R.id.fab_add_category);
    }

    private void setupRecyclerView() {
        categoryList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        categoryAdapter = new Admin_Category_Adapter(categoryList, new Admin_Category_Adapter.OnCategoryActionListener() {
            @Override
            public void onEdit(CategoriesEntity category) {
                // Chuyển sang màn hình sửa danh mục
                Intent intent = new Intent(Admin_All_Category.this, Admin_Edit_Category.class);
                intent.putExtra("category_id", category.getCategoryId());
                startActivity(intent);
            }

            @Override
            public void onDelete(CategoriesEntity category) {
                showDeleteConfirmDialog(category);
            }
        });
        
        recyclerView.setAdapter(categoryAdapter);
    }

    private void loadCategoriesFromDatabase() {
        new Thread(() -> {
            try {
                FoodDB db = FoodDB.getInstance(this);
                List<CategoriesEntity> categories = db.categoriesDAO().getAllCategories();
                
                runOnUiThread(() -> {
                    categoryList.clear();
                    categoryList.addAll(categories);
                    categoryAdapter.notifyDataSetChanged();
                    Log.d("Admin_All_Category", "Loaded " + categories.size() + " categories");
                });
            } catch (Exception e) {
                Log.e("Admin_All_Category", "Error loading categories", e);
                runOnUiThread(() -> Toast.makeText(this, "Lỗi tải danh mục: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    private void showDeleteConfirmDialog(CategoriesEntity category) {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc chắn muốn xóa danh mục '" + category.getCategoryName() + "' không?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    performDeleteCategory(category);
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void performDeleteCategory(CategoriesEntity category) {
        new Thread(() -> {
            try {
                FoodDB.getInstance(this).categoriesDAO().deleteCategory(category);
                runOnUiThread(() -> {
                    Toast.makeText(this, "Đã xóa danh mục thành công", Toast.LENGTH_SHORT).show();
                    loadCategoriesFromDatabase();
                });
            } catch (Exception e) {
                Log.e("Admin_All_Category", "Error deleting category", e);
                runOnUiThread(() -> Toast.makeText(this, "Lỗi khi xóa: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
}
