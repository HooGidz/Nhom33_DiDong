package com.example.nhom33.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom33.R;
import com.example.nhom33.db.item_noti;

import java.util.List;

public class Admin_Noti_Adapter extends RecyclerView.Adapter<Admin_Noti_Adapter.MyViewHolder> {
    private List<item_noti> itemList;

    public Admin_Noti_Adapter(List<item_noti> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Đảm bảo tên layout R.layout.admin_item_notification khớp với file XML của bạn
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_item_notification, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        item_noti item = itemList.get(position);

        holder.img_user.setImageResource(item.getImg_user());

        // Gán dữ liệu cho Title và Content
        holder.txt_title.setText(item.getTxt_title());
        holder.txt_content.setText(item.getTxt_content());

        holder.txt_time.setText(item.getTxt_time());
    }

    @Override
    public int getItemCount() {
        return itemList != null ? itemList.size() : 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final ImageView img_user;
        // Đã sửa đổi: Khai báo txt_title và txt_content thay vì txt_notification
        private final TextView txt_title, txt_content, txt_time;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img_user = itemView.findViewById(R.id.img_user);

            // Ánh xạ chính xác các ID từ file XML
            txt_title = itemView.findViewById(R.id.txt_title);
            txt_content = itemView.findViewById(R.id.txt_content);
            txt_time = itemView.findViewById(R.id.txt_time);
        }
    }
}