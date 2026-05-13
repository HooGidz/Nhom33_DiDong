package com.example.nhom33.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom33.R;

public class Login extends AppCompatActivity {

    // Khai báo các thành phần giao diện
    private EditText edtEmail, edtPassword;
    private Button btnLogin;
    private TextView txtSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Kết nối file XML (View) vào Activity này
        setContentView(R.layout.login);

        // Ánh xạ ID từ XML sang Java
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtSignUp = findViewById(R.id.txtSignUp);

        // Xử lý sự kiện khi nhấn nút Login
        btnLogin.setOnClickListener(v -> {
            String email = edtEmail.getText().toString();
            String password = edtPassword.getText().toString();

            // Logic kiểm tra đăng nhập (đây là phần của Controller)
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            }
            else {
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
            }
        });


        // Xử lý sự kiện khi nhấn vào chữ ĐĂNG KÝ
        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển từ Login sang SignUpActivity
                Intent intent = new Intent(Login.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}