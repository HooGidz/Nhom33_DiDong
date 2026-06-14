package com.example.nhom33.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.nhom33.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.navigation.NavigationView;

public class Admin_Dashboard extends AppCompatActivity {

    ImageButton btn_menu, btn_add, btn_notification, btn_personal, btn_dashboard;
    MaterialButton btn_MenuHome;
    MaterialCardView card_running_orders, card_delivery_orders, card_review;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.admin_dashboard);
        
        View mainView = findViewById(R.id.drawer_layout);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        btn_MenuHome = findViewById(R.id.btn_MenuHome);

        btn_menu = findViewById(R.id.btn_menu);
        btn_add = findViewById(R.id.btn_add);
        btn_notification = findViewById(R.id.btn_notification);
        btn_personal = findViewById(R.id.btn_personal);
        btn_dashboard = findViewById(R.id.btn_dashboard);
        card_running_orders = findViewById(R.id.card_running_orders);
        card_delivery_orders = findViewById(R.id.card_delivery_orders);

        // Đăng ký OnBackPressedCallback để xử lý nút Back thay cho onBackPressed()
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    // Tạm thời vô hiệu hóa callback để thực hiện hành động Back mặc định
                    setEnabled(false);
                    getOnBackPressedDispatcher().onBackPressed();
                    setEnabled(true);
                }
            }
        });

        // Mở drawer khi nhấn btn_MenuHome
        if (btn_MenuHome != null) {
            btn_MenuHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (drawerLayout != null) {
                        drawerLayout.openDrawer(GravityCompat.START);
                    }
                }
            });
        }

        // Xử lý click item trong NavigationView
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int id = item.getItemId();
                    if (id == R.id.nav_home) {
                        drawerLayout.closeDrawer(GravityCompat.START);
                    } else if (id == R.id.nav_order) {
                        startActivity(new Intent(Admin_Dashboard.this, Admin_All_Order.class));
                    } else if (id == R.id.nav_food_list) {
                        startActivity(new Intent(Admin_Dashboard.this, Admin_MyFoodList.class));
                    } else if (id == R.id.nav_all_category) {
                        startActivity(new Intent(Admin_Dashboard.this, Admin_All_Category.class));
                    } else if (id == R.id.nav_notifications) {
                        startActivity(new Intent(Admin_Dashboard.this, Admin_Notification.class));
                    } else if (id == R.id.nav_profile) {
                        startActivity(new Intent(Admin_Dashboard.this, MainAdProfile.class));
                    } else if (id == R.id.nav_logout) {
                        Toast.makeText(Admin_Dashboard.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    if (drawerLayout != null) {
                        drawerLayout.closeDrawer(GravityCompat.START);
                    }
                    return true;
                }
            });
        }

        if (card_running_orders != null) {
            card_running_orders.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Admin_Dashboard.this, Admin_All_Order.class);
                    intent.putExtra("FILTER_STATUS", "Chờ xác nhận");
                    startActivity(intent);
                }
            });
        }
        if (card_delivery_orders != null) {
            card_delivery_orders.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Admin_Dashboard.this, Admin_All_Order.class);
                    intent.putExtra("FILTER_STATUS", "Đang giao hàng");
                    startActivity(intent);
                }
            });
        }

        if (btn_menu != null) {
            btn_menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Admin_Dashboard.this, Admin_MyFoodList.class);
                    startActivity(intent);
                }
            });
        }
        
        if (btn_add != null) {
            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Admin_Dashboard.this, Admin_MyFoodList.class);
                    startActivity(intent);
                }
            });
        }
        
        if (btn_notification != null) {
            btn_notification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Admin_Dashboard.this, Admin_Notification.class);
                    startActivity(intent);
                }
            });
        }
        
        if (btn_personal != null) {
            btn_personal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Admin_Dashboard.this, MainAdProfile.class);
                    startActivity(intent);
                }
            });
        }
        
        if (btn_dashboard != null) {
            btn_dashboard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Reload dashboard hoặc không làm gì
                }
            });
        }
    }
}
