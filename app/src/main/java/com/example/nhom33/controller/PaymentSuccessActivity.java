package com.example.nhom33.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom33.R;

public class PaymentSuccessActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Kết nối với file layout XML của trang thành công
        setContentView(R.layout.activity_payment_success);

        // Đưa việc ánh xạ và xử lý sự kiện vào trong onCreate
        Button btnTrackOrder = findViewById(R.id.btnTrackOrder);
        btnTrackOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo Intent để chuyển sang Activity theo dõi đơn hàng
                Intent intent = new Intent(PaymentSuccessActivity.this, Main_track_order.class);
                startActivity(intent);
            }
        });
    }
}