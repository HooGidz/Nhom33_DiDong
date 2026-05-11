package com.example.nhom33.controller;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom33.MyAdapter;
import com.example.nhom33.R;
import com.example.nhom33.database.DatabaseHelper;
import com.example.nhom33.database.item_food;

import java.util.ArrayList;
import java.util.List;

public class Admin_MyFoodList extends AppCompatActivity {

    RecyclerView recyclerView;
    List<item_food> itemList;
    MyAdapter myAdapter;

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

//        recyclerView = findViewById(R.id.recyclerView);
//        itemList = new ArrayList<>();
//        item_food it1 = new item_food(R.drawable.ic_launcher_background, "Gà rán", "60.000 VND", "Bữa sáng");
//        item_food it2 = new item_food(R.drawable.ic_launcher_background, "Bánh pizza", "120.000 VND", "Bữa tối");
//        item_food it3 = new item_food(R.drawable.ic_launcher_background, "Cơm sườn", "60.000 VND", "Bữa trưa");
//        item_food it4 = new item_food(R.drawable.ic_launcher_background, "Gà rán", "60.000 VND", "Bữa sáng");
//
//        itemList.add(it1);
//        itemList.add(it2);
//        itemList.add(it3);
//        itemList.add(it4);
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//
//        //myAdapter = new MyAdapter(itemList);
//        recyclerView.setAdapter(myAdapter);

        // Trong Activity của bạn
        recyclerView = findViewById(R.id.recyclerView); // Uncommented
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.openDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM Foods", null);
        List<item_food> list = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                item_food item = new item_food();
                int nameIndex = cursor.getColumnIndex("food_name");
                int priceIndex = cursor.getColumnIndex("price");
                int tagIndex = cursor.getColumnIndex("meal_type");
                int imgIndex = cursor.getColumnIndex("image_url");

                if (nameIndex != -1) item.setTxt_food(cursor.getString(nameIndex));
                if (priceIndex != -1) item.setTxt_price(cursor.getString(priceIndex));
                if (tagIndex != -1) item.setTxt_tag(cursor.getString(tagIndex));
                if (imgIndex != -1) item.setImg_food(cursor.getInt(imgIndex));
                list.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

// Sau đó truyền list này vào MyAdapter
        MyAdapter adapter = new MyAdapter(list);
        recyclerView.setAdapter(adapter);
    }
}
