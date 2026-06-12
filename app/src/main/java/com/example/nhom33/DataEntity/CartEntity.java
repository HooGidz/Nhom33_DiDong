package com.example.nhom33.DataEntity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "Cart",
        foreignKeys = {
                @ForeignKey(
                        entity = UsersEntity.class, // Tên lớp Entity của bảng Users
                        parentColumns = "user_id",
                        childColumns = "user_id",
                        onDelete = ForeignKey.CASCADE // Tự động xóa giỏ hàng nếu user bị xóa (tùy chọn)
                ),
                @ForeignKey(
                        entity = FoodsEntity.class, // Tên lớp Entity của bảng Foods
                        parentColumns = "food_id",
                        childColumns = "food_id",
                        onDelete = ForeignKey.CASCADE // Tự động xóa món trong giỏ nếu món ăn đó bị xóa khỏi menu
                )
        }
)
public class CartEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cart_id")
    private int cartId;

    @ColumnInfo(name = "user_id")
    private int userId;

    @ColumnInfo(name = "food_id")
    private int foodId;

    @ColumnInfo(name = "quantity")
    private int quantity;

    @ColumnInfo(name = "price_at_time")
    private double priceAtTime; // Kiểu REAL trong SQLite tương ứng với double/float trong Java

    // --- Constructor ---
    public CartEntity(int userId, int foodId, int quantity, double priceAtTime) {
        this.userId = userId;
        this.foodId = foodId;
        this.quantity = quantity;
        this.priceAtTime = priceAtTime;
    }

    // --- Getter và Setter ---
    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPriceAtTime() {
        return priceAtTime;
    }

    public void setPriceAtTime(double priceAtTime) {
        this.priceAtTime = priceAtTime;
    }
}
