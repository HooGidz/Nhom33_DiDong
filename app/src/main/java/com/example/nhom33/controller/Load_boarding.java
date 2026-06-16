package com.example.nhom33.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.nhom33.R;

public class Load_boarding extends AppCompatActivity {
    // Khai báo biến toàn cục để các hàm đều dùng được
    private ViewPager2 viewPager;
    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_boarding);

        // Ánh xạ View
        viewPager = findViewById(R.id.viewPager);
        btnNext = findViewById(R.id.btnNext);

        // Thiết lập Adapter cho ViewPager2
        OnboardingAdapter adapter = new OnboardingAdapter();
        viewPager.setAdapter(adapter);

        // Xử lý sự kiện nút Next
        btnNext.setOnClickListener(v -> {
            if (viewPager.getCurrentItem() < 2) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            } else {
                // Đã đến trang cuối, chuyển sang màn hình Location
                Intent intent = new Intent(Load_boarding.this, LocationActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Thay đổi chữ trên nút khi trượt trang
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if (position == 2) {
                    btnNext.setText("GET STARTED");
                } else {
                    btnNext.setText("NEXT");
                }
            }
        });
    }

    // --- LỚP ADAPTER ĐỂ HIỂN THỊ 3 TRANG ---
    class OnboardingAdapter extends RecyclerView.Adapter<OnboardingAdapter.ViewHolder> {
        // Dữ liệu cho 3 trang (Ảnh, Tiêu đề, Mô tả)
        int[] images = {R.drawable.onboarding_fav, R.drawable.onboarding_chef, R.drawable.onboarding_delivery};
        String[] titles = {"All your favorites", "Order from chosen chef", "Free delivery offers"};
        String[] descs = {"Get all your loved foods in one place", "Just place the order, we do the rest", "Fast and free delivery for you"};

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_load_boarding, parent, false);
            return new ViewHolder(view);
        }


        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.img.setImageResource(images[position]);
            holder.title.setText(titles[position]);
            holder.desc.setText(descs[position]);
        }

        @Override
        public int getItemCount() { return 3; }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView img;
            TextView title, desc;
            ViewHolder(View itemView) {
                super(itemView);
                img = itemView.findViewById(R.id.imgOnboarding);
                title = itemView.findViewById(R.id.tvTitle);
                desc = itemView.findViewById(R.id.tvDesc);
            }
        }
    }
}