package com.example.nhom33.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.nhom33.DataEntity.CartEntity;

import java.util.List;

@Dao
public interface CartDAO {
    @Insert
    void insert(CartEntity cart);

    @Update
    void update(CartEntity cart);

    @Delete
    void delete(CartEntity cart);

    @Query("SELECT * FROM Cart WHERE user_id = :userId")
    List<CartEntity> getCartByUser(int userId);

    @Query("SELECT * FROM Cart WHERE user_id = :userId AND food_id = :foodId LIMIT 1")
    CartEntity getCartItem(int userId, int foodId);

    @Query("DELETE FROM Cart WHERE user_id = :userId")
    void clearCart(int userId);

    @Query("DELETE FROM Cart WHERE user_id = :userId AND food_id = :foodId")
    void deleteByFoodId(int userId, int foodId);
}
