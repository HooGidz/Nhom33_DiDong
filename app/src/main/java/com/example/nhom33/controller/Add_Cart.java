package com.example.nhom33.controller;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom33.adapter.Cart_Adapter;
import com.example.nhom33.R;
import com.example.nhom33.database.item_cart;

import java.util.ArrayList;
import java.util.List;

public class Add_Cart extends AppCompatActivity {

    // add card
//   @Override
//    protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            EdgeToEdge.enable(this);
//            setContentView(R.layout.activity_add_card);
//        // 1. Ánh xạ nút bấm từ XML vào Java
//        Button btnSubmit = findViewById(R.id.btnSubmit);
//
//        // 2. Thiết lập sự kiện click
//        btnSubmit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // 3. Lệnh chuyển từ Activity hiện tại sang Activity Success
//                // Lưu ý: Bạn cần tạo file PaymentSuccessActivity.java tương ứng với layout success
//                Intent intent = new Intent(Add_Cart.this, PaymentSuccessActivity.class);
//                startActivity(intent);
//            }
//        });
//        }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<item_cart> itemList = new ArrayList<>();
    // Thêm dữ liệu mẫu (Sử dụng icon pizza bạn có)
        itemList.add(new item_cart("Pizza Calzone European", "14\"", 64, R.drawable.fba));
        itemList.add(new item_cart("Pizza Calzone European", "14\"", 32, R.drawable.fba));

        Cart_Adapter adapter = new Cart_Adapter(itemList);
        recyclerView.setAdapter(adapter);
    }
}
    // Cart
/*@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_cart);
    RecyclerView recyclerView = findViewById(R.id.recyclerView);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));

    List<Item> itemList = new ArrayList<>();
// Thêm dữ liệu mẫu (Sử dụng icon pizza bạn có)
    itemList.add(new Item("Pizza Calzone European", "14\"", 64, R.drawable.fba));
    itemList.add(new Item("Pizza Calzone European", "14\"", 32, R.drawable.fba));

    MyAdapter adapter = new MyAdapter(itemList);
    recyclerView.setAdapter(adapter);
    }
}*/


/*@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_cart);
    }
}*/
