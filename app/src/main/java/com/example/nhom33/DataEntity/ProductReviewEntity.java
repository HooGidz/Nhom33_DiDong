package com.example.nhom33.DataEntity; // Thay đổi package này cho đúng với cấu trúc thư mục của bạn

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

// Định nghĩa Entity, tên bảng và các ràng buộc Khóa ngoại (Foreign Key)
@Entity(
        tableName = "ProductReview",
        foreignKeys = {
                @ForeignKey(
                        entity = FoodsEntity.class, // Tên lớp Entity của bảng Foods
                        parentColumns = "food_id",
                        childColumns = "food_id",
                        onDelete = ForeignKey.CASCADE // Hoặc NO_ACTION tùy theo logic của bạn
                ),
                @ForeignKey(
                        entity = OrdersEntity.class, // Tên lớp Entity của bảng Orders
                        parentColumns = "order_id",
                        childColumns = "order_id",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = UsersEntity.class, // Tên lớp Entity của bảng Users
                        parentColumns = "user_id",
                        childColumns = "user_id",
                        onDelete = ForeignKey.CASCADE
                )
        }
)
public class ProductReviewEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "review_id")
    private int reviewId;

    @ColumnInfo(name = "user_id")
    private int userId;

    @ColumnInfo(name = "food_id")
    private int foodId;

    @ColumnInfo(name = "order_id")
    private int orderId;

    @ColumnInfo(name = "rating")
    private int rating; // Lưu ý: Ràng buộc CHECK (1 đến 5) bạn nên tự kiểm tra (validate) ở code giao diện trước khi Insert vào Room

    @ColumnInfo(name = "comment")
    private String comment;

    @ColumnInfo(name = "image_url")
    private String imageUrl;

    @ColumnInfo(name = "review_date")
    private String reviewDate; // Trong SQLite, DATETIME thường được xử lý và lưu dưới dạng chuỗi String TEXT hoặc Long (Timestamp)

    // ================= DƯỚI ĐÂY LÀ CONSTRUCTOR =================

    public ProductReviewEntity(int userId, int foodId, int orderId, int rating, String comment, String imageUrl, String reviewDate) {
        this.userId = userId;
        this.foodId = foodId;
        this.orderId = orderId;
        this.rating = rating;
        this.comment = comment;
        this.imageUrl = imageUrl;
        this.reviewDate = reviewDate;
    }

    // ================= DƯỚI ĐÂY LÀ GETTER VÀ SETTER =================
    // (Bắt buộc phải có trong Java để Room có thể đọc/ghi dữ liệu vào thuộc tính private)

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
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

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }
}