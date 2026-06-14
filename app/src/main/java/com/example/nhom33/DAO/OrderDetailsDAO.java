package com.example.nhom33.DAO;

import androidx.room.Dao;
import androidx.room.Query;
import java.util.List;

@Dao
public interface OrderDetailsDAO {
    @Query("SELECT od.order_id, f.food_name, c.category_name, od.quantity, od.price_at_time " +
           "FROM OrderDetails od " +
           "JOIN Foods f ON od.food_id = f.food_id " +
           "JOIN Categories c ON f.category_id = c.category_id " +
           "WHERE od.order_id = :orderId")
    List<OrderDetailWithFood> getDetailsWithFoodByOrderId(int orderId);

    class OrderDetailWithFood {
        public int order_id;
        public String food_name;
        public String category_name;
        public int quantity;
        public double price_at_time;
    }
}
