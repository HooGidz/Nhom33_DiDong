package com.example.nhom33.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom33.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        Button btnSignUp = findViewById(R.id.btnGoToAdmin);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Admin_Dashboard.class);
                startActivity(intent);
            }
        });

        // 2. Xử lý nút chuyển đến Trang chủ cũ (Home)
        Button btnHome = findViewById(R.id.btnGoToHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TrangChuActivity.class);
                startActivity(intent);
            }
        });

        // 3. Xử lý nút chuyển đến Danh Sách Burger (MỚI)
        // Đảm bảo bạn đã thêm nút này vào activity_main.xml với id là btnGoToBurgerList
//        Button btnBurgerList = findViewById(R.id.btnGoToBurgerList);
//        btnBurgerList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Chuyển từ MainActivity sang DanhSachBurgerActivity
//                Intent intent = new Intent(MainActivity.this, DanhSachBurgerActivity.class);
//                startActivity(intent);
//            }
//        });
        // 4. Xử lý nút chuyển đến trang Chi tiết Pizza (Details)
        // Đảm bảo bạn đã thêm Button vào layout với ID là btnGoToDetails
//        Button btnDetails = findViewById(R.id.btnGoToDetails);
//        btnDetails.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Chuyển từ MainActivity sang activity_details (DetailsActivity)
//                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
//                startActivity(intent);
//            }
//        });
    }
}