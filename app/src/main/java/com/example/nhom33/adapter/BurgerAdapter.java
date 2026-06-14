package com.example.nhom33.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom33.DataEntity.FoodsEntity;
import com.example.nhom33.R;
import com.example.nhom33.controller.DetailsActivity;

import java.util.List;
import java.util.Locale;

public class BurgerAdapter extends RecyclerView.Adapter<BurgerAdapter.BurgerViewHolder> {

    private List<FoodsEntity> foodList;
    private Context context;

    public BurgerAdapter(Context context, List<FoodsEntity> foodList) {
        this.context = context;
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public BurgerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_burger, parent, false);
        return new BurgerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BurgerViewHolder holder, int position) {
        FoodsEntity food = foodList.get(position);
        holder.txtName.setText(food.getFoodName());
        holder.txtRest.setText(food.getDescription());

        // Xử lý hiển thị giá
        if (food.getPriceSale() != null && food.getPriceSale() > 0) {
            // Có giảm giá
            holder.txtPriceSale.setVisibility(View.VISIBLE);
            holder.txtPriceSale.setText(String.format(Locale.getDefault(), "%,.0f VNĐ", food.getPriceSale()));
            
            // Giá gốc hiển thị nhỏ hơn và có gạch ngang
            holder.txtPrice.setText(String.format(Locale.getDefault(), "%,.0f VNĐ", food.getPrice()));
            holder.txtPrice.setPaintFlags(holder.txtPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.txtPrice.setTextColor(context.getResources().getColor(android.R.color.darker_gray));
        } else {
            // Không giảm giá
            holder.txtPriceSale.setVisibility(View.GONE);
            holder.txtPrice.setText(String.format(Locale.getDefault(), "%,.0f VNĐ", food.getPrice()));
            holder.txtPrice.setPaintFlags(holder.txtPrice.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            holder.txtPrice.setTextColor(context.getResources().getColor(R.color.black)); // Đảm bảo màu sắc đúng
        }

        // Load ảnh từ drawable
        String imgName = food.getImageUrl();
        if (imgName != null && !imgName.isEmpty()) {
            if (imgName.contains(".")) imgName = imgName.substring(0, imgName.lastIndexOf("."));
            int resId = context.getResources().getIdentifier(imgName, "drawable", context.getPackageName());
            if (resId != 0) holder.imgFood.setImageResource(resId);
            else holder.imgFood.setImageResource(R.drawable.pizza_img);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("FOOD_ID", food.getFoodId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return foodList != null ? foodList.size() : 0;
    }

    public static class BurgerViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtRest, txtPrice, txtPriceSale;
        ImageView imgFood;

        public BurgerViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtRest = itemView.findViewById(R.id.txtRest);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtPriceSale = itemView.findViewById(R.id.txtPriceSale);
            imgFood = itemView.findViewById(R.id.imgBurger);
        }
    }
}