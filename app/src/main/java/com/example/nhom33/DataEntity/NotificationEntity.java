package com.example.nhom33.DataEntity; // Thay đổi đúng package của bạn

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "Notifications")
public class NotificationEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "notification_id")
    private int notificationId;

    @NonNull
    @ColumnInfo(name = "title")
    private String title;

    @NonNull
    @ColumnInfo(name = "content")
    private String content;

    @ColumnInfo(name = "created_at")
    private String createdAt; // Lưu chuỗi ngày tháng dạng: "12/06/2026 15:30"

    // ==================== CONSTRUCTOR ====================
    public NotificationEntity(@NonNull String title, @NonNull String content, String createdAt) {
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

    // ==================== GETTER VÀ SETTER ====================
    public int getNotificationId() { return notificationId; }
    public void setNotificationId(int notificationId) { this.notificationId = notificationId; }

    @NonNull
    public String getTitle() { return title; }
    public void setTitle(@NonNull String title) { this.title = title; }

    @NonNull
    public String getContent() { return content; }
    public void setContent(@NonNull String content) { this.content = content; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}