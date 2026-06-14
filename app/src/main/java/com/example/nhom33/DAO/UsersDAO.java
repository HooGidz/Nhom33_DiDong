package com.example.nhom33.DAO;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.nhom33.DataEntity.UsersEntity;

@Dao
public interface UsersDAO {
    @Query("SELECT * FROM Users WHERE user_id = :userId LIMIT 1")
    UsersEntity getUserById(int userId);
}
