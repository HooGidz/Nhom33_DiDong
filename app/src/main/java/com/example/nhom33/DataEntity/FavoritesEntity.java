package com.example.nhom33.DataEntity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "Favorites",
        foreignKeys = {
                @ForeignKey(
                        entity = FoodsEntity.class, // Bạn cần có file FoodsEntity tương ứng với bảng Foods
                        parentColumns = "food_id",
                        childColumns = "food_id",
                        onDelete = ForeignKey.CASCADE // Tùy chọn: Xóa món ăn thì tự động xóa khỏi danh sách yêu thích
                ),

                @ForeignKey(
                        entity = UsersEntity.class, // Tương ứng với bảng Users
                        parentColumns = "user_id",
                        childColumns = "user_id",
                        onDelete = ForeignKey.CASCADE
                )
        }
)
public class FavoritesEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "favorite_id")
    private int favoriteId;

    @ColumnInfo(name = "user_id")
    private int userId;

    @ColumnInfo(name = "food_id")
    private Integer foodId; // Dùng Integer thay vì int nếu cột này có thể chứa giá trị NULL


    // --- Constructor ---
    public FavoritesEntity(int userId, Integer foodId) {
        this.userId = userId;
        this.foodId = foodId;
    }

    // --- Getter và Setter ---
    public int getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(int favoriteId) {
        this.favoriteId = favoriteId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Integer getFoodId() {
        return foodId;
    }

    public void setFoodId(Integer foodId) {
        this.foodId = foodId;
    }
}