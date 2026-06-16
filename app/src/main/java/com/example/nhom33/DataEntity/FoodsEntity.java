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

    @ColumnInfo(name = "size")
    private String size;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "price")
    private double price;

    @ColumnInfo(name = "price_sale")
    private Double priceSale;

    @ColumnInfo(name = "image_url")
    private String imageUrl;

    @ColumnInfo(name = "is_new", defaultValue = "0")
    private int isNew; // 0: Không hoạt động, 1: Hoạt động

    @ColumnInfo(name = "is_best", defaultValue = "0")
    private int isBest; // 0: Không hoạt động, 1: Hoạt động

    @ColumnInfo(name = "is_available", defaultValue = "1")
    private int isAvailable; // 0: Không, 1: Có

    // Hàm khởi tạo không tham số bắt buộc của Room
    public FoodsEntity() {
    }

    // Hàm khởi tạo đầy đủ tham số (đã cập nhật theo bảng mới)
    public FoodsEntity(int categoryId, @NonNull String foodName, String size, String description, 
                       double price, Double priceSale, String imageUrl, int isNew, 
                       int isBest, int isAvailable) {
        this.categoryId = categoryId;
        this.foodName = foodName;
        this.size = size;
        this.description = description;
        this.price = price;
        this.priceSale = priceSale;
        this.imageUrl = imageUrl;
        this.isNew = isNew;
        this.isBest = isBest;
        this.isAvailable = isAvailable;
    }

    // --- GETTER & SETTER ---
    public int getFoodId() { return foodId; }
    public void setFoodId(int foodId) { this.foodId = foodId; }

    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    @NonNull
    public String getFoodName() { return foodName; }
    public void setFoodName(@NonNull String foodName) { this.foodName = foodName; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public Double getPriceSale() { return priceSale; }
    public void setPriceSale(Double priceSale) { this.priceSale = priceSale; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public int getIsNew() { return isNew; }
    public void setIsNew(int isNew) { this.isNew = isNew; }

    public int getIsBest() { return isBest; }
    public void setIsBest(int isBest) { this.isBest = isBest; }

    public int getIsAvailable() { return isAvailable; }
    public void setIsAvailable(int isAvailable) { this.isAvailable = isAvailable; }
}
