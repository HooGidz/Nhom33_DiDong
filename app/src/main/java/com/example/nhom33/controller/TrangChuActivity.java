package com.example.nhom33.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom33.DataEntity.CategoriesEntity;
import com.example.nhom33.Database.FoodDB;
import com.example.nhom33.R;
import com.example.nhom33.adapter.HomeCategoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class TrangChuActivity extends AppCompatActivity {
    private RecyclerView recyclerViewCategories;
    private HomeCategoryAdapter categoryAdapter;
    private List<CategoriesEntity> categoryList = new ArrayList<>();
    private FoodDB db;
    private EditText edtSearchHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trangchu);

        // Khởi tạo Database
        db = FoodDB.getInstance(this);

        // Ánh xạ View tìm kiếm
        edtSearchHome = findViewById(R.id.edtSearchHome);
        if (edtSearchHome != null) {
            edtSearchHome.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH || 
                        (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                        String query = edtSearchHome.getText().toString().trim();
                        if (!query.isEmpty()) {
                            performSearch(query);
                        }
                        return true;
                    }
                    return false;
                }
            });
        }

        // Thiết lập RecyclerView danh mục
        recyclerViewCategories = findViewById(R.id.recyclerViewCategories);
        if (recyclerViewCategories != null) {
            recyclerViewCategories.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            categoryAdapter = new HomeCategoryAdapter(this, categoryList, new HomeCategoryAdapter.OnCategoryClickListener() {
                @Override
                public void onCategoryClick(CategoriesEntity category) {
                    Intent intent = new Intent(TrangChuActivity.this, DanhSachBurgerActivity.class);
                    intent.putExtra("CATEGORY_ID", category.getCategoryId());
                    intent.putExtra("CATEGORY_NAME", category.getCategoryName());
                    startActivity(intent);
                }
            });
            recyclerViewCategories.setAdapter(categoryAdapter);
        }

        // Tải dữ liệu từ database
        loadCategories();

        CardView card_food = findViewById(R.id.card_food);
        if (card_food != null) {
            card_food.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(TrangChuActivity.this, DanhSachBurgerActivity.class);
                    startActivity(intent);
                }
            });
        }

        ImageButton btn_Profile = findViewById(R.id.btn_Profile);
        if (btn_Profile != null) {
            btn_Profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     Intent intent = new Intent(TrangChuActivity.this, MainProfile.class);
                     startActivity(intent);
                }
            });
        }
    }

    private void performSearch(String query) {
        Intent intent = new Intent(TrangChuActivity.this, DanhSachBurgerActivity.class);
        intent.putExtra("SEARCH_QUERY", query);
        startActivity(intent);
    }

    private void loadCategories() {
        new Thread(() -> {
            try {
                List<CategoriesEntity> list = db.categoriesDAO().getAllCategories();
                runOnUiThread(() -> {
                    categoryList.clear();
                    categoryList.addAll(list);
                    if (categoryAdapter != null) {
                        categoryAdapter.notifyDataSetChanged();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
