package com.example.nhom33.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.nhom33.DataEntity.ProductReviewEntity;
import com.example.nhom33.DataEntity.ProductReviewWithDetails;
import java.util.List;

@Dao
public interface ProductReviewDao {

    @Query("SELECT * FROM ProductReview")
    List<ProductReviewEntity> getAllReviews();

    @Insert
    void insertReview(ProductReviewEntity review);

    @Query("SELECT COUNT(*) FROM ProductReview WHERE order_id = :orderId")
    int countReviewsByOrderId(int orderId);

    @Query("SELECT pr.*, u.full_name as fullName, f.food_name as foodName " +
           "FROM ProductReview pr " +
           "LEFT JOIN Users u ON pr.user_id = u.user_id " +
           "LEFT JOIN Foods f ON pr.food_id = f.food_id")
    List<ProductReviewWithDetails> getAllReviewsWithDetails();

    @Query("SELECT pr.*, u.full_name as fullName, f.food_name as foodName " +
           "FROM ProductReview pr " +
           "LEFT JOIN Users u ON pr.user_id = u.user_id " +
           "LEFT JOIN Foods f ON pr.food_id = f.food_id " +
           "WHERE pr.food_id = :foodId")
    List<ProductReviewWithDetails> getReviewsByFoodId(int foodId);
}