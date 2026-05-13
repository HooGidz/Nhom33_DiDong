package com.example.nhom33.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.nhom33.R;

public class MainProfile extends AppCompatActivity {
    // 1. Đổi kiểu dữ liệu thành View (vì container là RelativeLayout)
    View btnInfo, btnAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        // 2. Sử dụng ID của container để vùng bấm được rộng hơn (cả dòng)
        btnInfo = findViewById(R.id.btn_info_container);

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainProfile.this, EditProfile.class);
                startActivity(intent);
            }
        });

        btnAddress = findViewById(R.id.btn_address_container);
        btnAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainProfile.this, AddressActivity.class);
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
