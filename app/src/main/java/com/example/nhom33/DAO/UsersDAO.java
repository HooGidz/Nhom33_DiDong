package com.example.nhom33.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.nhom33.DataEntity.UsersEntity;

import java.util.List;

@Dao
public interface UsersDAO {
    @Query("SELECT * FROM Users WHERE user_id = :userId LIMIT 1")
    UsersEntity getUserById(int userId);

    @Query("SELECT * FROM Users WHERE email = :email AND password = :password LIMIT 1")
    UsersEntity getUserByEmailAndPassword(String email, String password);

    @Query("SELECT * FROM Users")
    List<UsersEntity> getAllUsers();

    @Query("SELECT * FROM Users WHERE role = :role")
    List<UsersEntity> getUsersByRole(String role);

    @Insert
    void insertUser(UsersEntity user);

    @Update
    void updateUser(UsersEntity user);

    @Delete
    void deleteUser(UsersEntity user);
}
