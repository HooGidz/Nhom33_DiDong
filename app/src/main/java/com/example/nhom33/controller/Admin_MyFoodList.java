package com.example.nhom33.controller;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import java.util.ArrayList;
import java.util.List;

public class Admin_MyFoodList extends AppCompatActivity {

    RecyclerView recyclerView;
    List<item_food> itemList;
    Admin_FoodList_Adapter myAdapter;

    ImageButton btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.admin_my_food_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        itemList = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        myAdapter = new Admin_FoodList_Adapter(itemList);
        recyclerView.setAdapter(myAdapter);

        // Fetch dữ liệu từ Room Database
        loadFoodsFromDatabase();
    }

    private void loadFoodsFromDatabase() {
        // Lấy dữ liệu từ database trên background thread
        new Thread(() -> {
            try {
                FoodDB db = FoodDB.getInstance(this);
                List<FoodsEntity> foodsEntities = db.foodsDAO().getAllFoods();

                // Log kiểm tra
                Log.d("Admin_MyFoodList", "Số lượng foods lấy được: " + foodsEntities.size());
                for (FoodsEntity entity : foodsEntities) {
                    Log.d("Admin_MyFoodList", "Food: " + entity.getFoodName() + " - Price: " + entity.getPrice());
                }

                // Chuyển đổi danh sách FoodsEntity sang item_food
                List<item_food> foods = new ArrayList<>();
                for (FoodsEntity entity : foodsEntities) {
                    String priceFormatted = String.format("%.0f VND", entity.getPrice());
                    item_food item = new item_food(
                            R.drawable.pizza_img, // Drawable mặc định (có thể cập nhật dựa trên imageUrl)
                            entity.getFoodName(),
                            priceFormatted,
                            entity.getMealType()
                    );
                    foods.add(item);
                }

                // Cập nhật RecyclerView trên UI thread
                runOnUiThread(() -> {
                    itemList.clear();
                    itemList.addAll(foods);
                    myAdapter.notifyDataSetChanged();

                    // Hiện Toast thông báo
                    Toast.makeText(Admin_MyFoodList.this,
                            "Đã tải " + foods.size() + " món ăn",
                            Toast.LENGTH_SHORT).show();

                    Log.d("Admin_MyFoodList", "RecyclerView đã cập nhật với " + foods.size() + " items");
                });
            } catch (Exception e) {
                Log.e("Admin_MyFoodList", "Lỗi khi tải dữ liệu: " + e.getMessage(), e);
                runOnUiThread(() -> {
                    Toast.makeText(Admin_MyFoodList.this,
                            "Lỗi: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                });
            }
        }).start();
    }
}
