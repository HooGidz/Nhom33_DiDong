package com.example.nhom33.controller;

import android.os.Bundle;
import android.view.View;
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
import com.example.nhom33.adapter.Admin_Noti_Adapter;
import com.example.nhom33.database.item_noti;

import java.util.ArrayList;
import java.util.List;

public class Admin_Notification extends AppCompatActivity {

    RecyclerView recyclerView;
    List<item_noti> itemList;
    Admin_Noti_Adapter myAdapter;

    ImageButton btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.admin_notfication);
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
        item_noti it1 = new item_noti(R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, "Hoàng Giáp đã làm  dự án này và đang làm tiếp", "20 phút trước");
        item_noti it2 = new item_noti(R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, "Hoàng Giáp đã làm xong", "20 phút trước");
        item_noti it3 = new item_noti(R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, "Hoàng Giáp đã làm xong", "20 phút trước");
        item_noti it4 = new item_noti(R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, "Hoàng Giáp đã làm xong", "20 phút trước");
        item_noti it5 = new item_noti(R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, "Hoàng Giáp đã làm xong", "20 phút trước");

        itemList.add(it1);
        itemList.add(it2);
        itemList.add(it3);
        itemList.add(it4);
        itemList.add(it5);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        myAdapter = new Admin_Noti_Adapter(itemList);
        recyclerView.setAdapter(myAdapter);

    }
}
