package com.example.nhom33.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom33.Database.FoodDB;
import com.example.nhom33.DataEntity.FoodsEntity;
import com.example.nhom33.R;
import com.example.nhom33.adapter.BurgerAdapter;
import com.example.nhom33.adapter.DiscountAdapter;

import java.util.ArrayList;
import java.util.List;

public class DanhSachBurgerActivity extends AppCompatActivity {
    private RecyclerView rvBurgers, rvDiscountList;
    private BurgerAdapter adapter;
    private DiscountAdapter discountAdapter;
    private List<FoodsEntity> foodList = new ArrayList<>();
    private List<FoodsEntity> discountList = new ArrayList<>();
    private FoodDB db;
    private int categoryId;
    private String categoryName;
    private String searchQuery;
    private boolean isFavoriteFilter;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.danh_sach_burger);

        db = FoodDB.getInstance(this);

        // 1. Lấy dữ liệu từ Intent
        categoryId = getIntent().getIntExtra("CATEGORY_ID", -1);
        categoryName = getIntent().getStringExtra("CATEGORY_NAME");
        searchQuery = getIntent().getStringExtra("SEARCH_QUERY");
        // Hỗ trợ cả key cũ và mới để đảm bảo tương thích
        isFavoriteFilter = getIntent().getBooleanExtra("FILTER_HIGH_RATING", false) || 
                           getIntent().getBooleanExtra("FILTER_FAVORITES", false);

        // Lấy userId từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);
        if (userId == -1) userId = 1; // Dự phòng cho người dùng đầu tiên

        // 2. Ánh xạ View
        TextView txtTitle = findViewById(R.id.txt_title_category);
        TextView tvTitlePopular = findViewById(R.id.tvTitlePopular);
        ImageButton btnBack = findViewById(R.id.btnBackBurgerList);
        ImageButton btnSearch = findViewById(R.id.btnSearchBurger);
        LinearLayout lnCategorySelector = findViewById(R.id.lnCategorySelector);
        ImageView imgArrow = findViewById(R.id.imgArrowCategory);
        rvBurgers = findViewById(R.id.rvBurgerList);
        rvDiscountList = findViewById(R.id.rvDiscountList);

        // 3. Cập nhật tiêu đề và giao diện dựa trên bộ lọc
        if (isFavoriteFilter) {
            if (txtTitle != null) {
                txtTitle.setText("YÊU THÍCH");
                txtTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22); // Tăng kích thước chữ cho dễ nhìn
            }
            if (tvTitlePopular != null) {
                tvTitlePopular.setText("Danh sách món ăn yêu thích của bạn");
            }
            // Xóa khung (background) và mũi tên ở giao diện yêu thích
            if (lnCategorySelector != null) {
                lnCategorySelector.setBackground(null);
            }
            if (imgArrow != null) {
                imgArrow.setVisibility(View.GONE); // Ẩn biểu tượng tam giác (mũi tên)
            }
            // Xóa biểu tượng kính lúp
            if (btnSearch != null) {
                btnSearch.setVisibility(View.GONE);
            }
        } else if (searchQuery != null && !searchQuery.isEmpty()) {
            if (txtTitle != null) txtTitle.setText("KẾT QUẢ TÌM KIẾM");
            if (tvTitlePopular != null) tvTitlePopular.setText("Kết quả cho: \"" + searchQuery + "\"");
        } else if (categoryName != null) {
            if (txtTitle != null) txtTitle.setText(categoryName.toUpperCase());
            if (tvTitlePopular != null) tvTitlePopular.setText(categoryName + " thịnh hành");
        }

        if (btnBack != null) btnBack.setOnClickListener(v -> finish());

        // 4. Thiết lập RecyclerView chính (Grid 2 cột)
        if (rvBurgers != null) {
            rvBurgers.setLayoutManager(new GridLayoutManager(this, 2));
            adapter = new BurgerAdapter(this, foodList);
            rvBurgers.setAdapter(adapter);
            rvBurgers.setNestedScrollingEnabled(false);
        }

        // 5. Thiết lập RecyclerView giảm giá (Dạng danh sách dọc nhỏ gọn)
        if (rvDiscountList != null) {
            rvDiscountList.setLayoutManager(new LinearLayoutManager(this));
            discountAdapter = new DiscountAdapter(this, discountList);
            rvDiscountList.setAdapter(discountAdapter);
            rvDiscountList.setNestedScrollingEnabled(false);
        }

        // 6. Tải dữ liệu từ database
        loadData();
    }

    private void loadData() {
        new Thread(() -> {
            List<FoodsEntity> listMain;
            if (isFavoriteFilter) {
                // Lấy danh sách yêu thích thực tế của user
                listMain = db.foodsDAO().getFavoriteFoodsByUser(userId);
            } else if (searchQuery != null && !searchQuery.isEmpty()) {
                listMain = db.foodsDAO().searchFoodsByName(searchQuery);
            } else if (categoryId != -1) {
                listMain = db.foodsDAO().getFoodsByCategoryId(categoryId);
            } else {
                listMain = db.foodsDAO().getAllFoods();
            }

            List<FoodsEntity> listDiscount = db.foodsDAO().getDiscountedFoods();

            runOnUiThread(() -> {
                foodList.clear();
                foodList.addAll(listMain);
                if (adapter != null) adapter.notifyDataSetChanged();

                discountList.clear();
                discountList.addAll(listDiscount);
                if (discountAdapter != null) discountAdapter.notifyDataSetChanged();
            });
        }).start();
    }
}
