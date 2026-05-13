package com.example.nhom33.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom33.R;
import com.example.nhom33.database.item_running_order;

import java.util.List;

public class Admin_RunOrd_Adapter extends RecyclerView.Adapter<Admin_RunOrd_Adapter.MyViewHolder> {
    private List<item_running_order> itemList;

    public Admin_RunOrd_Adapter(List<item_running_order> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_item_running_order, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        item_running_order item = itemList.get(position);
        holder.img_food.setImageResource(item.getImg_food());
        holder.txt_food.setText(item.getTxt_food());
        holder.txt_id.setText(item.getTxt_id());
        holder.txt_price.setText(item.getTxt_price());
        holder.txt_tag.setText(item.getTxt_tag());
        // Lưu ý: txt_tag có trong layout nhưng chưa có trong model item_running_order.
        // Nếu cần hiển thị tag động, bạn hãy thêm thuộc tính vào class item_running_order.
    }

    @Override
    public int getItemCount() {
        return itemList != null ? itemList.size() : 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final ImageView img_food;
        private final TextView txt_food, txt_id, txt_price, txt_tag;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img_food = itemView.findViewById(R.id.img_food);
            txt_food = itemView.findViewById(R.id.txt_food);
            txt_id = itemView.findViewById(R.id.txt_id);
            txt_price = itemView.findViewById(R.id.txt_price);
            txt_tag = itemView.findViewById(R.id.txt_tag);
        }
    }
}
