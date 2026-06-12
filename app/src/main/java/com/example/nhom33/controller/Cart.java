package com.example.nhom33.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom33.adapter.Cart_Adapter;
import com.example.nhom33.R;
import com.example.nhom33.db.item_cart;

import java.util.ArrayList;
import java.util.List;

public class Cart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);
        Button btnPlaceOrder = findViewById(R.id.btnPlaceOrder);

        // 2. Thiết lập sự kiện Click cho nút
        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 3. Tạo Intent để chuyển sang Activity mới (ví dụ: PaymentSuccessActivity)
                Intent intent = new Intent(Cart.this, Payment0.class);
                startActivity(intent);
            }
        });
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<item_cart> itemList = new ArrayList<>();
        // Thêm dữ liệu mẫu
        itemList.add(new item_cart("Pizza Calzone European", "14", 64, R.drawable.fba));
        itemList.add(new item_cart("Pizza Calzone European", "14", 32, R.drawable.fba));

        Cart_Adapter adapter = new Cart_Adapter(itemList);
        recyclerView.setAdapter(adapter);
    }
}
