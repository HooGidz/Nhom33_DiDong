package com.example.nhom33.controller;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom33.adapter.Admin_FoodList_Adapter;
import com.example.nhom33.R;
import com.example.nhom33.adapter.Admin_RunOrd_Adapter;
import com.example.nhom33.database.item_running_order;

import java.util.ArrayList;
import java.util.List;

public class Admin_Running_Order extends AppCompatActivity{
    RecyclerView recyclerView;
    List<item_running_order> itemList;
    Admin_RunOrd_Adapter myAdapter;
    ImageButton btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.admin_running_order);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(v -> finish());

        recyclerView = findViewById(R.id.recyclerView);
        itemList = new ArrayList<>();
        item_running_order it1 = new item_running_order(R.drawable.fb, "Gà rán", "ID: 45344", "60.000 VND", "Bữa sáng");
        item_running_order it2 = new item_running_order(R.drawable.fb, "Bánh pizza", "ID: 45344", "120.000 VND", "Bữa tối");
        item_running_order it3 = new item_running_order(R.drawable.fb, "Cơm sườn", "ID: 45344", "60.000 VND", "Bữa trưa");
        item_running_order it4 = new item_running_order(R.drawable.fb, "Gà rán", "ID: 45344", "60.000 VND", "Bữa sáng");
        item_running_order it5 = new item_running_order(R.drawable.fb, "Bánh pizza", "ID: 45344", "120.000 VND", "Bữa tối");

//        item_running_order it1 = new item_running_order(R.drawable.fb, "Gà rán", "ID: 45344", "60.000 VND");
//        item_running_order it2 = new item_running_order(R.drawable.fb, "Bánh pizza", "ID: 45344", "120.000 VND");
//        item_running_order it3 = new item_running_order(R.drawable.fb, "Cơm sườn", "ID: 45344", "60.000 VND");
//        item_running_order it4 = new item_running_order(R.drawable.fb, "Gà rán", "ID: 45344", "60.000 VND");
//        item_running_order it5 = new item_running_order(R.drawable.fb, "Bánh pizza", "ID: 45344", "120.000 VND");

        itemList.add(it1);
        itemList.add(it2);
        itemList.add(it3);
        itemList.add(it4);
        itemList.add(it5);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        myAdapter = new Admin_RunOrd_Adapter(itemList);
        recyclerView.setAdapter(myAdapter);
    }

}
