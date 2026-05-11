package com.example.nhom33.controller;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom33.R;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Kết nối file Java này với giao diện activity_details.xml
        setContentView(R.layout.activity_details);

        // Xử lý nút quay lại (Back)
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Đóng màn hình này để quay về màn hình trước đó
                finish();
            }
        });
    }
}