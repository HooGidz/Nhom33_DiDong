package com.example.nhom33.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.nhom33.R;

public class MainProfile extends AppCompatActivity {
    View btnInfo, btnAddress, btnOrderHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        // Thông tin cá nhân
        btnInfo = findViewById(R.id.btn_info_container);
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainProfile.this, EditProfile.class);
                startActivity(intent);
            }
        });

        // Địa chỉ
        btnAddress = findViewById(R.id.btn_address_container);
        btnAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainProfile.this, AddressActivity.class);
                startActivity(intent);
            }
        });

        // Lịch sử giao hàng -> Chuyển sang MainOnOrder
        btnOrderHistory = findViewById(R.id.btn_order_history_container);
        btnOrderHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainProfile.this, MainOnOrder.class);
                startActivity(intent);
            }
        });

        CardView btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
