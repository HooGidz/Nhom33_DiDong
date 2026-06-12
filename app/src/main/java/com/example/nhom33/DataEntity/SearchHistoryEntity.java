package com.example.nhom33.DataEntity; // Bạn nhớ sửa lại package cho đúng với thư mục dự án của bạn

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

// Định nghĩa Entity, tên bảng và ràng buộc Khóa ngoại (Foreign Key) nối với bảng Users
@Entity(
        tableName = "SearchHistory",
        foreignKeys = @ForeignKey(
                entity = UsersEntity.class, // Tên lớp Entity của bảng Users (Ví dụ: UsersEntity hoặc User)
                parentColumns = "user_id",
                childColumns = "user_id",
                onDelete = ForeignKey.CASCADE // Khi tài khoản user bị xóa, lịch sử tìm kiếm của user đó cũng tự động xóa theo
        )
)
public class SearchHistoryEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "search_id")
    private int searchId;

    @ColumnInfo(name = "user_id")
    private int userId;

    @ColumnInfo(name = "search_query")
    private String searchQuery;

    @ColumnInfo(name = "search_time")
    private String searchTime; // DATETIME trong SQLite được Room ánh xạ thành String để lưu trữ chuỗi ngày tháng dễ dàng

    // ================= CONSTRUCTOR =================

    public SearchHistoryEntity(int userId, String searchQuery, String searchTime) {
        this.userId = userId;
        this.searchQuery = searchQuery;
        this.searchTime = searchTime;
    }

    // ================= GETTER VÀ SETTER =================
    // (Bắt buộc phải có đầy đủ để Room Compiler Java có thể đọc/ghi dữ liệu vào các thuộc tính private)

    public int getSearchId() {
        return searchId;
    }

    public void setSearchId(int searchId) {
        this.searchId = searchId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public String getSearchTime() {
        return searchTime;
    }

    public void setSearchTime(String searchTime) {
        this.searchTime = searchTime;
    }
}