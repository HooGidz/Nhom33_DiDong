package com.example.nhom33.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.nhom33.DataEntity.FoodsEntity;

import java.util.List;

@Dao
public interface FoodsDAO {

    @Query("SELECT * FROM Foods")
    List<FoodsEntity> getAllFoods();

    @Query("SELECT * FROM Foods WHERE food_id = :id")
    FoodsEntity getFoodById(int id);

    @Query("SELECT * FROM Foods WHERE category_id = :categoryId")
    List<FoodsEntity> getFoodsByCategoryId(int categoryId);

    @Query("SELECT * FROM Foods WHERE price_sale IS NOT NULL AND price_sale > 0")
    List<FoodsEntity> getDiscountedFoods();

    @Insert
    void insertFood(FoodsEntity food);

    @Update
    void updateFood(FoodsEntity food);

    @Delete
    void deleteFood(FoodsEntity food);

    @Query("DELETE FROM Foods WHERE food_id = :id")
    void deleteFoodById(int id);
}