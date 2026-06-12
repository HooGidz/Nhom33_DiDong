package com.example.nhom33.DataEntity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

// Định nghĩa Entity, tên bảng và thiết lập Khóa ngoại kết nối với bảng Orders
@Entity(
        tableName = "DeliveryLog",
        foreignKeys = @ForeignKey(
                entity = OrdersEntity.class,         // Lớp Entity của bảng Orders
                parentColumns = "order_id",         // Tên cột khóa chính ở bảng Orders
                childColumns = "order_id",          // Tên cột khóa ngoại ở bảng DeliveryLog
                onDelete = ForeignKey.CASCADE       // Tự động xóa log nếu đơn hàng bị xóa (tùy chọn)
        )
)
public class DeliveryLogEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "log_id")
    private int logId;

    @ColumnInfo(name = "order_id")
    private int orderId;

    @ColumnInfo(name = "status_description")
    private String statusDescription;

    // Trong Room, DATETIME của SQLite nên được map về String hoặc Long (Timestamp)
    // Ở đây dùng String để hứng giá trị chuỗi thời gian mặc định từ CURRENT_TIMESTAMP
    @ColumnInfo(name = "log_time", defaultValue = "CURRENT_TIMESTAMP")
    private String logTime;

    // --- Constructor ---
    public DeliveryLogEntity(int orderId, String statusDescription, String logTime) {
        this.orderId = orderId;
        this.statusDescription = statusDescription;
        this.logTime = logTime;
    }

    // --- Getter và Setter (Bắt buộc phải có trong Java để Room có thể đọc/ghi dữ liệu) ---
    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public String getLogTime() {
        return logTime;
    }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }
}