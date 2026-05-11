package com.example.nhom33.controller;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import com.example.nhom33.R;
import com.example.nhom33.database.AdProfile;
import com.example.nhom33.adapter.AdProfileAdapter;

public class MainAdProfile extends AppCompatActivity {

    private RecyclerView rvMenu;
    private AdProfileAdapter adapter;
    private List<AdProfile> profileList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Đảm bảo tên file layout này khớp với file activity_main.xml bạn đã tạo
        setContentView(R.layout.adprofile_activity);

        // 1. Ánh xạ View
        initViews();

        // 2. Tạo dữ liệu mẫu
        createData();

        // 3. Thiết lập RecyclerView
        setupRecyclerView();
    }

    private void initViews() {
        rvMenu = findViewById(R.id.rvMenu);
    }

    private void createData() {
        profileList = new ArrayList<>();

        // Sử dụng các icon hệ thống có sẵn của Android
        // Lưu ý: android.R.drawable là icon mặc định, bạn có thể thay bằng icon riêng sau này
        profileList.add(new AdProfile("Thông tin cá nhân", android.R.drawable.ic_menu_myplaces));
        profileList.add(new AdProfile("Cài đặt", android.R.drawable.ic_menu_preferences));
        profileList.add(new AdProfile("Lịch sử rút tiền", android.R.drawable.ic_menu_recent_history));
        profileList.add(new AdProfile("Số lượng đơn hàng", android.R.drawable.ic_menu_agenda));
        profileList.add(new AdProfile("Đánh giá của người dùng", android.R.drawable.ic_menu_manage));
        profileList.add(new AdProfile("Đăng xuất", android.R.drawable.ic_lock_power_off));
    }

    private void setupRecyclerView() {
        // LayoutManager quản lý việc các item hiển thị theo chiều dọc
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvMenu.setLayoutManager(layoutManager);

        // Khởi tạo Adapter với danh sách dữ liệu
        adapter = new AdProfileAdapter(profileList);

        // Kết nối Adapter vào RecyclerView
        rvMenu.setAdapter(adapter);
    }
}
