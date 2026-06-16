package com.example.nhom33.DataEntity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "OrderDetails",
        foreignKeys = {
                @ForeignKey(
                        entity = OrdersEntity.class,
                        parentColumns = "order_id",
                        childColumns = "order_id",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = FoodsEntity.class,
                        parentColumns = "food_id",
                        childColumns = "food_id",
                        onDelete = ForeignKey.RESTRICT
                )
        }
)
public class OrderDetailsEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "detail_id")
    private int detailId;

    @ColumnInfo(name = "order_id")
    private int orderId;

    @ColumnInfo(name = "food_id")
    private int foodId;

    @ColumnInfo(name = "quantity")
    private int quantity;

    @ColumnInfo(name = "price_at_time")
    private double priceAtTime;

    // Hàm khởi tạo không tham số cho Room
    public OrderDetailsEntity() {
    }

    // Hàm khởi tạo đầy đủ tham số
    public OrderDetailsEntity(int orderId, int foodId, int quantity, double priceAtTime) {
        this.orderId = orderId;
        this.foodId = foodId;
        this.quantity = quantity;
        this.priceAtTime = priceAtTime;
    }

    // --- Getter và Setter ---
    public int getDetailId() { return detailId; }
    public void setDetailId(int detailId) { this.detailId = detailId; }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public int getFoodId() { return foodId; }
    public void setFoodId(int foodId) { this.foodId = foodId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPriceAtTime() { return priceAtTime; }
    public void setPriceAtTime(double priceAtTime) { this.priceAtTime = priceAtTime; }
}
