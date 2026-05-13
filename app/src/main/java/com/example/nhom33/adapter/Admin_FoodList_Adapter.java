package com.example.nhom33.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom33.R;
import com.example.nhom33.database.item_food;

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

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView img_food;
        private TextView txt_food, txt_price, txt_tag;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img_food = itemView.findViewById(R.id.img_food);
            txt_food = itemView.findViewById(R.id.txt_food);
            txt_price = itemView.findViewById(R.id.txt_price);
            txt_tag = itemView.findViewById(R.id.txt_tag);
        }
    }
}
