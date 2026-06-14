package com.example.nhom33.controller;

import android.os.Bundle;
import android.widget.ImageButton;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.danh_sach_burger);

        db = FoodDB.getInstance(this);

        // 1. Lấy dữ liệu từ Intent
        categoryId = getIntent().getIntExtra("CATEGORY_ID", -1);
        categoryName = getIntent().getStringExtra("CATEGORY_NAME");

        // 2. Ánh xạ View
        TextView txtTitle = findViewById(R.id.txt_title_category);
        TextView tvTitlePopular = findViewById(R.id.tvTitlePopular);
        ImageButton btnBack = findViewById(R.id.btnBackBurgerList);
        rvBurgers = findViewById(R.id.rvBurgerList);
        rvDiscountList = findViewById(R.id.rvDiscountList);

        // 3. Cập nhật tiêu đề
        if (categoryName != null) {
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
            if (categoryId != -1) {
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
