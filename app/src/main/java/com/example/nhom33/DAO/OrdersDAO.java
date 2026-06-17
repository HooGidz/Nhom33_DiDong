package com.example.nhom33.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
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
    List<OrdersEntity> getOrdersByStatus(int status);

    @Query("SELECT * FROM Orders WHERE user_id = :userId ORDER BY order_id DESC")
    List<OrdersEntity> getOrdersByUserId(int userId);

    @Query("SELECT * FROM Orders WHERE user_id = :userId AND status IN (0, 1) ORDER BY order_id DESC")
    List<OrdersEntity> getOngoingOrdersByUserId(int userId);

    @Query("SELECT * FROM Orders WHERE user_id = :userId AND status IN (2, 3) ORDER BY order_id DESC")
    List<OrdersEntity> getHistoryOrdersByUserId(int userId);

    @Query("SELECT * FROM Orders WHERE order_id = :orderId LIMIT 1")
    OrdersEntity getOrderById(int orderId);

    @Insert
    long insertOrder(OrdersEntity order);

    @Update
    void updateOrder(OrdersEntity order);

    @Delete
    void deleteOrder(OrdersEntity order);
    
    @Query("DELETE FROM Orders WHERE order_id = :orderId")
    void deleteOrderById(int orderId);
}
