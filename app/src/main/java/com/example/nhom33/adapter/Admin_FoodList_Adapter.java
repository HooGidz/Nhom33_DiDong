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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

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
        holder.img_food.setImageResource(item.getImg_food());
        holder.txt_food.setText(item.getTxt_food());
        holder.txt_price.setText(item.getTxt_price());
        holder.txt_tag.setText(item.getTxt_tag());

        holder.btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                PopupMenu popupMenu = new PopupMenu(context, v);
                popupMenu.getMenuInflater().inflate(R.menu.menu_food_options, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        int id = menuItem.getItemId();
                        if (id == R.id.menu_edit) {
                            Intent intent = new Intent(context, AdminEditFoodActivity.class);
                            intent.putExtra("FOOD_ID", item.getFood_id());
                            context.startActivity(intent);
                            return true;
                        } else if (id == R.id.menu_delete) {
                            // Sử dụng holder.getAdapterPosition() để lấy vị trí mới nhất
                            showDeleteConfirmationDialog(context, item, holder.getAdapterPosition());
                            return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    private void showDeleteConfirmationDialog(Context context, item_food item, int position) {
        if (position == RecyclerView.NO_POSITION) return;

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Xác nhận xóa món");
        builder.setMessage("Bạn có chắc chắn muốn xóa món '" + item.getTxt_food() + "' này không?");

        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new Thread(() -> {
                    try {
                        // Gọi hàm xóa theo ID vừa thêm trong DAO
                        FoodDB.getInstance(context).foodsDAO().deleteFoodById(item.getFood_id());

                        new Handler(Looper.getMainLooper()).post(() -> {
                            // Kiểm tra lại vị trí trước khi xóa khỏi list để tránh crash
                            if (position < itemList.size() && itemList.get(position).getFood_id() == item.getFood_id()) {
                                itemList.remove(position);
                                notifyItemRemoved(position);
                                // Cập nhật lại dải index cho các item còn lại
                                notifyItemRangeChanged(position, itemList.size());
                                Toast.makeText(context, "Đã xóa món ăn thành công!", Toast.LENGTH_SHORT).show();
                            } else {
                                // Nếu vị trí không khớp (do danh sách đã thay đổi), tìm lại index chính xác
                                int currentIdx = -1;
                                for(int i=0; i<itemList.size(); i++) {
                                    if(itemList.get(i).getFood_id() == item.getFood_id()) {
                                        currentIdx = i;
                                        break;
                                    }
                                }
                                if (currentIdx != -1) {
                                    itemList.remove(currentIdx);
                                    notifyItemRemoved(currentIdx);
                                    notifyItemRangeChanged(currentIdx, itemList.size());
                                    Toast.makeText(context, "Đã xóa món ăn thành công!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } catch (Exception e) {
                        new Handler(Looper.getMainLooper()).post(() -> 
                            Toast.makeText(context, "Lỗi khi xóa: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    }
                }).start();
            }
        });

        builder.setNegativeButton("Không", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView img_food;
        private TextView txt_food, txt_price, txt_tag;
        private ImageButton btn_detail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img_food = itemView.findViewById(R.id.img_food);
            txt_food = itemView.findViewById(R.id.txt_food);
            txt_price = itemView.findViewById(R.id.txt_price);
            txt_tag = itemView.findViewById(R.id.txt_tag);
            btn_detail = itemView.findViewById(R.id.btn_detail);
        }
    }
}