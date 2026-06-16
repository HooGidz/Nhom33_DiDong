package com.example.nhom33.DataEntity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "Orders",
        foreignKeys = {
                @ForeignKey(
                        entity = UsersEntity.class,
                        parentColumns = "user_id",
                        childColumns = "user_id",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = CouponsEntity.class,
                        parentColumns = "coupon_id",
                        childColumns = "coupon_id",
                        onDelete = ForeignKey.CASCADE
                )
        }
)
public class OrdersEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "order_id")
    private int orderId;

    @ColumnInfo(name = "user_id")
    private int userId;

    @ColumnInfo(name = "coupon_id")
    private int couponId; // Thiết kế ghi int - Not Null

    @ColumnInfo(name = "customer_name")
    private String customerName;

    @ColumnInfo(name = "customer_phone")
    private String customerPhone;

    @ColumnInfo(name = "custome_address") // Giữ nguyên typo "custome" theo thiết kế bảng 2.8
    private String customerAddress;

    @ColumnInfo(name = "order_date")
    private String orderDate;

    @ColumnInfo(name = "total_amount")
    private double totalAmount;

    @ColumnInfo(name = "delivery_address")
    private String deliveryAddress;

    @ColumnInfo(name = "status")
    private int status;

    @ColumnInfo(name = "method_payment")
    private int methodPayment;

    @ColumnInfo(name = "note")
    private String note;

    // --- Constructor mặc định cho Room ---
    public OrdersEntity() {
    }

    // --- Constructor đầy đủ ---
    public OrdersEntity(int userId, int couponId, String customerName, String customerPhone, 
                        String customerAddress, String orderDate, double totalAmount, 
                        String deliveryAddress, int status, int methodPayment, String note) {
        this.userId = userId;
        this.couponId = couponId;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.customerAddress = customerAddress;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.deliveryAddress = deliveryAddress;
        this.status = status;
        this.methodPayment = methodPayment;
        this.note = note;
    }

    // --- Getter và Setter ---
    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getCouponId() { return couponId; }
    public void setCouponId(int couponId) { this.couponId = couponId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getCustomerPhone() { return customerPhone; }
    public void setCustomerPhone(String customerPhone) { this.customerPhone = customerPhone; }

    public String getCustomerAddress() { return customerAddress; }
    public void setCustomerAddress(String customerAddress) { this.customerAddress = customerAddress; }

    public String getOrderDate() { return orderDate; }
    public void setOrderDate(String orderDate) { this.orderDate = orderDate; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public String getDeliveryAddress() { return deliveryAddress; }
    public void setDeliveryAddress(String deliveryAddress) { this.deliveryAddress = deliveryAddress; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public int getMethodPayment() { return methodPayment; }
    public void setMethodPayment(int methodPayment) { this.methodPayment = methodPayment; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}
