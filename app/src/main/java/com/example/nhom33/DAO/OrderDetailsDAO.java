package com.example.nhom33.DAO;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.nhom33.DataEntity.OrderDetailsEntity;

import java.util.List;

@Dao
public interface OrderDetailsDAO {
    @Query("SELECT od.*, f.food_name FROM OrderDetails od " +
           "JOIN Foods f ON od.food_id = f.food_id " +
           "WHERE od.order_id = :orderId")
    List<OrderDetailWithFood> getDetailsWithFoodByOrderId(int orderId);

    class OrderDetailWithFood {
        public int detail_id;
        public int order_id;
        public int food_id;
        public int quantity;
        public double price_at_time;
        public String food_name;
    }
}
