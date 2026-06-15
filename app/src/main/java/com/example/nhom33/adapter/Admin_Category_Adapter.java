package com.example.nhom33.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nhom33.DataEntity.CategoriesEntity;
import com.example.nhom33.R;

import java.util.List;

public class Admin_Category_Adapter extends RecyclerView.Adapter<Admin_Category_Adapter.MyViewHolder> {
    private List<CategoriesEntity> categoryList;
    private OnCategoryActionListener listener;

    public interface OnCategoryActionListener {
        void onEdit(CategoriesEntity category);
        void onDelete(CategoriesEntity category);
    }

    public Admin_Category_Adapter(List<CategoriesEntity> categoryList, OnCategoryActionListener listener) {
        this.categoryList = categoryList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_item_category, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CategoriesEntity category = categoryList.get(position);

        holder.txtCategoryName.setText(category.getCategoryName());
        holder.txtDescription.setText(category.getDescription());

        // Sử dụng Glide để load ảnh từ thư mục assets/imgg_product/
        String imageUrl = category.getImageUrl();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            String fullPath = "file:///android_asset/img_product/" + imageUrl;
            Glide.with(holder.itemView.getContext())
                    .load(fullPath)
                    .placeholder(R.drawable.fb) // Ảnh thay thế khi đang load
                    .error(R.drawable.fb)       // Ảnh hiển thị nếu lỗi
                    .into(holder.imgCategory);
        } else {
            holder.imgCategory.setImageResource(R.drawable.fb);
        }

        holder.btnEdit.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEdit(category);
            }
        });

        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDelete(category);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList != null ? categoryList.size() : 0;
    }

    public void updateData(List<CategoriesEntity> newList) {
        this.categoryList = newList;
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgCategory;
        public TextView txtCategoryName, txtDescription;
        public ImageButton btnEdit, btnDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCategory = itemView.findViewById(R.id.img_category);
            txtCategoryName = itemView.findViewById(R.id.txt_category_name);
            txtDescription = itemView.findViewById(R.id.txt_category_description);
            btnEdit = itemView.findViewById(R.id.btn_edit_category);
            btnDelete = itemView.findViewById(R.id.btn_delete_category);
        }
    }
}
