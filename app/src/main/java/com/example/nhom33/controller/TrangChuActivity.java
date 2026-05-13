package com.example.nhom33.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.nhom33.R;

public class TrangChuActivity extends AppCompatActivity {
    CardView card_food;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trangchu);

        CardView card_food = findViewById(R.id.card_food);
        card_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrangChuActivity.this, DanhSachBurgerActivity.class);
                startActivity(intent);
            }
        });

        ImageButton btn_Profile = findViewById(R.id.btn_Profile);
        btn_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrangChuActivity.this, MainProfile.class);
                startActivity(intent);
            }
        });
    }

}