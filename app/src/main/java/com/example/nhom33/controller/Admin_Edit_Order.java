package com.example.nhom33.controller;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import com.example.nhom33.Database.FoodDB;
import com.example.nhom33.R;
import com.example.nhom33.adapter.Admin_OrderDetail_Adapter;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Admin_Edit_Order extends AppCompatActivity {

    private TextView txtOrderId, txtOrderDate, txtOrderTotal, txtCustomerName, 
            txtCustomerPhone, txtDeliveryAddress, txtPaymentMethod, txtOrderNote;
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
        txtCustomerPhone = findViewById(R.id.txt_edit_customer_phone);
        txtDeliveryAddress = findViewById(R.id.txt_edit_delivery_address);
        txtPaymentMethod = findViewById(R.id.txt_edit_payment_method);
        txtOrderNote = findViewById(R.id.txt_edit_order_note);
        
        spinnerStatus = findViewById(R.id.spinner_order_status);
        rvOrderDetails = findViewById(R.id.rv_order_details);
        btnUpdate = findViewById(R.id.btn_update_status);
        btnBack = findViewById(R.id.btn_back);

        rvOrderDetails.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupSpinner() {
        // Danh sách trạng thái (Index tương ứng với giá trị int status trong DB)
        statusList = new ArrayList<>();
        statusList.add("Chờ xác nhận"); // 0
        statusList.add("Đang giao hàng"); // 1
        statusList.add("Hoàn thành");    // 2
        statusList.add("Đã huỷ");         // 3

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapter);
    }

    private void loadOrderData() {
        new Thread(() -> {
            currentOrder = db.ordersDAO().getOrderById(orderId);
            if (currentOrder != null) {
                // Tải danh sách món ăn chi tiết (OrderDetails)
                List<OrderDetailsDAO.OrderDetailWithFood> details = db.orderDetailsDAO().getDetailsWithFoodByOrderId(orderId);
                
                runOnUiThread(() -> {
                    txtOrderId.setText(String.format(Locale.getDefault(), "Mã đơn hàng: #ORD-%d", currentOrder.getOrderId()));
                    txtOrderDate.setText(String.format("Ngày đặt: %s", currentOrder.getOrderDate()));
                    txtOrderTotal.setText(String.format(Locale.getDefault(), "Tổng cộng: %,.0f VNĐ", currentOrder.getTotalAmount()));
                    
                    txtCustomerName.setText(String.format("Họ tên: %s", currentOrder.getCustomerName()));
                    txtCustomerPhone.setText(String.format("SĐT: %s", currentOrder.getCustomerPhone()));
                    txtDeliveryAddress.setText(String.format("Địa chỉ: %s", currentOrder.getDeliveryAddress()));
                    txtOrderNote.setText(currentOrder.getNote() != null && !currentOrder.getNote().isEmpty() 
                            ? currentOrder.getNote() : "Không có ghi chú");

                    // Hiển thị phương thức thanh toán (0: Tiền mặt, 1: Chuyển khoản)
                    String payment = (currentOrder.getMethodPayment() == 1) ? "Chuyển khoản" : "Tiền mặt";
                    txtPaymentMethod.setText(String.format("Thanh toán: %s", payment));

                    // Thiết lập vị trí hiện tại cho Spinner dựa trên int status
                    if (currentOrder.getStatus() >= 0 && currentOrder.getStatus() < statusList.size()) {
                        spinnerStatus.setSelection(currentOrder.getStatus());
                    }

                    // Cập nhật adapter cho RecyclerView
                    Admin_OrderDetail_Adapter detailsAdapter = new Admin_OrderDetail_Adapter(details);
                    rvOrderDetails.setAdapter(detailsAdapter);
                });
            }
        }).start();
    }

    private void updateOrderStatus() {
        if (currentOrder == null) return;

        int newStatus = spinnerStatus.getSelectedItemPosition();
        currentOrder.setStatus(newStatus);

        new Thread(() -> {
            try {
                // Cập nhật trạng thái mới vào cơ sở dữ liệu
                db.ordersDAO().updateOrder(currentOrder);
                
                new Handler(Looper.getMainLooper()).post(() -> {
                    Toast.makeText(Admin_Edit_Order.this, "Cập nhật trạng thái thành công!", Toast.LENGTH_SHORT).show();
                    // Trả kết quả về cho màn hình trước đó để cập nhật lại danh sách
                    setResult(RESULT_OK);
                    finish();
                });
            } catch (Exception e) {
                new Handler(Looper.getMainLooper()).post(() -> 
                    Toast.makeText(Admin_Edit_Order.this, "Lỗi khi cập nhật: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
}
