package com.example.nhom33.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import android.util.Log;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.nhom33.DAO.CategoriesDAO;
import com.example.nhom33.DAO.FavoritesDAO;
import com.example.nhom33.DAO.FoodsDAO;
import com.example.nhom33.DAO.NotificationDAO;
import com.example.nhom33.DAO.OrderDetailsDAO;
import com.example.nhom33.DAO.OrdersDAO;
import com.example.nhom33.DAO.ProductReviewDao;
import com.example.nhom33.DAO.UsersDAO;
import com.example.nhom33.DataEntity.CartEntity;
import com.example.nhom33.DataEntity.CategoriesEntity;
import com.example.nhom33.DataEntity.CouponsEntity;
import com.example.nhom33.DataEntity.DeliveryLogEntity;
import com.example.nhom33.DataEntity.FavoritesEntity;
import com.example.nhom33.DataEntity.FoodsEntity;
import com.example.nhom33.DataEntity.NotificationEntity;
import com.example.nhom33.DataEntity.OrderDetailsEntity;
import com.example.nhom33.DataEntity.OrdersEntity;
import com.example.nhom33.DataEntity.ProductReviewEntity;
import com.example.nhom33.DataEntity.SearchHistoryEntity;
import com.example.nhom33.DataEntity.UsersEntity;

import java.util.concurrent.Executors;

@Database(entities = {
        FoodsEntity.class,
        CategoriesEntity.class,
        UsersEntity.class,
        CartEntity.class,
        CouponsEntity.class,
        DeliveryLogEntity.class,
        FavoritesEntity.class,
        OrderDetailsEntity.class,
        OrdersEntity.class,
        ProductReviewEntity.class,
        NotificationEntity.class,
        SearchHistoryEntity.class
}, version = 11) // Nâng version lên 11 để database cập nhật lại dữ liệu mẫu
public abstract class FoodDB extends RoomDatabase {
    private static final String DB_NAME = "ql_doan33.db";
    private static FoodDB instance;

