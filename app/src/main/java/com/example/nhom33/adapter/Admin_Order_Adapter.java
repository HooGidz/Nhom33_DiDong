package com.example.nhom33.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom33.DataEntity.OrdersEntity;
import com.example.nhom33.R;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class Admin_Order_Adapter extends RecyclerView.Adapter<Admin_Order_Adapter.OrderViewHolder> {

    private List<OrdersEntity> orderList;
    private OnOrderClickListener listener;

    // Interface để xử lý sự kiện click
    public interface OnOrderClickListener {
        void onDetailClick(OrdersEntity order);
    }

    public Admin_Order_Adapter(List<OrdersEntity> orderList, OnOrderClickListener listener) {
        this.orderList = orderList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OrdersEntity order = orderList.get(position);

        // Đổ dữ liệu vào các View
        holder.txtOrderId.setText("#ORD-" + order.getOrderId());
        holder.txtOrderDate.setText(order.getOrderDate());
        
        // Vì OrdersEntity chỉ có customerId, tạm thời hiển thị ID. 
        // Sau này bạn có thể join bảng Users để lấy fullName.
        holder.txtCustomerName.setText("Khách hàng ID: " + order.getCustomerId());
        
        // Định dạng hiển thị tiền tệ (Ví dụ: 150.000 VNĐ)
        holder.txtOrderTotal.setText(String.format("%,.0f VNĐ", order.getTotalAmount()));
        
        holder.txtOrderStatus.setText(order.getStatus());
        
        // Bạn có thể thêm logic đổi màu trạng thái tại đây nếu muốn
        // Ví dụ: if(order.getStatus().equals("Completed")) holder.txtOrderStatus.setTextColor(...)

        // Xử lý sự kiện click nút Chi tiết
        holder.btnDetail.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDetailClick(order);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList != null ? orderList.size() : 0;
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView txtOrderId, txtOrderDate, txtCustomerName, txtOrderTotal, txtOrderStatus, txtPaymentMethod;
        MaterialButton btnDetail;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            txtOrderId = itemView.findViewById(R.id.txt_order_id);
            txtOrderDate = itemView.findViewById(R.id.txt_order_date);
            txtCustomerName = itemView.findViewById(R.id.txt_customer_name);
            txtOrderTotal = itemView.findViewById(R.id.txt_order_total);
            txtOrderStatus = itemView.findViewById(R.id.txt_order_status);
            btnDetail = itemView.findViewById(R.id.btn_detail);
        }
    }
}
