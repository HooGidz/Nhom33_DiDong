package com.example.nhom33.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom33.DAO.OrderDetailsDAO;
import com.example.nhom33.R;

import java.util.List;
import java.util.Locale;

public class Admin_OrderDetail_Adapter extends RecyclerView.Adapter<Admin_OrderDetail_Adapter.ViewHolder> {

    private List<OrderDetailsDAO.OrderDetailWithFood> detailsList;

    public Admin_OrderDetail_Adapter(List<OrderDetailsDAO.OrderDetailWithFood> detailsList) {
        this.detailsList = detailsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_item_order_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderDetailsDAO.OrderDetailWithFood detail = detailsList.get(position);
        holder.txtFoodName.setText(detail.food_name);
        holder.txtQuantity.setText(String.format(Locale.getDefault(), "x%d", detail.quantity));
        holder.txtPrice.setText(String.format(Locale.getDefault(), "%,.0f VNĐ", detail.price_at_time));
    }

    @Override
    public int getItemCount() {
        return detailsList != null ? detailsList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtFoodName, txtQuantity, txtPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtFoodName = itemView.findViewById(R.id.txt_food_name);
            txtQuantity = itemView.findViewById(R.id.txt_quantity);
            txtPrice = itemView.findViewById(R.id.txt_price);
        }
    }
}
