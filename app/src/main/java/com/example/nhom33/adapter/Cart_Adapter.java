package com.example.nhom33.adapter;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom33.R;
import com.example.nhom33.db.item_cart;

import java.util.List;
import java.util.Locale;

public class Cart_Adapter extends RecyclerView.Adapter<Cart_Adapter.ViewHolder> {

    private List<item_cart> list;
    private OnCartActionListener listener;

    public interface OnCartActionListener {
        void onPlus(item_cart item, int position);
        void onMinus(item_cart item, int position);
        void onRemove(item_cart item, int position);
    }

    public Cart_Adapter(List<item_cart> list, OnCartActionListener listener) {
        this.list = list;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name, quantity, price, originalPrice;
        ImageButton btnPlus, btnMinus, btnRemove;

        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            name = itemView.findViewById(R.id.txtName);
            quantity = itemView.findViewById(R.id.txtQuantity);
            price = itemView.findViewById(R.id.txtPrice);
            originalPrice = itemView.findViewById(R.id.txtOriginalPrice);
            btnPlus = itemView.findViewById(R.id.btnPlus);
            btnMinus = itemView.findViewById(R.id.btnMinus);
            btnRemove = itemView.findViewById(R.id.btnRemove);
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
        
        holder.price.setText(String.format(Locale.getDefault(), "%,d VNĐ", item.getPrice()));
        
        if (item.getOriginalPrice() > item.getPrice()) {
            holder.originalPrice.setVisibility(View.VISIBLE);
            holder.originalPrice.setText(String.format(Locale.getDefault(), "%,d VNĐ", item.getOriginalPrice()));
            holder.originalPrice.setPaintFlags(holder.originalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.originalPrice.setVisibility(View.GONE);
        }

        holder.img.setImageResource(item.getImage());

        holder.btnPlus.setOnClickListener(v -> {
            if (listener != null) listener.onPlus(item, position);
        });

        holder.btnMinus.setOnClickListener(v -> {
            if (listener != null) listener.onMinus(item, position);
        });

        holder.btnRemove.setOnClickListener(v -> {
            if (listener != null) listener.onRemove(item, position);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
