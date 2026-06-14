package com.example.nhom33.controller;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom33.R;
import com.example.nhom33.adapter.Admin_Noti_Adapter;
import com.example.nhom33.Database.FoodDB;
import com.example.nhom33.DataEntity.NotificationEntity;
import com.example.nhom33.db.item_noti;

import java.util.ArrayList;
import java.util.List;

public class Admin_Notification extends AppCompatActivity {

    RecyclerView recyclerView;
    List<item_noti> itemList;
    Admin_Noti_Adapter myAdapter;

    ImageButton btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.admin_notfication);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        itemList = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        myAdapter = new Admin_Noti_Adapter(itemList);
        recyclerView.setAdapter(myAdapter);

        // Fetch dữ liệu từ Room Database
        loadNotificationsFromDB();
    }

    private void loadNotificationsFromDB() {
        // Lấy dữ liệu từ database trên background thread
        new Thread(() -> {
            try {
                FoodDB db = FoodDB.getInstance(this);
                List<NotificationEntity> dbNotifications = db.notificationDAO().getAllNotifications();

                // Log kiểm tra
                Log.d("Admin_Notification", "Số lượng thông báo lấy được: " + dbNotifications.size());

                // Chuyển đổi danh sách Notification (Entity) sang item_noti (UI Model)
                List<item_noti> tempUIList = new ArrayList<>();
                for (NotificationEntity noti : dbNotifications) {
                    Log.d("Admin_Notification", "Noti: " + noti + " - Content: " + noti.getContent());

                    String time = noti.getCreatedAt() != null ? noti.getCreatedAt() : "Vừa xong";

                    item_noti item = new item_noti(
                            R.drawable.ava_1,
                            noti.getTitle(),
                            noti.getContent(),
                            time
                    );
                    tempUIList.add(item);
                }

                // Cập nhật RecyclerView trên UI thread
                runOnUiThread(() -> {
                    itemList.clear();
                    itemList.addAll(tempUIList);
                    myAdapter.notifyDataSetChanged();

                    // Hiện Toast thông báo
                    Toast.makeText(Admin_Notification.this,
                            "Đã tải " + tempUIList.size() + " thông báo",
                            Toast.LENGTH_SHORT).show();

                    Log.d("Admin_Notification", "RecyclerView đã cập nhật với " + tempUIList.size() + " items");
                });
            } catch (Exception e) {
                Log.e("Admin_Notification", "Lỗi khi tải dữ liệu: " + e.getMessage(), e);
                runOnUiThread(() -> {
                    Toast.makeText(Admin_Notification.this,
                            "Lỗi: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                });
            }
        }).start();
    }
}