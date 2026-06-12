package com.example.nhom33.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.nhom33.DAO.CategoriesDAO;
import com.example.nhom33.DAO.FoodsDAO;
import com.example.nhom33.DataEntity.CartEntity;
import com.example.nhom33.DataEntity.CategoriesEntity;
import com.example.nhom33.DataEntity.CouponsEntity;
import com.example.nhom33.DataEntity.DeliveryLogEntity;
import com.example.nhom33.DataEntity.FavoritesEntity;
import com.example.nhom33.DataEntity.FoodsEntity;
import com.example.nhom33.DataEntity.OrderDetailsEntity;
import com.example.nhom33.DataEntity.OrdersEntity;
import com.example.nhom33.DataEntity.ProductReviewEntity;
import com.example.nhom33.DataEntity.SearchHistoryEntity;
import com.example.nhom33.DataEntity.UsersEntity;

@Database(entities = {
        FoodsEntity.class,
        CategoriesEntity.class,
        UsersEntity.class,
        CartEntity.class,
        CouponsEntity.class,
        DeliveryLogEntity.class,
        FavoritesEntity.class,
        OrderDetailsEntity.class,
        OrdersEntity.class,
        ProductReviewEntity.class,
        SearchHistoryEntity.class
}, version = 4)
public abstract class FoodDB extends RoomDatabase {
    private static final String DB_NAME = "ql_doan33.db";
    private static FoodDB instance;

    public static synchronized FoodDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            FoodDB.class, DB_NAME)
                    .allowMainThreadQueries()
                    //.createFromAsset(DB_NAME) // Load từ assets nếu database chưa tồn tại
                    .fallbackToDestructiveMigration() // Added to handle schema changes during development
                    .build();
        }
        return instance;
    }

    public abstract FoodsDAO foodsDAO();
    public abstract CategoriesDAO categoriesDAO();
}