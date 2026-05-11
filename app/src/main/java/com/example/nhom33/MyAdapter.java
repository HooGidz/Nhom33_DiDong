package com.example.nhom33;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom33.database.item_food;
import com.example.nhom33.database.item_noti;
import com.example.nhom33.database.item_running_order;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
//    private List<item_noti> itemList;
//    public MyAdapter(List<item_noti> itemList){
//        this.itemList = itemList;
//    }
//    private List<item_food> itemList;
//    public MyAdapter(List<item_food> itemList){
//        this.itemList = itemList;
//    }
    private List<item_food> itemList;
    public MyAdapter(List<item_food> itemList){
        this.itemList = itemList;
    }


    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_item_myfood, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {

//        item_running_order item = itemList.get(position);
//        holder.img_food.setImageResource(item.getImg_food());
//        holder.txt_food.setText(item.getTxt_food());
//        holder.txt_id.setText(item.getTxt_id());
//        holder.txt_price.setText(item.getTxt_price());

        item_food item = itemList.get(position);
        holder.img_food.setImageResource(item.getImg_food());
        holder.txt_food.setText(item.getTxt_food());
        holder.txt_price.setText(item.getTxt_price());
        holder.txt_tag.setText(item.getTxt_tag());

//        item_noti item = itemList.get(position);
//        holder.img_user.setImageResource(item.getImg_user());
//        holder.img_food.setImageResource(item.getImg_food());
//        holder.txt_notification.setText(item.getTxt_notification());
//        holder.txt_time.setText(item.getTxt_time());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{

//        private ImageView img_food;
//        private TextView txt_food, txt_id, txt_price;
//        public MyViewHolder(@NonNull View itemView) {
//            super(itemView);
//            img_food = itemView.findViewById(R.id.img_food);
//            txt_food = itemView.findViewById(R.id.txt_food);
//            txt_id = itemView.findViewById(R.id.txt_id);
//            txt_price = itemView.findViewById(R.id.txt_price);

        private ImageView img_food;
        private TextView txt_food, txt_price, txt_tag;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img_food = itemView.findViewById(R.id.img_food);
            txt_food = itemView.findViewById(R.id.txt_food);
            txt_price = itemView.findViewById(R.id.txt_price);
            txt_tag = itemView.findViewById(R.id.txt_tag);

//        private ImageView img_user, img_food;
//        private TextView txt_notification, txt_time;
//        public MyViewHolder(@NonNull View itemView) {
//            super(itemView);
//            img_user = itemView.findViewById(R.id.img_user);
//            img_food = itemView.findViewById(R.id.img_food);
//            txt_notification = itemView.findViewById(R.id.txt_notification);
//            txt_time = itemView.findViewById(R.id.txt_time);
        }
    }
}
