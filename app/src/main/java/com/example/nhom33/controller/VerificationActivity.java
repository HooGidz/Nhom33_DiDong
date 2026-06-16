package com.example.nhom33.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom33.R;

public class VerificationActivity extends AppCompatActivity {
    Button btnVerify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verification);

        btnVerify = findViewById(R.id.btnVerify);
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sau khi xác thực thành công, chuyển sang trang thành công hoặc đăng nhập
                Intent intent = new Intent(VerificationActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