    public static synchronized FoodDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            FoodDB.class, DB_NAME)
                    .allowMainThreadQueries()
                    .addCallback(roomCallback)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract FoodsDAO foodsDAO();
    public abstract CategoriesDAO categoriesDAO();
    public abstract NotificationDAO notificationDAO();
    public abstract OrdersDAO ordersDAO();
    public abstract OrderDetailsDAO orderDetailsDAO();
    public abstract UsersDAO usersDAO();
    public abstract ProductReviewDao productReviewDAO();
    public abstract FavoritesDAO favoritesDAO();

    private static final RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            Executors.newSingleThreadExecutor().execute(() -> {
                try {
                    // 1. Chèn người dùng
                    db.execSQL("INSERT INTO Users (username, password, full_name, email, phone, address, role) VALUES " +
                            "('hoanggiap', '123456', 'Hoàng Giáp', 'giap@gmail.com', '0987654321', 'Vinh, Nghệ An', 'customer'), " +
                            "('giang', '123', 'Giang Nguyễn', 'giang@gmail.com', '0123456789', 'Hà Nội', 'customer'), " +
                            "('thangle', '123', 'Thắng Lê', 'tl@gmail.com', '0123456789', 'Hà Nội', 'customer'), " +
                            "('admin', '123', 'Admin Hệ Thống', 'admin@nhom33.com', '0123456789', 'Hà Nội', 'merchant');");

                    // 2. Chèn SearchHistory
                    db.execSQL("INSERT INTO SearchHistory (user_id, search_query, search_time) VALUES " +
                            "(1, 'Pizza', datetime('now')), " +
                            "(1, 'Trà sữa Phúc Long', datetime('now')), " +
                            "(2, 'Gà rán KFC', datetime('now')), " +
                            "(1, 'Trà sữa', datetime('now'));");
                    // 3. DeliveryLog
                    db.execSQL("INSERT INTO DeliveryLog (order_id, status_description, log_time) VALUES " +
                            "(1, 'Đã nhận đơn hàng', datetime('now', '-2 hours')), " +
                            "(1, 'Shipper đã lấy hàng', datetime('now', '-1 hour')), " +
                            "(1, 'Giao hàng thành công', datetime('now', '-30 minutes')), " +
                            "(2, 'Đã nhận đơn hàng', datetime('now', '-1 hour')), " +
                            "(2, 'Đang chuẩn bị món ăn', datetime('now', '-45 minutes')), " +
                            "(2, 'Đang giao hàng', datetime('now', '-10 minutes')), " +
                            "(5, 'Đã nhận đơn hàng', datetime('now', '-20 minutes'));");
                    // 4. Notification (bây giờ đổi sang bảng Notifications)
                    db.execSQL("INSERT INTO Notifications (title, content, created_at) VALUES " +
                            "('Khuyến mãi sốc', 'Giảm giá 50% toàn bộ thực đơn ngày hôm nay!', '13/06/2026'), " +
                            "('Hệ thống bảo trì', 'Ứng dụng sẽ bảo trì hệ thống nạp tiền vào lúc 23h00.', '13/06/2026');");
                    // 5. Chèn Categories
                    db.execSQL("INSERT INTO Categories (category_name, description, image_url) VALUES " +
                            "('Đồ ăn nhanh', 'Các món chế biến nhanh, tiện lợi', 'fastfood.png'), " +
                            "('Đồ uống', 'Nước giải khát, trà sữa, cà phê', 'drinks.png'), " +
                            "('Món Việt', 'Ẩm thực truyền thống Việt Nam', 'vietnamese.png'), " +
                            "('Tráng miệng', 'Bánh ngọt và trái cây', 'dessert.png');");
                    // 6. Chèn Foods
                    db.execSQL("INSERT INTO Foods (category_id, food_name, description, price, price_sale, image_url, is_available, meal_type) VALUES " +
                            "(1, 'Pizza Hải Sản', 'Pizza với tôm, mực và phô mai', 159000.0, 140000.0, 'pizza_hs.png', 1, 'Cả ngày')," +
                            "(2, 'Trà Sữa Phúc Long', 'Đậm vị trà, béo vị sữa', 55000.0, NULL, 'ts_phuclong.png', 1, 'Cả ngày')," +
                            "(1, 'Combo Gà Rán', '2 miếng gà + 1 khoai tây + 1 Pepsi', 89000.0, 79000.0, 'kfc_combo.png', 1, 'Cả ngày')," +
                            "(2, 'Cà Phê Sữa Đá', 'Hạt cà phê nguyên chất', 35000.0, NULL, 'cf_suada.png', 1, 'Sáng')," +
                            "(1, 'Burger Bò', 'Burger bò mỹ với các loại thịt chất lượng cao', 95000.0, 85000.0, 'burger_bo.png', 1, 'Cả ngày');");

                    // 7. Chèn Coupons
                    db.execSQL("INSERT INTO Coupons (coupon_code, discount_percent, discount_amount, min_order_value, expiry_date, is_active) VALUES " +
                            "('GIAP2026', 20, 0.0, 100000.0, '2026-12-31', 1), " +
                            "('FREESHIP', 0, 30000.0, 50000.0, '2026-06-01', 1);");

                    // 8. Chèn Orders
                    db.execSQL("INSERT INTO Orders (customer_id, order_date, total_amount, status, delivery_address) VALUES " +
                            "(1, datetime('now'), 159000, 'Hoàn thành', 'Vinh, Nghệ An'), " +
                            "(2, datetime('now'), 55000, 'Đang giao hàng', 'Hà Nội'), " +
                            "(3, datetime('now'), 89000, 'Chờ xác nhận', 'Đà Nẵng'), " +
                            "(1, datetime('now'), 35000, 'Đã huỷ', 'Vinh, Nghệ An'), " +
                            "(1, datetime('now'), 55000, 'Đang giao hàng', 'Hà Nội');");

                    // 9. Chèn OrderDetails
                    db.execSQL("INSERT INTO OrderDetails (order_id, food_id, quantity, price_at_time) VALUES " +
                            "(1, 1, 1, 159000), " +
                            "(2, 2, 1, 55000), " +
                            "(3, 3, 1, 89000), " +
                            "(4, 4, 1, 35000), " +
                            "(5, 2, 1, 55000);"); // Đã bổ sung chi tiết cho đơn hàng ID 5

                    // 10. Chèn ProductReview
                    db.execSQL("INSERT INTO ProductReview (user_id, food_id, order_id, rating, comment, image_url, review_date) VALUES " +
                            "(1, 1, 1, 5, 'Pizza rất ngon, giao hàng nhanh!', 'review_pizza.jpg', datetime('now')), " +
                            "(2, 2, 2, 4, 'Trà sữa thơm nhưng hơi ngọt quá.', NULL, datetime('now')), " +
                            "(1, 5, 5, 5, 'Burger bò rất chất lượng, sẽ ủng hộ tiếp.', 'review_burger.jpg', datetime('now')), " +
                            "(3, 3, 3, 3, 'Gà hơi nguội khi giao đến.', NULL, datetime('now')), " +
                            "(1, 1, 1, 5, 'Rất ngon!', 'review1.jpg', datetime('now'));");


                    // 11. Favorites
                    db.execSQL("INSERT INTO Favorites (user_id, food_id) VALUES " +
                            "(1, 1), " +
                            "(1, 2), " +
                            "(2, 3), " +
                            "(3, 4), " +
                            "(1, 5);");

                } catch (Exception e) {
                    Log.e("FoodDB", "Error inserting initial data", e);
                }
            });

        }
    };
}
