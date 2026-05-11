package com.example.nhom33.controller; // Thay bằng package name của bạn

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import com.example.nhom33.R;
import com.example.nhom33.adapter.ProfileAdapter;
import com.example.nhom33.database.Profile;

public class MainProfile extends AppCompatActivity {

    private RecyclerView rvGroup1, rvGroup2, rvGroup3;
    private ProfileAdapter adapter1, adapter2, adapter3;
    private ImageButton btnBack, btnMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        // 1. Ánh xạ View
        initViews();

        // 2. Thiết lập dữ liệu và Adapter
        setupRecyclerViews();

        // 3. Xử lý sự kiện nút bấm Toolba
    }

    private void initViews() {
        rvGroup1 = findViewById(R.id.rvGroup1);
        rvGroup2 = findViewById(R.id.rvGroup2);
        rvGroup3 = findViewById(R.id.rvGroup3); // Nếu bạn có thêm nhóm thứ 3 (FAQs...)

    }

    private void setupRecyclerViews() {
        // --- Nhóm 1: Personal & Addresses ---
        List<Profile> group1Data = new ArrayList<>();
        group1Data.add(new Profile("Personal Info", R.drawable.ic_launcher_foreground));
        group1Data.add(new Profile("Addresses", R.drawable.ic_launcher_foreground));

        adapter1 = new ProfileAdapter(this, group1Data);
        rvGroup1.setLayoutManager(new LinearLayoutManager(this));
        rvGroup1.setAdapter(adapter1);

        // --- Nhóm 2: Shopping & Notifications ---
        List<Profile> group2Data = new ArrayList<>();
        group2Data.add(new Profile("Cart", R.drawable.ic_launcher_foreground));
        group2Data.add(new Profile("Favourite", R.drawable.ic_launcher_foreground));
        group2Data.add(new Profile("Notifications", R.drawable.ic_launcher_foreground));
        group2Data.add(new Profile("Payment Method", R.drawable.ic_launcher_foreground));

        adapter2 = new ProfileAdapter(this, group2Data);
        rvGroup2.setLayoutManager(new LinearLayoutManager(this));
        rvGroup2.setAdapter(adapter2);

        // --- Nhóm 3: Others & Logout ---
        List<Profile> group3Data = new ArrayList<>();
        group3Data.add(new Profile("FAQs", R.drawable.ic_launcher_foreground));
        group3Data.add(new Profile("User Reviews", R.drawable.ic_launcher_foreground));
        group3Data.add(new Profile("Settings", R.drawable.ic_launcher_foreground));
        group3Data.add(new Profile("Log Out", R.drawable.ic_launcher_foreground));

        adapter3 = new ProfileAdapter(this, group3Data);
        rvGroup3.setLayoutManager(new LinearLayoutManager(this));
        rvGroup3.setAdapter(adapter3);
    }
}