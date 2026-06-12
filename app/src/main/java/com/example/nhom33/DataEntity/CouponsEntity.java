package com.example.nhom33.DataEntity; // Thay đổi package này cho đúng thư mục dự án của bạn

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

// Khai báo bảng và thiết lập chỉ mục UNIQUE cho coupon_code ngay tại đây
@Entity(
        tableName = "Coupons",
        indices = {@Index(value = {"coupon_code"}, unique = true)}
)
public class CouponsEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "coupon_id")
    private int couponId;

    @ColumnInfo(name = "coupon_code")
    private String couponCode;

    @ColumnInfo(name = "discount_percent")
    private Integer discountPercent; // Dùng Integer thay vì int đề phòng trường hợp giá trị dưới CSDL bị NULL

    @ColumnInfo(name = "discount_amount")
    private Double discountAmount; // REAL trong SQLite tương ứng với Double trong Java

    @ColumnInfo(name = "min_order_value")
    private Double minOrderValue;

    @ColumnInfo(name = "expiry_date")
    private String expiryDate; // SQLite không có kiểu DATE riêng, thường được lưu dưới dạng chuỗi TEXT/DATETIME

    @ColumnInfo(name = "is_active", defaultValue = "1")
    private int isActive;

    // --- CONSTRUCTOR ---
    public CouponsEntity(String couponCode, Integer discountPercent, Double discountAmount,
                         Double minOrderValue, String expiryDate, int isActive) {
        this.couponCode = couponCode;
        this.discountPercent = discountPercent;
        this.discountAmount = discountAmount;
        this.minOrderValue = minOrderValue;
        this.expiryDate = expiryDate;
        this.isActive = isActive;
    }

    // --- GETTERS & SETTERS (Bắt buộc phải có để Room hoạt động với Java) ---
    public int getCouponId() { return couponId; }
    public void setCouponId(int couponId) { this.couponId = couponId; }

    public String getCouponCode() { return couponCode; }
    public void setCouponCode(String couponCode) { this.couponCode = couponCode; }

    public Integer getDiscountPercent() { return discountPercent; }
    public void setDiscountPercent(Integer discountPercent) { this.discountPercent = discountPercent; }

    public Double getDiscountAmount() { return discountAmount; }
    public void setDiscountAmount(Double discountAmount) { this.discountAmount = discountAmount; }

    public Double getMinOrderValue() { return minOrderValue; }
    public void setMinOrderValue(Double minOrderValue) { this.minOrderValue = minOrderValue; }

    public String getExpiryDate() { return expiryDate; }
    public void setExpiryDate(String expiryDate) { this.expiryDate = expiryDate; }

    public int getIsActive() { return isActive; }
    public void setIsActive(int isActive) { this.isActive = isActive; }
}