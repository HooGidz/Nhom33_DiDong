package com.example.nhom33.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom33.DataEntity.ProductReviewEntity;
import com.example.nhom33.DataEntity.ProductReviewWithDetails;
import com.example.nhom33.R;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    private List<ProductReviewWithDetails> reviewList;
    private boolean showOptions;

    public ReviewAdapter(List<ProductReviewWithDetails> reviewList) {
        this(reviewList, false);
    }

    public ReviewAdapter(List<ProductReviewWithDetails> reviewList, boolean showOptions) {
        this.reviewList = reviewList;
        this.showOptions = showOptions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductReviewWithDetails item = reviewList.get(position);
        ProductReviewEntity review = item.review;

        // 1. Ngày đánh giá (review_date)
        holder.txtDate.setText(review.getReviewDate() != null ? review.getReviewDate() : "N/A");

        // 2. Hiển thị Tên người dùng và Tên món ăn (Thay vì ID)
        String displayName = (item.fullName != null) ? item.fullName : "Người dùng #" + review.getUserId();
        String foodDisplay = (item.foodName != null) ? item.foodName : "Món #" + review.getFoodId();
        holder.txtTitle.setText(displayName + " - " + foodDisplay);

        // 3. Mã đơn hàng (txtOrderId)
        holder.txtOrderId.setText("Đơn hàng: #" + review.getOrderId());

        // 4. Số sao (rating)
        StringBuilder stars = new StringBuilder();
        int rating = review.getRating();
        for (int i = 0; i < rating; i++) {
            stars.append("★");
        }
        if (stars.length() == 0) stars.append("Chưa có đánh giá");
        holder.txtStars.setText(stars.toString());

        // 5. Bình luận (comment)
        holder.txtContent.setText(review.getComment() != null ? review.getComment() : "Không có bình luận.");

        // 6. Xử lý ảnh đánh giá (image_url)
        if (review.getImageUrl() != null && !review.getImageUrl().isEmpty()) {
            holder.imgReview.setVisibility(View.VISIBLE);
        } else {
            holder.imgReview.setVisibility(View.GONE);
        }

        // 7. Ảnh đại diện mặc định
        holder.imgAvatar.setImageResource(R.mipmap.ic_launcher);

        // 8. Xử lý nút tùy chọn (Dấu 3 chấm)
        if (showOptions && holder.btnMoreOptions != null) {
            holder.btnMoreOptions.setVisibility(View.VISIBLE);
            holder.btnMoreOptions.setOnClickListener(v -> {
                PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                popupMenu.getMenu().add(android.view.Menu.NONE, 1, 1, "Chỉnh sửa");
                popupMenu.getMenu().add(android.view.Menu.NONE, 2, 2, "Xóa");

                popupMenu.setOnMenuItemClickListener(menuItem -> {
                    int id = menuItem.getItemId();
                    int currentPosition = holder.getAdapterPosition();
                    if (currentPosition == RecyclerView.NO_POSITION) return false;

                    if (id == 1) {
                        Toast.makeText(v.getContext(), "Sửa đánh giá ID: " + reviewList.get(currentPosition).review.getReviewId(), Toast.LENGTH_SHORT).show();
                        return true;
                    } else if (id == 2) {
                        reviewList.remove(currentPosition);
                        notifyItemRemoved(currentPosition);
                        notifyItemRangeChanged(currentPosition, reviewList.size());
                        Toast.makeText(v.getContext(), "Đã xóa đánh giá", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    return false;
                });
                popupMenu.show();
            });
        } else if (holder.btnMoreOptions != null) {
            holder.btnMoreOptions.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return reviewList != null ? reviewList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtDate, txtTitle, txtOrderId, txtStars, txtContent;
        ImageView imgAvatar, btnMoreOptions, imgReview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtOrderId = itemView.findViewById(R.id.txtOrderId);
            txtStars = itemView.findViewById(R.id.txtStars);
            txtContent = itemView.findViewById(R.id.txtContent);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            btnMoreOptions = itemView.findViewById(R.id.imgMore);
            imgReview = itemView.findViewById(R.id.imgReview);
        }
    }
}
