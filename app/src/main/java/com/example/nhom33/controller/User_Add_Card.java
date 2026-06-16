package com.example.nhom33.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom33.R;

public class User_Add_Card extends AppCompatActivity {
    ImageButton btn_back;
    // add card
   @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.user_add_card);
        // 1. Ánh xạ nút bấm từ XML vào Java/
        Button btnSubmit = findViewById(R.id.btnSubmit);

         // 2. Thiết lập sự kiện click
          btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // 3. Lệnh chuyển từ Activity hiện tại sang Activity Success
               // Lưu ý: Bạn cần tạo file PaymentSuccessActivity.java tương ứng với layout success
               Intent intent = new Intent(User_Add_Card.this, User_Card_Payment.class);
                startActivity(intent);
            }
        });
       btn_back = findViewById(R.id.btn_back);
       btn_back.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               finish();
           }
       });
       }
}
