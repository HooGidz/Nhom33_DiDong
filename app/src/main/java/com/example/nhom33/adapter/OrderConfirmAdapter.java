package com.example.nhom33.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.nhom33.R;
import com.example.nhom33.db.item_cart;
import java.util.List;
import java.util.Locale;

public class OrderConfirmAdapter extends RecyclerView.Adapter<OrderConfirmAdapter.ViewHolder> {
    private Context context;
    private List<item_cart> list;
    private OnItemChangeListener listener;

    public interface OnItemChangeListener {
        void onRemove(int position);
    }

    public OrderConfirmAdapter(Context context, List<item_cart> list, OnItemChangeListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_confirm, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        item_cart item = list.get(position);
        holder.txtName.setText(String.format("%sx %s", item.getQuantity(), item.getName()));
        holder.txtPrice.setText(String.format(Locale.getDefault(), "%,dđ", item.getPrice()));
        
        if (item.getOriginalPrice() > item.getPrice()) {
            holder.txtOldPrice.setVisibility(View.VISIBLE);
            holder.txtOldPrice.setText(String.format(Locale.getDefault(), "%,dđ", item.getOriginalPrice()));
            holder.txtOldPrice.setPaintFlags(holder.txtOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.txtOldPrice.setVisibility(View.GONE);
        }

        Glide.with(context)
                .load("file:///android_asset/img_product/" + item.getImageUrl())
                .placeholder(R.drawable.pizza_img)
                .error(R.drawable.pizza_img)
                .into(holder.imgProduct);

        // Sử dụng holder.getAdapterPosition() để tránh lỗi IndexOutOfBounds khi danh sách thay đổi
        holder.btnRemove.setOnClickListener(v -> {
            int currentPos = holder.getAdapterPosition();
            if (currentPos != RecyclerView.NO_POSITION && listener != null) {
                listener.onRemove(currentPos);
            }
        });
    }

    @Override
    public int getItemCount() { return list.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView txtName, txtPrice, txtOldPrice;
        ImageButton btnRemove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            txtName = itemView.findViewById(R.id.txtName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtOldPrice = itemView.findViewById(R.id.txtOldPrice);
            btnRemove = itemView.findViewById(R.id.btnRemove);
        }
    }
}
