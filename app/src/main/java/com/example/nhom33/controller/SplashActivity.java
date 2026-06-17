package com.example.nhom33.controller;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom33.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Đổi Activity đích từ Load_boarding sang Login để vào thẳng màn hình đăng nhập
                Intent intent = new Intent(SplashActivity.this, Login.class);
                startActivity(intent);
                finish(); // Đóng Splash để không quay lại được bằng nút Back
            }
        }, 3000);
    }
}