package com.example.nhom33.controller;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom33.R;

public class PaymentSuccessActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Kết nối với file layout XML của trang thành công
        setContentView(R.layout.activity_payment_success);
    }
}