package com.example.nhom33.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nhom33.controller.AdminEditFoodActivity;
import com.example.nhom33.R;
import com.example.nhom33.db.item_food;
import com.example.nhom33.Database.FoodDB;

import java.util.List;

public class Admin_FoodList_Adapter extends RecyclerView.Adapter<Admin_FoodList_Adapter.MyViewHolder>{
    private List<item_food> itemList;

    public Admin_FoodList_Adapter(List<item_food> itemList){
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public Admin_FoodList_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_item_myfood, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Admin_FoodList_Adapter.MyViewHolder holder, int position) {
        item_food item = itemList.get(position);
        
        // Load ảnh từ assets/img_product/ bằng Glide
        String imgName = item.getImg_url();
        String fullPath = "file:///android_asset/img_product/" + imgName;
        
        Glide.with(holder.itemView.getContext())
                .load(fullPath)
                .placeholder(R.drawable.fb)
                .error(R.drawable.fb)
                .into(holder.img_food);

        holder.txt_food.setText(item.getTxt_food());
        holder.txt_price.setText(item.getTxt_price());
        holder.txt_size.setText(item.getTxt_size() != null ? item.getTxt_size() : "N/A");

        // Sự kiện cho nút menu (Sửa/Xóa)
        holder.btn_detail.setOnClickListener(v -> {
            Context context = v.getContext();
            PopupMenu popupMenu = new PopupMenu(context, v);
            popupMenu.getMenuInflater().inflate(R.menu.menu_food_options, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(menuItem -> {
                int id = menuItem.getItemId();
                if (id == R.id.menu_edit) {
                    Intent intent = new Intent(context, AdminEditFoodActivity.class);
                    intent.putExtra("FOOD_ID", item.getFood_id());
                    context.startActivity(intent);
                    return true;
                } else if (id == R.id.menu_delete) {
                    showDeleteConfirmationDialog(context, item, holder.getAdapterPosition());
                    return true;
                }
                return false;
            });
            popupMenu.show();
        });

    }

    private void showDeleteConfirmationDialog(Context context, item_food item, int position) {
        if (position == RecyclerView.NO_POSITION) return;

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Xác nhận xóa món");
        builder.setMessage("Bạn có chắc chắn muốn xóa món '" + item.getTxt_food() + "' này không?");

        builder.setPositiveButton("Có", (dialog, which) -> {
            new Thread(() -> {
                try {
                    FoodDB.getInstance(context).foodsDAO().deleteFoodById(item.getFood_id());
                    new Handler(Looper.getMainLooper()).post(() -> {
                        if (position < itemList.size() && itemList.get(position).getFood_id() == item.getFood_id()) {
                            itemList.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, itemList.size());
                            Toast.makeText(context, "Đã xóa món ăn thành công!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    new Handler(Looper.getMainLooper()).post(() -> 
                        Toast.makeText(context, "Lỗi khi xóa: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                }
            }).start();
        });

        builder.setNegativeButton("Không", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }

    @Override
    public int getItemCount() {
        return itemList != null ? itemList.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView img_food;
        private TextView txt_food, txt_price, txt_size;
        private ImageButton btn_detail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img_food = itemView.findViewById(R.id.img_food);
            txt_food = itemView.findViewById(R.id.txt_food);
            txt_price = itemView.findViewById(R.id.txt_price);
            txt_size = itemView.findViewById(R.id.txt_size);
            btn_detail = itemView.findViewById(R.id.btn_detail);
        }
    }
}
