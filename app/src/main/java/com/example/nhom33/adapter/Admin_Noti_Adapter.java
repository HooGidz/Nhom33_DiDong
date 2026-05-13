package com.example.nhom33.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom33.R;
import com.example.nhom33.database.item_noti;

import java.util.List;

public class Admin_Noti_Adapter extends RecyclerView.Adapter<Admin_Noti_Adapter.MyViewHolder> {
    private List<item_noti> itemList;

    public Admin_Noti_Adapter(List<item_noti> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_item_notification, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        item_noti item = itemList.get(position);
        holder.img_user.setImageResource(item.getImg_user());
        holder.img_food.setImageResource(item.getImg_food());
        holder.txt_notification.setText(item.getTxt_notification());
        holder.txt_time.setText(item.getTxt_time());
    }

    @Override
    public int getItemCount() {
        return itemList != null ? itemList.size() : 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final ImageView img_user, img_food;
        private final TextView txt_notification, txt_time;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img_user = itemView.findViewById(R.id.img_user);
            img_food = itemView.findViewById(R.id.img_food);
            txt_notification = itemView.findViewById(R.id.txt_notification);
            txt_time = itemView.findViewById(R.id.txt_time);
        }
    }
}
