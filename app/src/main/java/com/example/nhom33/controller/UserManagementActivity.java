package com.example.nhom33.controller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom33.Database.FoodDB;
import com.example.nhom33.DataEntity.UsersEntity;
import com.example.nhom33.R;
import com.example.nhom33.adapter.UserAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class UserManagementActivity extends AppCompatActivity {

    private RecyclerView rvUsers;
    private UserAdapter userAdapter;
    private List<UsersEntity> userList;
    private FoodDB db;
    private TabLayout tabLayout;
    private String currentRole = "customer"; // Mặc định hiển thị customer

    // Khai báo launcher để nhận kết quả từ màn hình chỉnh sửa
    private final ActivityResultLauncher<Intent> editUserLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    // Tải lại danh sách sau khi chỉnh sửa thành công
                    loadUsers();
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management);

        rvUsers = findViewById(R.id.rvUsers);
        tabLayout = findViewById(R.id.tabLayout);
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        db = FoodDB.getInstance(this);
        userList = new ArrayList<>();

        // Khởi tạo Adapter với Listener xử lý Xóa và Chuyển sang trang Sửa
        userAdapter = new UserAdapter(userList, new UserAdapter.OnUserActionListener() {
            @Override
            public void onDelete(UsersEntity user, int position) {
                showDeleteConfirmDialog(user, position);
            }

            @Override
            public void onEdit(UsersEntity user, int position) {
                // Mở Activity chỉnh sửa tài khoản thay vì Dialog
                Intent intent = new Intent(UserManagementActivity.this, AdminEditUserActivity.class);
                intent.putExtra("USER_ID", user.getUserId());
                editUserLauncher.launch(intent);
            }
        });

        rvUsers.setLayoutManager(new LinearLayoutManager(this));
        rvUsers.setAdapter(userAdapter);

        // Xử lý chuyển Tab
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    currentRole = "customer";
                } else {
                    currentRole = "merchant";
                }
                loadUsers();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        loadUsers();
    }

    private void loadUsers() {
        new Thread(() -> {
            List<UsersEntity> users = db.usersDAO().getUsersByRole(currentRole);
            runOnUiThread(() -> {
                userList.clear();
                userList.addAll(users);
                userAdapter.notifyDataSetChanged();
            });
        }).start();
    }

    private void showDeleteConfirmDialog(UsersEntity user, int position) {
        new AlertDialog.Builder(this)
                .setTitle("Xóa tài khoản")
                .setMessage("Bạn có chắc chắn muốn xóa " + (user.getRole().equals("merchant") ? "Admin " : "người dùng ") + user.getFullName() + "?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    deleteUser(user, position);
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void deleteUser(UsersEntity user, int position) {
        new Thread(() -> {
            db.usersDAO().deleteUser(user);
            runOnUiThread(() -> {
                userList.remove(position);
                userAdapter.notifyItemRemoved(position);
                userAdapter.notifyItemRangeChanged(position, userList.size());
                Toast.makeText(this, "Đã xóa tài khoản", Toast.LENGTH_SHORT).show();
            });
        }).start();
    }
}
