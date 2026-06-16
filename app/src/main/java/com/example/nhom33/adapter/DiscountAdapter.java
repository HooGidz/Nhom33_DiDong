package com.example.nhom33.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nhom33.DataEntity.FoodsEntity;
import com.example.nhom33.R;
import com.example.nhom33.controller.User_Food_Detail;

import java.util.List;
import java.util.Locale;

public class DiscountAdapter extends RecyclerView.Adapter<DiscountAdapter.ViewHolder> {

    private List<FoodsEntity> foodList;
    private Context context;

    public DiscountAdapter(Context context, List<FoodsEntity> foodList) {
        this.context = context;
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_discount, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FoodsEntity food = foodList.get(position);
        holder.txtName.setText(food.getFoodName());
        
        // Cập nhật giao diện: Sử dụng txtSize hiển thị thông tin getSize() từ FoodsEntity
        holder.txtSize.setText(food.getSize() != null ? food.getSize() : "N/A");
        
        double priceToShow = (food.getPriceSale() != null && food.getPriceSale() > 0) ? food.getPriceSale() : food.getPrice();
        holder.txtPriceSale.setText(String.format(Locale.getDefault(), "%,.0f VNĐ", priceToShow));

        // Load ảnh từ assets/img_product/ bằng Glide
        String imgName = food.getImageUrl();
        String fullPath = "file:///android_asset/img_product/" + imgName;
        
        Glide.with(context)
                .load(fullPath)
                .placeholder(R.drawable.fb)
                .error(R.drawable.fb)
                .into(holder.imgFood);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, User_Food_Detail.class);
            intent.putExtra("FOOD_ID", food.getFoodId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return foodList != null ? foodList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtSize, txtPriceSale, txtPush;
        ImageView imgFood;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtSize = itemView.findViewById(R.id.txtSize); // Đã đổi ID từ txtMealType thành txtSize
            txtPriceSale = itemView.findViewById(R.id.txtPriceSale);
            imgFood = itemView.findViewById(R.id.imgFood);
        }
    }
}
