package com.example.nhom33.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.nhom33.DataEntity.OrdersEntity;

import java.util.List;

@Dao
public interface OrdersDAO {
    @Query("SELECT * FROM Orders ORDER BY order_id DESC")
    List<OrdersEntity> getAllOrders();

    @Query("SELECT * FROM Orders WHERE status = :status ORDER BY order_id DESC")
    List<OrdersEntity> getOrdersByStatus(String status);

    @Query("SELECT * FROM Orders WHERE order_id = :orderId LIMIT 1")
    OrdersEntity getOrderById(int orderId);

    @Insert
    void insertOrder(OrdersEntity order);

    @Update
    void updateOrder(OrdersEntity order);
}
