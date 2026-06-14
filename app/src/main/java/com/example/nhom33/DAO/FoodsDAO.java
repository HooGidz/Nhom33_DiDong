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

    @Insert
    void insertFood(FoodsEntity food);

    @Update
    void updateFood(FoodsEntity food);

    @Delete
    void deleteFood(FoodsEntity food);

    // Thêm hàm xóa theo ID để đảm bảo xóa chính xác và an toàn hơn
    @Query("DELETE FROM Foods WHERE food_id = :id")
    void deleteFoodById(int id);
}