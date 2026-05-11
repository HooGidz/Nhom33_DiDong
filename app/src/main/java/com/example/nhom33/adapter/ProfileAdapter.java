package com.example.nhom33.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom33.R;

import java.util.List;

import com.example.nhom33.database.Profile;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder> {
    private Context context;
    private List<Profile> profileList;
    public ProfileAdapter(Context context, List<Profile> profileList) {
        this.context = context;
        this.profileList = profileList;
    }
    @Override
    public ProfileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_profile, parent, false);
        return new ProfileViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ProfileViewHolder holder, int position) {
        Profile profile = profileList.get(position);
        holder.tvMenuTitle.setText(profile.getTitle());
        holder.imgMenuIcon.setImageResource(profile.getIconResId());
        }
    @Override
    public int getItemCount() {
        return profileList.size();
    }
    public static class ProfileViewHolder extends RecyclerView.ViewHolder {
        TextView tvMenuTitle;
        ImageView imgMenuIcon;
        public ProfileViewHolder(View itemView) {
            super(itemView);
            tvMenuTitle = itemView.findViewById(R.id.tvMenuTitle);
            imgMenuIcon = itemView.findViewById(R.id.imgMenuIcon);
        }
    }

}
