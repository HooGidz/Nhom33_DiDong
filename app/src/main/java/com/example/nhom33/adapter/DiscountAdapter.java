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

import com.example.nhom33.DataEntity.FoodsEntity;
import com.example.nhom33.R;
import com.example.nhom33.controller.DetailsActivity;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_discount_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FoodsEntity food = foodList.get(position);
        holder.txtName.setText(food.getFoodName());
        holder.txtMealType.setText(food.getMealType() != null ? food.getMealType() : "Cả ngày");
        
        double priceToShow = (food.getPriceSale() != null && food.getPriceSale() > 0) ? food.getPriceSale() : food.getPrice();
        holder.txtPriceSale.setText(String.format(Locale.getDefault(), "%,.0f VNĐ", priceToShow));

        String imgName = food.getImageUrl();
        if (imgName != null && !imgName.isEmpty()) {
            if (imgName.contains(".")) imgName = imgName.substring(0, imgName.lastIndexOf("."));
            int resId = context.getResources().getIdentifier(imgName, "drawable", context.getPackageName());
            if (resId != 0) holder.imgFood.setImageResource(resId);
            else holder.imgFood.setImageResource(R.drawable.fb);
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtMealType, txtPriceSale, txtPush;
        ImageView imgFood;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtMealType = itemView.findViewById(R.id.txtMealType);
            txtPriceSale = itemView.findViewById(R.id.txtPriceSale);
            txtPush = itemView.findViewById(R.id.txtPush);
            imgFood = itemView.findViewById(R.id.imgFood);
        }
    }
}
