package com.example.nhom33.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom33.R;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Kết nối file Java này với giao diện activity_details.xml
        setContentView(R.layout.activity_details);

        if (getIntent().hasExtra("name")) {
            String name = getIntent().getStringExtra("name");
            String price = getIntent().getStringExtra("price");
            int image = getIntent().getIntExtra("image", 0);
        }

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button btn_add_to_cart = findViewById(R.id.btn_add_to_cart);
        btn_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(DetailsActivity.this, Cart.class);
                startActivity(intent);
            }
        });
    }
}