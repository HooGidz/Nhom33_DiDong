package com.example.nhom33.DataEntity;

import androidx.room.Embedded;

public class ProductReviewWithDetails {
    @Embedded
    public ProductReviewEntity review;

    public String fullName;
    public String foodName;
}
