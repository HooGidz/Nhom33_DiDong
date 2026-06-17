package com.example.nhom33.controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom33.adapter.Admin_FoodList_Adapter;
import com.example.nhom33.Database.FoodDB;
import com.example.nhom33.DataEntity.FoodsEntity;
import com.example.nhom33.R;
import com.example.nhom33.db.item_food;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class Admin_MyFoodList extends AppCompatActivity {

    RecyclerView recyclerView;
    List<item_food> itemList;
    Admin_FoodList_Adapter myAdapter;
    ImageButton btn_back;
    MaterialButton btn_add_new;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.admin_food_list);
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(v -> finish());

        btn_add_new = findViewById(R.id.btn_add_new);
        btn_add_new.setOnClickListener(v -> {
            Intent intent = new Intent(Admin_MyFoodList.this, Admin_Add_Food.class);
            startActivity(intent);
        });

        recyclerView = findViewById(R.id.recyclerView);
        itemList = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new Admin_FoodList_Adapter(itemList);
        recyclerView.setAdapter(myAdapter);

        loadFoodsFromDatabase();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadFoodsFromDatabase();
    }

    private void loadFoodsFromDatabase() {
        new Thread(() -> {
            try {
                FoodDB db = FoodDB.getInstance(this);
                List<FoodsEntity> foodsEntities = db.foodsDAO().getAllFoods();

                List<item_food> foods = new ArrayList<>();
                for (FoodsEntity entity : foodsEntities) {
                    // Định dạng giá tiền
                    String priceFormatted = String.format("%,.0f VND", entity.getPrice());
                    
                    // Tạo item_food với cấu trúc mới (String imageUrl và String size)
                    item_food item = new item_food(
                            entity.getFoodId(),
                            entity.getImageUrl(), // Ví dụ: "pizza_hs.png"
                            entity.getFoodName(),
                            priceFormatted,
                            entity.getSize()      // Sử dụng getSize() thay cho getMealType()
                    );
                    foods.add(item);
                }

                runOnUiThread(() -> {
                    itemList.clear();
                    itemList.addAll(foods);
                    myAdapter.notifyDataSetChanged();
                    
                    if (foods.isEmpty()) {
                        Toast.makeText(this, "Chưa có món ăn nào trong database", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                Log.e("Admin_MyFoodList", "Lỗi: " + e.getMessage());
            }
        }).start();
    }
}
