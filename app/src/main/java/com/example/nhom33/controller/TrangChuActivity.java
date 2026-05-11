package com.example.nhom33.controller;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom33.R;

public class TrangChuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Dòng này cực kỳ quan trọng để hiện giao diện trangchu.xml của bạn
        setContentView(R.layout.trangchu);
    }
}