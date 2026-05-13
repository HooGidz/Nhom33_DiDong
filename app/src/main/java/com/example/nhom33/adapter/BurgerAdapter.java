package com.example.nhom33.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom33.R;
import com.example.nhom33.controller.Burger;
import com.example.nhom33.controller.DetailsActivity;

import java.util.List;

public class BurgerAdapter extends RecyclerView.Adapter<BurgerAdapter.BurgerViewHolder> {

    // 1. Khai báo danh sách dữ liệu
    private List<Burger> burgerList;

    public BurgerAdapter(List<Burger> burgerList) {
        this.burgerList = burgerList;
    }

    @NonNull
    @Override
    public BurgerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Kết nối với item_burger.xml
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_burger, parent, false);
        return new BurgerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BurgerViewHolder holder, int position) {
        // Đổ dữ liệu vào View
        Burger burger = burgerList.get(position);
        holder.txtName.setText(burger.getName());
        holder.txtRest.setText(burger.getRestaurant());
        holder.txtPrice.setText(burger.getPrice());
        holder.imgFood.setImageResource(burger.getImageRes());

        // Xử lý sự kiện click vào item để chuyển sang DetailsActivity
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailsActivity.class);
                // Bạn có thể gửi thêm dữ liệu nếu cần, ví dụ:
                // intent.putExtra("burger_name", burger.getName());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return burgerList != null ? burgerList.size() : 0;
    }

    // 2. Class ViewHolder (Nằm bên TRONG BurgerAdapter)
    public static class BurgerViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtRest, txtPrice;
        ImageView imgFood;

        public BurgerViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtRest = itemView.findViewById(R.id.txtRest);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            imgFood = itemView.findViewById(R.id.imgBurger);
        }
    }
}