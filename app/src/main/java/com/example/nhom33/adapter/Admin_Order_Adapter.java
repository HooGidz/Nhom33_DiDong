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
import java.util.Locale;

public class Admin_Order_Adapter extends RecyclerView.Adapter<Admin_Order_Adapter.OrderViewHolder> {

    private final List<OrdersEntity> orderList;
    private final OnOrderClickListener listener;

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
          holder.txtOrderId.setText(String.format(Locale.getDefault(), "#ORD-%d", order.getOrderId()));
          holder.txtOrderDate.setText(order.getOrderDate());

          // Vì OrdersEntity chỉ có customerId, tạm thời hiển thị ID.
          // Sau này bạn có thể join bảng Users để lấy fullName.
          holder.txtCustomerName.setText(String.format(Locale.getDefault(), "Khách hàng ID: %d", order.getUserId()));

          // Định dạng hiển thị tiền tệ (Ví dụ: 150.000 VNĐ)
          holder.txtOrderTotal.setText(String.format(Locale.getDefault(), "%,.0f VNĐ", order.getTotalAmount()));

         // Convert int status thành String
         String statusText = mapStatusToString(order.getStatus());
         holder.txtOrderStatus.setText(statusText);
         
         // Bạn có thể thêm logic đổi màu trạng thái tại đây nếu muốn
         // Ví dụ: if(order.getStatus() == 2) holder.txtOrderStatus.setTextColor(...)

         // Xử lý sự kiện click nút Chi tiết
         holder.btnDetail.setOnClickListener(v -> {
             if (listener != null) {
                 listener.onDetailClick(order);
             }
         });
     }

     /**
      * Chuyển đổi status int thành String
      * 0 - Chờ xác nhận
      * 1 - Đang giao hàng
      * 2 - Hoàn thành
      * 3 - Đã hủy
      */
     private String mapStatusToString(int status) {
         switch (status) {
             case 1:
                 return "Đang giao hàng";
             case 2:
                 return "Hoàn thành";
             case 3:
                 return "Đã hủy";
             default:
                 return "Chờ xác nhận";
         }
     }

    @Override
    public int getItemCount() {
        return orderList != null ? orderList.size() : 0;
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView txtOrderId, txtOrderDate, txtCustomerName, txtOrderTotal, txtOrderStatus;
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
