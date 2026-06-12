package com.example.nhom33.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.example.nhom33.R;
import com.example.nhom33.adapter.AddressAdapter;
import com.example.nhom33.db.Address;

public class AddressActivity extends AppCompatActivity {
    View btnAddNew, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_activiti);

        // 1. Tìm RecyclerView từ XML
        RecyclerView rvAddress = findViewById(R.id.rvAddress);
        btnAddNew = findViewById(R.id.btnAddNew);
        btnBack = findViewById(R.id.btnBack);
        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(AddressActivity.this, MapActivity.class);
            startActivity(intent);
        }});
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }});

        // 2. Chuẩn bị dữ liệu mẫu
        List<Address> data = new ArrayList<>();
        data.add(new Address("Nhà ở", "172 Nguyễn Trãi, Thanh Xuân, Hà Nội"));
        data.add(new Address("Nơi làm việc", "65 Lê Duẩn, Thanh Xuân, Hà Nội"));

        // 3. Thiết lập LayoutManager (dạng danh sách dọc)
        rvAddress.setLayoutManager(new LinearLayoutManager(this));

        // 4. Khởi tạo và gán Adapter
        AddressAdapter adapter = new AddressAdapter(data);
        rvAddress.setAdapter(adapter);
    }

}
