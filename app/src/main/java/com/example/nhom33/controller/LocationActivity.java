package com.example.nhom33.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom33.MainActivity;
import com.example.nhom33.R;

public class LocationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        Button btnAccess = findViewById(R.id.btnAccessLocation);
        btnAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sau khi cho phép vị trí, chuyển vào MainActivity
                Intent intent = new Intent(LocationActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}