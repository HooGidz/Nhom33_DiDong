package com.example.nhom33.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.nhom33.DataEntity.CategoriesEntity;

import java.util.List;

@Dao
public interface CategoriesDAO {
    @Query("SELECT * FROM Categories")
    List<CategoriesEntity> getAllCategories();

    @Query("SELECT * FROM Categories WHERE category_id = :id")
    CategoriesEntity getCategoryById(int id);

    @Insert
    void insertCategory(CategoriesEntity category);
}
