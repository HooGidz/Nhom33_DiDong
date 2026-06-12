package com.example.nhom33.DataEntity; // Thay đổi package cho đúng với thư mục thực tế của bạn

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(
        tableName = "Foods",
        foreignKeys = @ForeignKey(
                entity = CategoriesEntity.class, // Tên lớp Entity của bảng Categories của bạn
                parentColumns = "category_id",
                childColumns = "category_id",
                onDelete = ForeignKey.CASCADE // Tự động xóa món ăn nếu danh mục bị xóa (hoặc dùng NO_ACTION)
        )
)
public class FoodsEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "food_id")
    private int foodId; // Kiểu int để đảm bảo notNull = true khớp với hệ thống

    @ColumnInfo(name = "category_id")
    private int categoryId; // Kiểu int để đảm bảo notNull = true khớp with hệ thống

    @NonNull // Trường này bắt buộc phải có vì SQL khai báo NOT NULL
    @ColumnInfo(name = "food_name")
    private String foodName;

    @ColumnInfo(name = "description")
    private String description; // Cho phép null

    @ColumnInfo(name = "price")
    private double price; // REAL trong SQLite tương ứng với double trong Java

    //@Ignore
    private Double priceSale; // Sử dụng Double (viết hoa) vì trường này có thể NULL

    @ColumnInfo(name = "image_url")
    private String imageUrl; // Cho phép null

    //@Ignore
    private int isAvailable; // Thay vì boolean, dùng int (0 hoặc 1) để khớp hoàn toàn với INTEGER của SQLite

    @ColumnInfo(name = "meal_type")
    private String mealType; // Chứa các giá trị 'Sáng', 'Trưa', 'Tối', 'Cả ngày'

    // ==================== CONSTRUCTOR ====================

    public FoodsEntity() {
    }

    public FoodsEntity(int categoryId, @NonNull String foodName, String description, double price, Double priceSale, String imageUrl, int isAvailable, String mealType) {
        this.categoryId = categoryId;
        this.foodName = foodName;
        this.description = description;
        this.price = price;
        this.priceSale = priceSale;
        this.imageUrl = imageUrl;
        this.isAvailable = isAvailable;
        this.mealType = mealType;
    }

    // ==================== GETTER VÀ SETTER ====================

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @NonNull
    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(@NonNull String foodName) {
        this.foodName = foodName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Double getPriceSale() {
        return priceSale;
    }

    public void setPriceSale(Double priceSale) {
        this.priceSale = priceSale;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(int isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }
}
