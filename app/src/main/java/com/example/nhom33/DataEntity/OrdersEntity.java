package com.example.nhom33.DataEntity; // Thay đổi package này cho đúng với cấu trúc thư mục của bạn

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "Orders",
        foreignKeys = {
                @ForeignKey(
                        entity = UsersEntity.class, // Tên lớp Entity của bảng Users
                        parentColumns = "user_id",
                        childColumns = "customer_id",
                        onDelete = ForeignKey.CASCADE // Hoặc NO_ACTION tùy theo logic của bạn
                )
        }
)
public class OrdersEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "order_id")
    private int orderId;

    @ColumnInfo(name = "customer_id")
    private int customerId;

    // Trong Room, DATETIME thường được lưu dưới dạng String hoặc Long (Timestamp).
    // Mặc định ban đầu bạn có thể để String để đồng bộ với SQLite.
    @ColumnInfo(name = "order_date", defaultValue = "CURRENT_TIMESTAMP")
    private String orderDate;

    @ColumnInfo(name = "total_amount")
    private double totalAmount; // REAL trong SQLite tương ứng với double/float trong Java

    @ColumnInfo(name = "status")
    private String status; // Giá trị sẽ gồm: Pending, Confirmed, Delivering, Completed, Cancelled

    @ColumnInfo(name = "delivery_address")
    private String deliveryAddress;

    // --- Constructor ---
    public OrdersEntity(int customerId, String orderDate, double totalAmount, String status, String deliveryAddress) {
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.deliveryAddress = deliveryAddress;
    }

    // --- Getter và Setter (Bắt buộc phải có để Room hoạt động trong Java) ---
    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public String getOrderDate() { return orderDate; }
    public void setOrderDate(String orderDate) { this.orderDate = orderDate; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDeliveryAddress() { return deliveryAddress; }
    public void setDeliveryAddress(String deliveryAddress) { this.deliveryAddress = deliveryAddress; }
}