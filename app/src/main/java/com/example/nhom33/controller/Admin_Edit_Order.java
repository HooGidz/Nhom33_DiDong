package com.example.nhom33.controller;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom33.DAO.OrderDetailsDAO;
import com.example.nhom33.DataEntity.OrdersEntity;
import com.example.nhom33.DataEntity.UsersEntity;
import com.example.nhom33.Database.FoodDB;
import com.example.nhom33.R;
import com.example.nhom33.adapter.Admin_OrderDetail_Adapter;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Admin_Edit_Order extends AppCompatActivity {

    private TextView txtOrderId, txtOrderDate, txtOrderTotal, txtCustomerName, txtDeliveryAddress;
    private Spinner spinnerStatus;
    private RecyclerView rvOrderDetails;
    private MaterialButton btnUpdate;
    private ImageButton btnBack;

    private FoodDB db;
    private int orderId;
    private OrdersEntity currentOrder;
    private List<String> statusList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_edit_order);

        db = FoodDB.getInstance(this);
        // Nhận ID đơn hàng được truyền sang từ Intent
        orderId = getIntent().getIntExtra("order_id", -1);

        if (orderId == -1) {
            Toast.makeText(this, "Lỗi: Không tìm thấy mã đơn hàng!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        initViews();
        setupSpinner();
        loadOrderData();

        btnBack.setOnClickListener(v -> finish());
        btnUpdate.setOnClickListener(v -> updateOrderStatus());
    }

    private void initViews() {
        txtOrderId = findViewById(R.id.txt_edit_order_id);
        txtOrderDate = findViewById(R.id.txt_edit_order_date);
        txtOrderTotal = findViewById(R.id.txt_edit_order_total);
        txtCustomerName = findViewById(R.id.txt_edit_customer_name);
        txtDeliveryAddress = findViewById(R.id.txt_edit_delivery_address);
        spinnerStatus = findViewById(R.id.spinner_order_status);
        rvOrderDetails = findViewById(R.id.rv_order_details);
        btnUpdate = findViewById(R.id.btn_update_status);
        btnBack = findViewById(R.id.btn_back);

        rvOrderDetails.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupSpinner() {
        // Danh sách trạng thái khớp với dữ liệu trong database của bạn
        statusList = new ArrayList<>();
        statusList.add("Chờ xác nhận");
        statusList.add("Đang giao hàng");
        statusList.add("Hoàn thành");
        statusList.add("Đã huỷ");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapter);
    }

    private void loadOrderData() {
        // 1. Lấy thông tin đơn hàng
        currentOrder = db.ordersDAO().getOrderById(orderId);
        if (currentOrder != null) {
            txtOrderId.setText(String.format(Locale.getDefault(), "Mã đơn hàng: #ORD-%d", currentOrder.getOrderId()));
            txtOrderDate.setText(String.format("Ngày đặt: %s", currentOrder.getOrderDate()));
            txtOrderTotal.setText(String.format(Locale.getDefault(), "Tổng cộng: %,.0f VNĐ", currentOrder.getTotalAmount()));
            txtDeliveryAddress.setText(String.format("Địa chỉ: %s", currentOrder.getDeliveryAddress()));

            // 2. Lấy thông tin khách hàng từ customerId
            UsersEntity user = db.usersDAO().getUserById(currentOrder.getCustomerId());
            if (user != null) {
                txtCustomerName.setText(user.getFullName());
            }

            // 3. Thiết lập vị trí hiện tại cho Spinner
            int position = statusList.indexOf(currentOrder.getStatus());
            if (position >= 0) {
                spinnerStatus.setSelection(position);
            }

            // 4. Tải danh sách món ăn chi tiết (OrderDetails)
            List<OrderDetailsDAO.OrderDetailWithFood> details = db.orderDetailsDAO().getDetailsWithFoodByOrderId(orderId);
            Admin_OrderDetail_Adapter detailsAdapter = new Admin_OrderDetail_Adapter(details);
            rvOrderDetails.setAdapter(detailsAdapter);
        }
    }

    private void updateOrderStatus() {
        if (currentOrder == null) return;

        String newStatus = spinnerStatus.getSelectedItem().toString();
        currentOrder.setStatus(newStatus);

        // Cập nhật trạng thái mới vào cơ sở dữ liệu
        db.ordersDAO().updateOrder(currentOrder);
        Toast.makeText(this, "Cập nhật trạng thái thành công!", Toast.LENGTH_SHORT).show();

        // Trả kết quả về cho màn hình trước đó để cập nhật lại danh sách
        setResult(RESULT_OK);
        finish();
    }
}
