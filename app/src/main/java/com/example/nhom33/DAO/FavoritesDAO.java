package com.example.nhom33.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.nhom33.DataEntity.FavoritesEntity;

@Dao
public interface FavoritesDAO {
    @Insert
    void insertFavorite(FavoritesEntity favorite);

    @Delete
    void deleteFavorite(FavoritesEntity favorite);

    @Query("SELECT * FROM Favorites WHERE user_id = :userId AND food_id = :foodId LIMIT 1")
    FavoritesEntity getFavorite(int userId, int foodId);

    @Query("DELETE FROM Favorites WHERE user_id = :userId AND food_id = :foodId")
    void deleteFavorite(int userId, int foodId);
}
