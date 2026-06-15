package com.example.nhom33.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom33.DataEntity.CartEntity;
import com.example.nhom33.DataEntity.FoodsEntity;
import com.example.nhom33.Database.FoodDB;
import com.example.nhom33.adapter.Cart_Adapter;
import com.example.nhom33.R;
import com.example.nhom33.db.item_cart;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Cart extends AppCompatActivity implements Cart_Adapter.OnCartActionListener {

    private RecyclerView recyclerView;
    private Cart_Adapter adapter;
    private List<item_cart> itemList = new ArrayList<>();
    private FoodDB db;
    private TextView txtTotalPrice;
    private int userId;
    private double currentTotal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);

        db = FoodDB.getInstance(this);

        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);

        txtTotalPrice = findViewById(R.id.txtTotalPrice);

        View btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        Button btnPlaceOrder = findViewById(R.id.btnPlaceOrder);
        btnPlaceOrder.setOnClickListener(v -> {
            if (itemList.isEmpty()) {
                Toast.makeText(this, "Giỏ hàng của bạn đang trống", Toast.LENGTH_SHORT).show();
                return;
            }
            int totalQty = 0;
            for (item_cart item : itemList) {
                totalQty += Integer.parseInt(item.getQuantity());
            }

            Intent intent = new Intent(Cart.this, Payment0.class);
            intent.putExtra("TOTAL_PRICE", currentTotal);
            intent.putExtra("TOTAL_ITEMS", totalQty);
            startActivity(intent);
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new Cart_Adapter(itemList, this);
        recyclerView.setAdapter(adapter);

        if (userId != -1) {
            loadCartData();
        } else {
            Toast.makeText(this, "Vui lòng đăng nhập để xem giỏ hàng", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadCartData() {
        new Thread(() -> {
            List<CartEntity> cartEntities = db.cartDAO().getCartByUser(userId);
            itemList.clear();
            double total = 0;

            for (CartEntity cart : cartEntities) {
                FoodsEntity food = db.foodsDAO().getFoodById(cart.getFoodId());
                if (food != null) {
                    double effectivePrice = (food.getPriceSale() != null && food.getPriceSale() > 0)
                            ? food.getPriceSale()
                            : food.getPrice();

                    int resId = R.drawable.pizza_img;
                    String imgName = food.getImageUrl();
                    if (imgName != null && !imgName.isEmpty()) {
                        if (imgName.contains(".")) {
                            imgName = imgName.substring(0, imgName.lastIndexOf("."));
                        }
                        int checkResId = getResources().getIdentifier(imgName, "drawable", getPackageName());
                        if (checkResId != 0) resId = checkResId;
                    }

                    itemList.add(new item_cart(
                            food.getFoodId(),
                            food.getFoodName(),
                            String.valueOf(cart.getQuantity()),
                            (int) effectivePrice,
                            (int) food.getPrice(),
                            resId
                    ));
                    total += effectivePrice * cart.getQuantity();
                }
            }

            double finalTotal = total;
            runOnUiThread(() -> {
                currentTotal = finalTotal;
                adapter.notifyDataSetChanged();
                updateTotalUI(finalTotal);
            });
        }).start();
    }

    private void updateTotalUI(double total) {
        if (txtTotalPrice != null) {
            txtTotalPrice.setText(String.format(Locale.getDefault(), "%,.0f VNĐ", total));
        }
    }

    private void updateTotalPrice() {
        double total = 0;
        for (item_cart item : itemList) {
            total += (double) item.getPrice() * Integer.parseInt(item.getQuantity());
        }
        currentTotal = total;
        updateTotalUI(total);
    }

    @Override
    public void onPlus(item_cart item, int position) {
        new Thread(() -> {
            CartEntity cartItem = db.cartDAO().getCartItem(userId, item.getFoodId());
            if (cartItem != null) {
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                db.cartDAO().update(cartItem);

                runOnUiThread(() -> {
                    item.setQuantity(String.valueOf(cartItem.getQuantity()));
                    adapter.notifyItemChanged(position);
                    updateTotalPrice();
                });
            }
        }).start();
    }

    @Override
    public void onMinus(item_cart item, int position) {
        int currentQty = Integer.parseInt(item.getQuantity());
        if (currentQty <= 1) return;

        new Thread(() -> {
            CartEntity cartItem = db.cartDAO().getCartItem(userId, item.getFoodId());
            if (cartItem != null) {
                cartItem.setQuantity(currentQty - 1);
                db.cartDAO().update(cartItem);

                runOnUiThread(() -> {
                    item.setQuantity(String.valueOf(cartItem.getQuantity()));
                    adapter.notifyItemChanged(position);
                    updateTotalPrice();
                });
            }
        }).start();
    }

    @Override
    public void onRemove(item_cart item, int position) {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc chắn muốn xóa sản phẩm này khỏi giỏ hàng?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    new Thread(() -> {
                        CartEntity cartItem = db.cartDAO().getCartItem(userId, item.getFoodId());
                        if (cartItem != null) {
                            db.cartDAO().delete(cartItem);

                            runOnUiThread(() -> {
                                itemList.remove(position);
                                adapter.notifyItemRemoved(position);
                                adapter.notifyItemRangeChanged(position, itemList.size());
                                updateTotalPrice();
                                Toast.makeText(this, "Đã xóa sản phẩm", Toast.LENGTH_SHORT).show();
                            });
                        }
                    }).start();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}
