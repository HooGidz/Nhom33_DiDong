package com.example.nhom33.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nhom33.DataEntity.UsersEntity;
import com.example.nhom33.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<UsersEntity> userList;
    private OnUserActionListener actionListener;

    public interface OnUserActionListener {
        void onDelete(UsersEntity user, int position);
        void onEdit(UsersEntity user, int position);
    }

    public UserAdapter(List<UsersEntity> userList, OnUserActionListener actionListener) {
        this.userList = userList;
        this.actionListener = actionListener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        UsersEntity user = userList.get(position);
        
        holder.tvFullName.setText(user.getFullName());
        holder.tvEmail.setText(user.getEmail());

        holder.btnDelete.setOnClickListener(v -> {
            if (actionListener != null) {
                actionListener.onDelete(user, holder.getAdapterPosition());
            }
        });

        holder.btnEdit.setOnClickListener(v -> {
            if (actionListener != null) {
                actionListener.onEdit(user, holder.getAdapterPosition());
            }
        });

        // Hiển thị ảnh đại diện (Áp dụng cho cả Admin và Người dùng)
        String avatar = user.getAvatar();
        if (!TextUtils.isEmpty(avatar)) {
            Object loadTarget;
            if (avatar.startsWith("http") || avatar.startsWith("https") || avatar.startsWith("file://")) {
                loadTarget = avatar;
            } else {
                // Tải từ thư mục assets/img_user/
                loadTarget = "file:///android_asset/img_user/" + avatar;
            }

            Glide.with(holder.itemView.getContext())
                    .load(loadTarget)
                    .placeholder(R.drawable.fb)
                    .error(R.drawable.fb)
                    .circleCrop()
                    .into(holder.imgAvatar);
        } else {
            // Hiện icon mặc định nếu không có dữ liệu ảnh trong CSDL
            holder.imgAvatar.setImageResource(R.drawable.fb);
        }
    }

    @Override
    public int getItemCount() {
        return userList != null ? userList.size() : 0;
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView tvFullName, tvEmail;
        ImageView imgAvatar;
        ImageButton btnDelete, btnEdit;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFullName = itemView.findViewById(R.id.tvFullName);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            imgAvatar = itemView.findViewById(R.id.imgUserAvatar);
            btnDelete = itemView.findViewById(R.id.btnDeleteUser);
            btnEdit = itemView.findViewById(R.id.btnEditUser);
        }
    }
}
