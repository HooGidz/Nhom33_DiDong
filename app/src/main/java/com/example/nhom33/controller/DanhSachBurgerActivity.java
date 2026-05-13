package com.example.nhom33.controller;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom33.R;
import com.example.nhom33.adapter.BurgerAdapter;

import java.util.ArrayList;
import java.util.List;

public class DanhSachBurgerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.danh_sach_burger);

        RecyclerView rvBurgers = findViewById(R.id.rvBurgerList);

        // 1. Thiết lập hiển thị 2 cột (Grid)
        rvBurgers.setLayoutManager(new GridLayoutManager(this, 2));

        // 2. Tạo danh sách dữ liệu mẫu
        List<Burger> list = new ArrayList<>();
        list.add(new Burger("Burger Bistro", "Rose Garden", "$40", R.drawable.food));
        list.add(new Burger("Smokin' Burger", "Cafenio Restaurant", "$60", R.drawable.food));
        list.add(new Burger("Buffalo Burgers", "Kaji Firm Kitchen", "$75", R.drawable.food));
        list.add(new Burger("Bullseye Burgers", "Kabab Restaurant", "$94", R.drawable.food));

        // 3. Gắn Adapter vào RecyclerView
        BurgerAdapter adapter = new BurgerAdapter(list);
        rvBurgers.setAdapter(adapter);
    }
}