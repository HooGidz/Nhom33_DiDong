package com.example.nhom33.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom33.R;
import com.example.nhom33.database.item_cart;

import java.util.List;

public class Cart_Adapter extends RecyclerView.Adapter<Cart_Adapter.ViewHolder> {

    List<item_cart> list;

    public Cart_Adapter(List<item_cart> list) {
        this.list = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView name, quantity, price;

        public ViewHolder(View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img);
            name = itemView.findViewById(R.id.txtName);
            quantity = itemView.findViewById(R.id.txtQuantity);
            price = itemView.findViewById(R.id.txtPrice);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        item_cart item = list.get(position);
        holder.name.setText(item.getName());
        holder.quantity.setText(item.getQuantity());
        holder.price.setText("$" + item.getPrice());
        holder.img.setImageResource(item.getImage());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
