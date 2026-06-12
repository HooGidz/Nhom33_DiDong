package com.example.nhom33.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom33.R;

import java.util.List;

import com.example.nhom33.db.OnOrder;

public class OnOrderAdapter extends RecyclerView.Adapter<OnOrderAdapter.OnOrderViewHolder> {
    private Context context;
    private List<OnOrder> OnOrderList;
    public OnOrderAdapter (Context context, List<OnOrder> OnOrderList)
    {
        this.context = context;
        this.OnOrderList = OnOrderList;
    }
    @Override
    public OnOrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.item_onorder, parent, false);
        return new OnOrderViewHolder(view);
    }
    @Override
    public void onBindViewHolder(OnOrderViewHolder holder, int position) {
        OnOrder OnOrder = OnOrderList.get(position);
        holder.tvCategory.setText(OnOrder.getCategory());
        holder.tvStoreName.setText(OnOrder.getStoreName());
        holder.tvOrderID.setText(OnOrder.getOrderId());
        holder.tvPrice.setText(OnOrder.getPrice());
        holder.imgStore.setImageResource(OnOrder.getImageResId());
    }
    @Override
    public int getItemCount() {
        return OnOrderList.size();
    }
    public static class OnOrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategory, tvStatus, tvStoreName, tvOrderID, tvPrice, tvDateTime;
        ImageView imgStore;

        public OnOrderViewHolder(View itemView) {
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
