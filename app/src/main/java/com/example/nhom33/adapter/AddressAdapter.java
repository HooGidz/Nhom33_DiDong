package com.example.nhom33.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom33.R;

import java.util.List;

import com.example.nhom33.database.Address;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {

    private List<Address> addressList;

    // Constructor để nhận danh sách dữ liệu
    public AddressAdapter(List<Address> addressList) {
        this.addressList = addressList;
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Nạp file giao diện item_address.xml vào code
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_address, parent, false);
        return new AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
        Address item = addressList.get(position);

        // Đổ dữ liệu từ Model vào TextView
        holder.tvLabel.setText(item.getLabel());
        holder.tvAddressDetail.setText(item.getAddress());

        // Logic tự động đổi Icon dựa trên Label
        if (item.getLabel().equalsIgnoreCase("HOME")) {
            holder.ivTypeIcon.setImageResource(android.R.drawable.ic_menu_myplaces);
            // Bạn có thể đổi màu icon tại đây nếu muốn (ví dụ màu xanh cho Home)
            holder.ivTypeIcon.setColorFilter(0xFF1E88E5);
        } else {
            holder.ivTypeIcon.setImageResource(android.R.drawable.ic_menu_agenda);
            // Màu tím cho Work
            holder.ivTypeIcon.setColorFilter(0xFF8E24AA);
        }
    }

    @Override
    public int getItemCount() {
        return addressList != null ? addressList.size() : 0;
    }

    // Lớp giữ các thành phần giao diện của mỗi dòng
    public static class AddressViewHolder extends RecyclerView.ViewHolder {
        TextView tvLabel, tvAddressDetail;
        ImageView ivTypeIcon;

        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLabel = itemView.findViewById(R.id.tvLabel);
            tvAddressDetail = itemView.findViewById(R.id.tvAddressDetail);
            ivTypeIcon = itemView.findViewById(R.id.ivTypeIcon);
        }
    }
}