package com.example.nhom33.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom33.R;
import com.example.nhom33.controller.Main_track_order;
import com.example.nhom33.db.OnOrder;

import java.util.List;

public class OnOrderAdapter extends RecyclerView.Adapter<OnOrderAdapter.OnOrderViewHolder> {
    private Context context;
    private List<OnOrder> OnOrderList;

    public OnOrderAdapter(Context context, List<OnOrder> OnOrderList) {
        this.context = context;
        this.OnOrderList = OnOrderList;
    }

    @Override
    public OnOrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_onorder, parent, false);
        return new OnOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OnOrderViewHolder holder, int position) {
        OnOrder order = OnOrderList.get(position);
        holder.tvCategory.setText(order.getCategory());
        holder.tvStoreName.setText(order.getStoreName());
        holder.tvOrderID.setText(order.getOrderId());
        holder.tvPrice.setText(order.getPrice());
        holder.tvStatus.setText(order.getStatus());
        holder.tvDateTime.setText(order.getDateTime());
        holder.tvItemCount.setText(order.getItemCount());
        holder.imgStore.setImageResource(order.getImageResId());

        holder.btnTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Main_track_order.class);
                // Truyền dữ liệu đơn hàng nếu cần (ví dụ: orderId)
                intent.putExtra("orderId", order.getOrderId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return OnOrderList.size();
    }

    public static class OnOrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategory, tvStatus, tvStoreName, tvOrderID, tvPrice, tvDateTime, tvItemCount;
        ImageView imgStore;
        Button btnTrack;

        public OnOrderViewHolder(View itemView) {
            super(itemView);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvStoreName = itemView.findViewById(R.id.tvStoreName);
            tvOrderID = itemView.findViewById(R.id.tvOrderID);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
            tvItemCount = itemView.findViewById(R.id.tvItemCount);
            imgStore = itemView.findViewById(R.id.imgStore);
            btnTrack = itemView.findViewById(R.id.btnTrack);
        }
    }
}
