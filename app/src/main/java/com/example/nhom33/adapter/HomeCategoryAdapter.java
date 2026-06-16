package com.example.nhom33.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nhom33.DataEntity.CategoriesEntity;
import com.example.nhom33.R;

import java.util.List;

public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.ViewHolder> {
    private List<CategoriesEntity> categoryList;
    private Context context;
    private OnCategoryClickListener listener;

    public interface OnCategoryClickListener {
        void onCategoryClick(CategoriesEntity category);
    }

    public HomeCategoryAdapter(Context context, List<CategoriesEntity> categoryList, OnCategoryClickListener listener) {
        this.context = context;
        this.categoryList = categoryList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_category_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoriesEntity category = categoryList.get(position);
        holder.txtName.setText(category.getCategoryName());

        // SỬA: Load ảnh từ assets/img_cate/ thay vì img_product
        String imgName = category.getImageUrl();
        if (imgName != null && !imgName.isEmpty()) {
            String fullPath = "file:///android_asset/img_cate/" + imgName;
            Glide.with(context)
                    .load(fullPath)
                    .placeholder(R.drawable.fb)
                    .error(R.drawable.fb)
                    .into(holder.imgCategory);
        } else {
            holder.imgCategory.setImageResource(R.drawable.fb);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCategoryClick(category);
            }
        });
    }

    @Override
    public int getItemCount() { return categoryList != null ? categoryList.size() : 0; }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCategory;
        TextView txtName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCategory = itemView.findViewById(R.id.img_category);
            txtName = itemView.findViewById(R.id.txt_category_name);
        }
    }
}
