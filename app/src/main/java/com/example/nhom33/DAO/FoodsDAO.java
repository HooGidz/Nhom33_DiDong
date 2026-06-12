package com.example.nhom33.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.nhom33.DataEntity.FoodsEntity;

import java.util.List;

@Dao
public interface FoodsDAO {
    @Query("SELECT * FROM Foods")
    List<FoodsEntity> getAllFoods();

    @Insert
    void insertFood(FoodsEntity food);
}
