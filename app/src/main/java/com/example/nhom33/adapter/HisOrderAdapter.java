package com.example.nhom33.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom33.R;

import java.util.List;

import com.example.nhom33.database.hisOrder;

public class HisOrderAdapter extends RecyclerView.Adapter<HisOrderAdapter.HisOrderViewHolder> {
    private Context context;
    private List<hisOrder> hisOrderList;
    public HisOrderAdapter (Context context, List<hisOrder> hisOrderList)
    {
        this.context = context;
        this.hisOrderList = hisOrderList;
    }
    @Override
    public HisOrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hisorder, parent, false);
        return new HisOrderViewHolder(view);
    }
    @Override
    public void onBindViewHolder(HisOrderViewHolder holder, int position) {
        hisOrder hisOrder = hisOrderList.get(position);
        holder.tvCategory.setText(hisOrder.getCategory());
        holder.tvStatus.setText(hisOrder.getStatus());
        holder.tvStoreName.setText(hisOrder.getStoreName());
        holder.tvOrderID.setText(hisOrder.getOrderId());
        holder.tvPrice.setText(hisOrder.getPrice());
        holder.tvDateTime.setText(hisOrder.getDateTime());
        holder.tvDateTime.setText(hisOrder.getDateTime());
        holder.imgStore.setImageResource(hisOrder.getImageResId());
        if (hisOrder.getStatus().equalsIgnoreCase("Completed")) {
            holder.tvStatus.setTextColor(Color.parseColor("#2ECC71")); // Màu xanh lá
        } else if (hisOrder.getStatus().equalsIgnoreCase("Canceled")) {
            holder.tvStatus.setTextColor(Color.parseColor("#E74C3C")); // Màu đỏ
        } else {
            holder.tvStatus.setTextColor(Color.parseColor("#757575")); // Màu xám mặc định
        }

    }
    @Override
    public int getItemCount() {
        return hisOrderList.size();
    }
    public static class HisOrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategory, tvStatus, tvStoreName, tvOrderID, tvPrice, tvDateTime;
        ImageView imgStore;

        public HisOrderViewHolder(View itemView) {
            super(itemView);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvStoreName = itemView.findViewById(R.id.tvStoreName);
            tvOrderID = itemView.findViewById(R.id.tvOrderID);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            imgStore = itemView.findViewById(R.id.imgStore);
        }
    }
}
