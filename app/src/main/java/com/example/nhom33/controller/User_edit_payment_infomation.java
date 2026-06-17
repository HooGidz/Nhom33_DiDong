package com.example.nhom33.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom33.DataEntity.CartEntity;
import com.example.nhom33.DataEntity.FoodsEntity;
import com.example.nhom33.DataEntity.OrderDetailsEntity;
import com.example.nhom33.DataEntity.OrdersEntity;
import com.example.nhom33.DataEntity.UsersEntity;
import com.example.nhom33.Database.FoodDB;
import com.example.nhom33.R;
import com.example.nhom33.adapter.OrderConfirmAdapter;
import com.example.nhom33.db.item_cart;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class User_edit_payment_infomation extends AppCompatActivity {
    private ImageButton btnBack;
    private EditText edtFullName, edtAddress, edtPhone;
    private TextView tvSubTotal, tvFinalTotal, tvDiscountAmount, tvDiscountDesc;
    private Button btnConfirmOrder;
    private RecyclerView rvOrderItems;
    private OrderConfirmAdapter adapter;
    private List<item_cart> itemList = new ArrayList<>();
    
    private FoodDB db;
    private int userId;
    private double finalTotalPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.user_edit_payment_information);

        db = FoodDB.getInstance(this);

        initViews();
        setupRecyclerView();
        
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);

        if (userId != -1) {
            loadUserInfo();
            loadCartData();
        } else {
            Toast.makeText(this, "Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
            finish();
        }

        setupListeners();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btn_back);
        edtFullName = findViewById(R.id.edtFullName);
        edtAddress = findViewById(R.id.edtAddress);
        edtPhone = findViewById(R.id.edtPhone);
        tvSubTotal = findViewById(R.id.tvSubTotal);
        tvFinalTotal = findViewById(R.id.tvFinalTotal);
        tvDiscountAmount = findViewById(R.id.tvDiscountAmount);
        tvDiscountDesc = findViewById(R.id.tvDiscountDesc);
        btnConfirmOrder = findViewById(R.id.btnConfirmOrder);
        rvOrderItems = findViewById(R.id.rvOrderItems);
    }

    private void setupRecyclerView() {
        rvOrderItems.setLayoutManager(new LinearLayoutManager(this));
        adapter = new OrderConfirmAdapter(this, itemList, position -> {
            showRemoveDialog(position);
        });
        rvOrderItems.setAdapter(adapter);
    }

    private void setupListeners() {
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }
        
        View btnAddMore = findViewById(R.id.btnAddMore);
        if (btnAddMore != null) {
            btnAddMore.setOnClickListener(v -> {
                Intent intent = new Intent(this, User_TrangChu.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            });
        }

        if (btnConfirmOrder != null) {
            btnConfirmOrder.setOnClickListener(v -> processOrder());
        }
    }

    private void loadUserInfo() {
        new Thread(() -> {
            UsersEntity user = db.usersDAO().getUserById(userId);
            if (user != null) {
                runOnUiThread(() -> {
                    if (edtFullName != null) edtFullName.setText(user.getFullName());
                    if (edtAddress != null) edtAddress.setText(user.getAddress());
                    if (edtPhone != null) edtPhone.setText(user.getPhone());
                });
            }
        }).start();
    }

    private void loadCartData() {
        new Thread(() -> {
            List<CartEntity> cartEntities = db.cartDAO().getCartByUser(userId);
            itemList.clear();
            for (CartEntity cart : cartEntities) {
                FoodsEntity food = db.foodsDAO().getFoodById(cart.getFoodId());
                if (food != null) {
                    double effectivePrice = (food.getPriceSale() != null && food.getPriceSale() > 0)
                            ? food.getPriceSale()
                            : food.getPrice();

                    itemList.add(new item_cart(
                            food.getFoodId(),
                            food.getFoodName(),
                            String.valueOf(cart.getQuantity()),
                            (int) effectivePrice,
                            (int) food.getPrice(),
                            food.getImageUrl()
                    ));
                }
            }
            runOnUiThread(() -> {
                adapter.notifyDataSetChanged();
                calculateTotals();
            });
        }).start();
    }

    private void calculateTotals() {
        double subTotal = 0;
        int totalQty = 0;
        for (item_cart item : itemList) {
            try {
                int qty = Integer.parseInt(item.getQuantity());
                subTotal += (double) item.getPrice() * qty;
                totalQty += qty;
            } catch (Exception ignored) {}
        }

        double discount = 0;
        if (totalQty >= 3) {
            discount = subTotal * 0.5;
            if (tvDiscountDesc != null) tvDiscountDesc.setText("Giảm 50% cho đơn từ 3 ly");
            if (tvDiscountAmount != null) tvDiscountAmount.setText(String.format(Locale.getDefault(), "-%,.0fđ", discount));
        } else {
            if (tvDiscountDesc != null) tvDiscountDesc.setText("Không có khuyến mãi");
            if (tvDiscountAmount != null) tvDiscountAmount.setText("0đ");
        }

        finalTotalPrice = subTotal - discount;
        
        if (tvSubTotal != null) tvSubTotal.setText(String.format(Locale.getDefault(), "%,.0fđ", subTotal));
        if (tvFinalTotal != null) tvFinalTotal.setText(String.format(Locale.getDefault(), "%,.0fđ", finalTotalPrice));
    }

    private void showRemoveDialog(int position) {
        if (position < 0 || position >= itemList.size()) return;
        item_cart itemToRemove = itemList.get(position);

        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Xóa sản phẩm này khỏi đơn hàng?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    new Thread(() -> {
                        db.cartDAO().deleteByFoodId(userId, itemToRemove.getFoodId());
                        runOnUiThread(() -> {
                            // Tìm lại vị trí hiện tại của item trong trường hợp danh sách đã đổi
                            int currentIdx = itemList.indexOf(itemToRemove);
                            if (currentIdx != -1) {
                                itemList.remove(currentIdx);
                                adapter.notifyItemRemoved(currentIdx);
                                calculateTotals();
                                if (itemList.isEmpty()) finish();
                            }
                        });
                    }).start();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void processOrder() {
        if (edtFullName == null || edtAddress == null || edtPhone == null) return;

        String fullName = edtFullName.getText().toString().trim();
        String address = edtAddress.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();

        if (fullName.isEmpty() || address.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin nhận hàng", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(() -> {
            if (itemList.isEmpty()) return;

            String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
            
            // FIX: Truyền null cho couponId thay vì 0 để tránh lỗi khóa ngoại (FOREIGN KEY constraint)
            OrdersEntity newOrder = new OrdersEntity(
                    userId, null, fullName, phone, address, currentDate, finalTotalPrice, address, 0, 0, ""
            );

            try {
                long orderId = db.ordersDAO().insertOrder(newOrder);

                for (item_cart item : itemList) {
                    OrderDetailsEntity detail = new OrderDetailsEntity(
                            (int) orderId, item.getFoodId(), Integer.parseInt(item.getQuantity()), (double) item.getPrice()
                    );
                    db.orderDetailsDAO().insertOrderDetail(detail);
                }

                db.cartDAO().clearCart(userId);

                runOnUiThread(() -> {
                    Toast.makeText(this, "Đặt hàng thành công!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(this, User_Payment_Success.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                });
            } catch (Exception e) {
                runOnUiThread(() -> {
                    Toast.makeText(this, "Lỗi khi lưu đơn hàng: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
                e.printStackTrace();
            }
        }).start();
    }
}
