package com.example.nhom33.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nhom33.DAO.OrderDetailsDAO;
import com.example.nhom33.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductReviewAdapter extends RecyclerView.Adapter<ProductReviewAdapter.ViewHolder> {
    private Context context;
    private List<OrderDetailsDAO.OrderDetailWithFood> productList;
    private Map<Integer, Float> ratings = new HashMap<>();
    private Map<Integer, String> comments = new HashMap<>();

    public ProductReviewAdapter(Context context, List<OrderDetailsDAO.OrderDetailWithFood> productList) {
        this.context = context;
        this.productList = productList;
        for (int i = 0; i < productList.size(); i++) {
            ratings.put(i, 5.0f);
            comments.put(i, "");
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item_product_review, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Gỡ bỏ listener cũ để tránh lỗi khi RecyclerView tái sử dụng View
        holder.ratingBar.setOnRatingBarChangeListener(null);
        if (holder.textWatcher != null) {
            holder.edtReviewContent.removeTextChangedListener(holder.textWatcher);
        }

        OrderDetailsDAO.OrderDetailWithFood item = productList.get(position);
        holder.tvProductName.setText(item.quantity + "x " + item.food_name);

        String fullPath = "file:///android_asset/img_product/" + item.image_url;
        Glide.with(context).load(fullPath).placeholder(R.mipmap.ic_launcher).into(holder.imgProduct);

        // Khôi phục dữ liệu từ Map
        holder.ratingBar.setRating(ratings.get(position));
        holder.edtReviewContent.setText(comments.get(position));

        // Lưu thay đổi số sao
        holder.ratingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            if (fromUser) {
                ratings.put(holder.getAdapterPosition(), rating);
            }
        });

        // Lưu thay đổi nội dung bình luận
        holder.textWatcher = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                int pos = holder.getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    comments.put(pos, s.toString());
                }
            }
        };
        holder.edtReviewContent.addTextChangedListener(holder.textWatcher);
    }

    @Override
    public int getItemCount() {
        return productList != null ? productList.size() : 0;
    }

    public List<OrderDetailsDAO.OrderDetailWithFood> getProductList() { return productList; }
    public float getRating(int position) { return ratings.get(position); }
    public String getComment(int position) { return comments.get(position); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView tvProductName;
        RatingBar ratingBar;
        EditText edtReviewContent;
        TextWatcher textWatcher;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            edtReviewContent = itemView.findViewById(R.id.edtReviewContent);
        }
    }
}