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
import com.example.nhom33.DataEntity.FoodsEntity;
import com.example.nhom33.Database.FoodDB;
import com.example.nhom33.R;
import com.example.nhom33.adapter.DiscountAdapter;
import com.example.nhom33.adapter.FoodAdapter;
import com.example.nhom33.adapter.HomeCategoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class User_TrangChu extends AppCompatActivity {
    private RecyclerView recyclerViewCategories, recyclerViewDiscount, recyclerViewBestSeller;
    private HomeCategoryAdapter categoryAdapter;
    private DiscountAdapter discountAdapter;
    private FoodAdapter bestSellerAdapter;
    private List<CategoriesEntity> categoryList = new ArrayList<>();
    private List<FoodsEntity> discountList = new ArrayList<>();
    private List<FoodsEntity> bestSellerList = new ArrayList<>();
    private FoodDB db;
    private EditText edtSearchHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_trangchu);

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

        // Thiết lập RecyclerView danh mục (Ngang)
        recyclerViewCategories = findViewById(R.id.recyclerViewCategories);
        if (recyclerViewCategories != null) {
            recyclerViewCategories.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            categoryAdapter = new HomeCategoryAdapter(this, categoryList, category -> {
                Intent intent = new Intent(User_TrangChu.this, User_Food_List.class);
                intent.putExtra("CATEGORY_ID", category.getCategoryId());
                intent.putExtra("CATEGORY_NAME", category.getCategoryName());
                startActivity(intent);
            });
            recyclerViewCategories.setAdapter(categoryAdapter);
        }

        // Thiết lập RecyclerView món bán chạy (Dọc/Lưới tùy nhu cầu, ở đây dùng Dọc để thay thế mục nhà hàng)
        recyclerViewBestSeller = findViewById(R.id.recyclerViewBestSeller);
        if (recyclerViewBestSeller != null) {
            recyclerViewBestSeller.setLayoutManager(new LinearLayoutManager(this));
            bestSellerAdapter = new FoodAdapter(this, bestSellerList);
            recyclerViewBestSeller.setAdapter(bestSellerAdapter);
        }

        // Thiết lập RecyclerView món giảm giá (Ngang)
        recyclerViewDiscount = findViewById(R.id.recyclerViewDiscount);
        if (recyclerViewDiscount != null) {
            recyclerViewDiscount.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            discountAdapter = new DiscountAdapter(this, discountList);
            recyclerViewDiscount.setAdapter(discountAdapter);
        }

        // Tải dữ liệu từ database
        loadCategories();
        loadBestSellingFoods();
        loadDiscountedFoods();

        // Xử lý Profile & Cart
        ImageButton btn_Profile = findViewById(R.id.btn_Profile);
        if (btn_Profile != null) {
            btn_Profile.setOnClickListener(v -> {
                 Intent intent = new Intent(User_TrangChu.this, User_Profile.class);
                 startActivity(intent);
            });
        }
        
        ImageButton btn_Cart = findViewById(R.id.btn_Cart);
        if (btn_Cart != null) {
            btn_Cart.setOnClickListener(v -> {
                Intent intent = new Intent(User_TrangChu.this, User_Cart_List.class);
                startActivity(intent);
            });
        }
    }

    private void performSearch(String query) {
        Intent intent = new Intent(User_TrangChu.this, User_Food_List.class);
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
                    if (categoryAdapter != null) categoryAdapter.notifyDataSetChanged();
                });
            } catch (Exception e) { e.printStackTrace(); }
        }).start();
    }

    private void loadBestSellingFoods() {
        new Thread(() -> {
            try {
                List<FoodsEntity> list = db.foodsDAO().getBestSellingFoods();
                runOnUiThread(() -> {
                    bestSellerList.clear();
                    bestSellerList.addAll(list);
                    if (bestSellerAdapter != null) bestSellerAdapter.notifyDataSetChanged();
                });
            } catch (Exception e) { e.printStackTrace(); }
        }).start();
    }

    private void loadDiscountedFoods() {
        new Thread(() -> {
            try {
                List<FoodsEntity> list = db.foodsDAO().getDiscountedFoods();
                runOnUiThread(() -> {
                    discountList.clear();
                    discountList.addAll(list);
                    if (discountAdapter != null) discountAdapter.notifyDataSetChanged();
                });
            } catch (Exception e) { e.printStackTrace(); }
        }).start();
    }
}
