package com.example.nhom33.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom33.R;
import com.example.nhom33.db.hisOrder;

import java.util.List;

public class HisOrderAdapter extends RecyclerView.Adapter<HisOrderAdapter.HisOrderViewHolder> {
    private final Context context;
    private final List<hisOrder> hisOrderList;

    public HisOrderAdapter(Context context, List<hisOrder> hisOrderList) {
        this.context = context;
        this.hisOrderList = hisOrderList;
    }

    @NonNull
    @Override
    public HisOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hisorder, parent, false);
        return new HisOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HisOrderViewHolder holder, int position) {
        hisOrder order = hisOrderList.get(position);

        holder.tvCategory.setText(order.getCategory());
        holder.tvStatus.setText(order.getStatus());
        holder.tvStoreName.setText(order.getStoreName());
        holder.tvOrderID.setText(order.getOrderId());
        holder.tvPrice.setText(order.getPrice());
        holder.tvDateTime.setText(order.getDateTime());
        holder.imgStore.setImageResource(order.getImageResId());

        // Cập nhật màu sắc trạng thái theo tiếng Việt (khớp với HisOrderMain)
        if (order.getStatus().equalsIgnoreCase("Đã giao") || order.getStatus().equalsIgnoreCase("Completed")) {
            holder.tvStatus.setTextColor(Color.parseColor("#2ECC71")); // Xanh lá
        } else if (order.getStatus().equalsIgnoreCase("Đã hủy") || order.getStatus().equalsIgnoreCase("Canceled")) {
            holder.tvStatus.setTextColor(Color.parseColor("#E74C3C")); // Đỏ
        } else if (order.getStatus().equalsIgnoreCase("Đang giao hàng")) {
            holder.tvStatus.setTextColor(Color.parseColor("#3498DB")); // Xanh dương
        } else {
            holder.tvStatus.setTextColor(Color.parseColor("#FF7622")); // Cam (Đang chuẩn bị)
        }

        // Xử lý sự kiện Đặt lại món
        holder.btnReorder.setOnClickListener(v -> Toast.makeText(context, "Tính năng đặt lại cho đơn " + order.getOrderId() + " đang được phát triển", Toast.LENGTH_SHORT).show());
    }

    @Override
    public int getItemCount() {
        return hisOrderList != null ? hisOrderList.size() : 0;
    }

    public static class HisOrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategory, tvStatus, tvStoreName, tvOrderID, tvPrice, tvDateTime;
        ImageView imgStore;
        Button btnReorder;

        public HisOrderViewHolder(View itemView) {
            super(itemView);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvStoreName = itemView.findViewById(R.id.tvStoreName);
            tvOrderID = itemView.findViewById(R.id.tvOrderID);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
            imgStore = itemView.findViewById(R.id.imgStore);
            btnReorder = itemView.findViewById(R.id.btnReorder);
        }
    }
}