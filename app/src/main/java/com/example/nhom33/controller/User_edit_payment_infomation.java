package com.example.nhom33.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom33.DataEntity.CartEntity;
import com.example.nhom33.DataEntity.OrderDetailsEntity;
import com.example.nhom33.DataEntity.OrdersEntity;
import com.example.nhom33.DataEntity.UsersEntity;
import com.example.nhom33.Database.FoodDB;
import com.example.nhom33.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class User_edit_payment_infomation extends AppCompatActivity {
    private ImageButton btnBack;
    private EditText edtFullName, edtAddress, edtPhone;
    private TextView tvTotalItems, tvSubTotal, tvFinalTotal;
    private Button btnConfirmOrder;
    private FoodDB db;
    private int userId;
    private double totalPrice;
    private int totalItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.user_edit_payment_information);

        db = FoodDB.getInstance(this);

        // Khởi tạo views
        btnBack = findViewById(R.id.btn_back);
        edtFullName = findViewById(R.id.edtFullName);
        edtAddress = findViewById(R.id.edtAddress);
        edtPhone = findViewById(R.id.edtPhone);
        tvTotalItems = findViewById(R.id.tvTotalItems);
        tvSubTotal = findViewById(R.id.tvSubTotal);
        tvFinalTotal = findViewById(R.id.tvFinalTotal);
        btnConfirmOrder = findViewById(R.id.btnConfirmOrder);

        // Lấy dữ liệu từ Intent
        Intent intent = getIntent();
        totalPrice = intent.getDoubleExtra("TOTAL_PRICE", 0);
        totalItems = intent.getIntExtra("TOTAL_ITEMS", 0);

        // Hiển thị tóm tắt đơn hàng
        tvTotalItems.setText(String.valueOf(totalItems));
        String formattedPrice = String.format(Locale.getDefault(), "%,.0f VNĐ", totalPrice);
        tvSubTotal.setText(formattedPrice);
        tvFinalTotal.setText(formattedPrice);

        // Lấy userId từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);

        if (userId != -1) {
            loadUserInfo();
        }

        btnBack.setOnClickListener(v -> finish());

        btnConfirmOrder.setOnClickListener(v -> processOrder());
    }

    private void loadUserInfo() {
        new Thread(() -> {
            UsersEntity user = db.usersDAO().getUserById(userId);
            if (user != null) {
                runOnUiThread(() -> {
                    edtFullName.setText(user.getFullName());
                    edtAddress.setText(user.getAddress());
                    edtPhone.setText(user.getPhone());
                });
            }
        }).start();
    }

    private void processOrder() {
        String fullName = edtFullName.getText().toString().trim();
        String address = edtAddress.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();

        if (fullName.isEmpty() || address.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin nhận hàng", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(() -> {
            // 1. Lấy danh sách sản phẩm trong giỏ hàng
            List<CartEntity> cartList = db.cartDAO().getCartByUser(userId);
            if (cartList == null || cartList.isEmpty()) {
                runOnUiThread(() -> Toast.makeText(this, "Giỏ hàng của bạn đang trống", Toast.LENGTH_SHORT).show());
                return;
            }

            // 2. Tạo đơn hàng mới (Orders)
            String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

            // Sửa lại constructor để khớp với OrdersEntity
            // Tham số: userId, couponId, customerName, customerPhone, customerAddress, orderDate, totalAmount, deliveryAddress, status, methodPayment, note
            OrdersEntity newOrder = new OrdersEntity(
                    userId,
                    0,           // couponId (mặc định 0 nếu không có)
                    fullName,
                    phone,
                    address,
                    currentDate,
                    totalPrice,
                    address,     // deliveryAddress
                    0,           // status: 0 (Chờ xác nhận)
                    0,           // methodPayment: 0 (Tiền mặt)
                    ""           // note
            );

            long orderId = db.ordersDAO().insertOrder(newOrder);

            // 3. Thêm chi tiết đơn hàng (OrderDetails)
            for (CartEntity cartItem : cartList) {
                OrderDetailsEntity detail = new OrderDetailsEntity(
                        (int) orderId,
                        cartItem.getFoodId(),
                        cartItem.getQuantity(),
                        cartItem.getPriceAtTime()
                );
                db.orderDetailsDAO().insertOrderDetail(detail);
            }

            // 4. Xóa toàn bộ sản phẩm trong giỏ hàng của user này
            db.cartDAO().clearCart(userId);

            runOnUiThread(() -> {
                Toast.makeText(this, "Đặt hàng thành công! Đơn hàng của bạn đang chờ xác nhận.", Toast.LENGTH_LONG).show();

                // Chuyển sang trang đặt hàng thành công và xóa stack các màn hình trước đó
                Intent intentSuccess = new Intent(User_edit_payment_infomation.this, User_Payment_Success.class);
                intentSuccess.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intentSuccess);
                finish();
            });
        }).start();
    }
}