package com.example.nhom33.DAO; // Thay đổi đúng package của bạn

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.nhom33.DataEntity.NotificationEntity;
import java.util.List;

@Dao
public interface NotificationDAO {

    @Insert
    void insertNotification(NotificationEntity notification);

    @Query("SELECT * FROM Notifications ORDER BY notification_id DESC")
    List<NotificationEntity> getAllNotifications();
}