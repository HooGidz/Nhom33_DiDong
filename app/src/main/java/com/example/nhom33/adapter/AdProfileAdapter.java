package com.example.nhom33.adapter; // Thay đổi package phù hợp

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.nhom33.R;

import java.util.List;

import com.example.nhom33.database.AdProfile;

public class AdProfileAdapter extends RecyclerView.Adapter<AdProfileAdapter.ProfileViewHolder> {

    private List<AdProfile> profileList;

    // Constructor để nhận danh sách dữ liệu
    public AdProfileAdapter(List<AdProfile> profileList) {
        this.profileList = profileList;
    }

    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate file item_menu.xml đã tạo ở bước trước
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_adprofile, parent, false);
        return new ProfileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileViewHolder holder, int position) {
        AdProfile item = profileList.get(position);

        // Đổ dữ liệu từ model vào view
        holder.tvTitle.setText(item.getTitle());
        holder.imgIcon.setImageResource(item.getIcon());

    }

    @Override
    public int getItemCount() {
        return profileList != null ? profileList.size() : 0;
    }

    // Lớp giữ các view (ViewHolder)
    public static class ProfileViewHolder extends RecyclerView.ViewHolder {
        ImageView imgIcon;
        TextView tvTitle;

        public ProfileViewHolder(@NonNull View itemView) {
            super(itemView);
            // Các ID này phải trùng với ID trong file item_menu.xml
            imgIcon = itemView.findViewById(R.id.imgIcon);
            tvTitle = itemView.findViewById(R.id.tvTitle);
        }
    }
}