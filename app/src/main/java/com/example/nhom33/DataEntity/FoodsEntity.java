package com.example.nhom33.DataEntity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(
        tableName = "Foods",
        foreignKeys = @ForeignKey(
                entity = CategoriesEntity.class,
                parentColumns = "category_id",
                childColumns = "category_id",
                onDelete = ForeignKey.CASCADE
        )
)
public class FoodsEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "food_id")
    private int foodId;

    @ColumnInfo(name = "category_id")
    private int categoryId;

    @NonNull
    @ColumnInfo(name = "food_name")
    private String foodName;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "price")
    private double price;

    @ColumnInfo(name = "price_sale")
    private Double priceSale;

    @ColumnInfo(name = "image_url")
    private String imageUrl;

    @ColumnInfo(name = "is_available")
    private int isAvailable;

    @ColumnInfo(name = "meal_type")
    private String mealType;

    // Hàm khởi tạo không tham số bắt buộc của Room
    public FoodsEntity() {
    }

    // Hàm khởi tạo đầy đủ tham số
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

    // --- TOÀN BỘ GETTER & SETTER CHUẨN ---
    public int getFoodId() { return foodId; }
    public void setFoodId(int foodId) { this.foodId = foodId; }

    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    @NonNull
    public String getFoodName() { return foodName; }
    public void setFoodName(@NonNull String foodName) { this.foodName = foodName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public Double getPriceSale() { return priceSale; }
    public void setPriceSale(Double priceSale) { this.priceSale = priceSale; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public int getIsAvailable() { return isAvailable; }
    public void setIsAvailable(int isAvailable) { this.isAvailable = isAvailable; }

    public String getMealType() { return mealType; }
    public void setMealType(String mealType) { this.mealType = mealType; }
}