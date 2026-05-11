package com.example.nhom33.controller;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom33.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class Main_track_order extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_track_order);
    }
/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_card0);
    }
    public void showFilterDialog() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.activity_add_card0, null);
        bottomSheetDialog.setContentView(view);

        // Xử lý nút Filter trong Dialog
        Button btnFilter = view.findViewById(R.id.btnApplyFilter);
        btnFilter.setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
        });

        bottomSheetDialog.show();
    }*/
}
