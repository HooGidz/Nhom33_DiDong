package com.example.nhom33.Database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.nhom33.DAO.CartDAO;
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
}, version = 13) // Nâng lên version 12 để làm mới cấu trúc bảng
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
    public abstract CartDAO cartDAO();
    public abstract FavoritesDAO favoritesDAO();

    private static final RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            Executors.newSingleThreadExecutor().execute(() -> {
                try {
                    // 1. Chèn Users (role: 0-Admin, 1-User | status: 0-Inactive, 1-Active)
                    db.execSQL("INSERT INTO Users (username, password, full_name, email, phone, address, role, avatar, status, created_at) VALUES " +
                            "('admin', '123', 'Admin Hệ Thống', 'admin@nhom33.com', '0123456789', 'Hà Nội', 0, 'a1.jpg', 1, '2024-01-01'), " +
                            "('hoanggiap', '123', 'Hoàng Giáp', 'giap@gmail.com', '0987654321', 'Vinh, Nghệ An', 1, 'a2.jpg', 1, '2024-02-01'), " +
                            "('giang', '123', 'Giang Nguyễn', 'giang@gmail.com', '0111222333', 'Hà Nội', 1, 'a3.jpg', 1, '2024-02-10'), " +
                            "('thangle', '123', 'Thắng Lê', 'tl@gmail.com', '0444555666', 'Hồ Chí Minh', 1, 'a4.jpg', 1, '2024-03-01'), " +
                            "('user5', '123', 'Người Dùng 5', 'user5@gmail.com', '0999888777', 'Đà Nẵng', 1, 'a15.jpg', 1, '2024-03-05');");

                    // 2. Chèn Categories
                    db.execSQL("INSERT INTO Categories (category_name, description, image_url) VALUES " +
                            "('Combo bán chạy', 'Các phần ăn combo được yêu thích nhất', 'combo_ban_chay.png'), " +
                            "('Gà giòn vui vẻ', 'Gà rán giòn rụm thơm ngon', 'ga_gion_vui_ve.png'), " +
                            "('Mì ý jolly', 'Mì Ý sốt bò băm đậm đà', 'mi_y_jolly.png'), " +
                            "('Gà sốt cay', 'Gà rán phủ sốt gia vị cay nồng', 'ga_sot_cay.png'), " +
                            "('Burger/cơm', 'Các loại bánh burger và cơm phần', 'burger_com.png'), " +
                            "('Phần ăn phụ', 'Khoai tây chiên và các món ăn kèm', 'phan_an_phu.png'), " +
                            "('Món tráng miệng', 'Kem tươi và các món ngọt tráng miệng', 'mon_trang_mieng.png'), " +
                            "('Thức uống', 'Nước giải khát và trà trái cây thanh mát', 'thuc_uong.png');");

                    // 3. Chèn Foods (is_new, is_best, is_available: 0-No, 1-Yes)
                    db.execSQL("INSERT INTO Foods (category_id, food_name, size, description, price, price_sale, image_url, is_new, is_best, is_available) VALUES " +
                            // CATEGORY 1: Combo bán chạy (category_id = 1)
                            "(1, 'Combo gà rán mì ý', 'Phần', '1 Gà rán + 1 Mì Ý jolly lớn + 1 Nước ngọt', 95000.0, 85000.0, 'combo_ga_mi.png', 1, 1, 1)," +
                            "(1, 'Combo burger khoai tây', 'Phần', '1 Burger bò + 1 Khoai tây chiên vừa + 1 Nước ngọt', 75000.0, NULL, 'burger.png', 0, 1, 1)," +
                            "(1, 'Combo gia đình vui vẻ', 'Phần lớn', '4 Gà rán + 2 Mì Ý + 1 Khoai tây chiên lớn + 3 Nước ngọt', 289000.0, 259000.0, 'combo_family.png', 0, 1, 1)," +

                            // CATEGORY 2: Gà giòn vui vẻ (category_id = 2)
                            "(2, 'Gà rán truyền thống', '1 Miếng', 'Gà rán giòn rụm, thịt bên trong mềm ngọt', 36000.0, NULL, 'ga_truyen_thong.png', 0, 1, 1)," +
                            "(2, 'Cánh gà rán giòn', ' Cánh 1 miếng', '1 miếng cánh gà rán truyền thống tiết kiệm', 139000.0, 129000.0, 'canh_ga_ran.png', 0, 0, 1)," +
                            "(2, 'Gà rán tender', '3 Miếng', 'Gà phi lê chiên xù không xương tiện lợi', 42000.0, 39000.0, 'ga_tender.png', 1, 0, 1)," +

                            // CATEGORY 3: Mì ý jolly (category_id = 3)
                            "(3, 'Mì ý sốt bò băm', 'Đĩa vừa', 'Mì Ý truyền thống kết hợp sốt bò băm đậm đà', 40000.0, NULL, 'mi_y_bo_bam.png', 0, 1, 1)," +
                            "(3, 'Mì ý gà giòn', 'Đĩa lớn', 'Mì Ý sốt bò băm kèm theo 1 miếng gà rán giòn', 72000.0, 65000.0, 'mi_y_ga_gion.png', 0, 1, 1)," +
                            "(3, 'Mì ý sốt cá hồi', 'Đĩa vừa', 'Mì Ý cải tiến với sốt kem cá hồi béo ngậy', 55000.0, 49000.0, 'mi_y_ca_hoi.png', 1, 0, 1)," +

                            // CATEGORY 4: Gà sốt cay (category_id = 4)
                            "(4, 'Gà sốt cay đậm đà', '1 Miếng', 'Gà rán giòn phủ sốt cay ngọt kiểu Hàn Quốc', 38000.0, NULL, 'ga_cay.jpg', 0, 1, 1)," +
                            "(4, 'Gà sốt mật ong mù tạt', '1 Miếng', 'Gà rán phủ sốt mật ong mù tạt thơm lừng', 38000.0, 35000.0, 'ga_mat_ong.jpg', 1, 0, 1)," +
                            "(4, 'Xô gà sốt cay', 'Xô 4 miếng', 'Xô 4 miếng gà sốt cay nồng nàn', 145000.0, 135000.0, 'xo_ga_cay.jpg', 0, 1, 1)," +

                            // CATEGORY 5: Burger/cơm (category_id = 5)
                            "(5, 'Burger bò nướng', 'Cái', 'Bánh mì kẹp thịt bò nướng kèm sốt đặc biệt', 35000.0, NULL, 'burger_bo.jpg', 0, 1, 1)," +
                            "(5, 'Cơm gà rán giòn', 'Dĩa', 'Cơm trắng thơm dẻo ăn kèm 1 miếng gà rán giòn', 45000.0, 42000.0, 'com_ga_ran.png', 0, 1, 1)," +
                            "(5, 'Cơm thịt bò xào', 'Dĩa', 'Cơm trắng sốt thịt bò xào hành tây đậm vị', 45000.0, NULL, 'com_bo_xao.jpg', 1, 0, 1)," +

                            // CATEGORY 6: Phần ăn phụ (category_id = 6)
                            "(6, 'Khoai tây chiên', 'Hộp vừa', 'Khoai tây cắt thanh chiên vàng giòn rụm', 22000.0, 19000.0, 'khoai_tay_chien.jpg', 0, 1, 1)," +
                            "(6, 'Khoai tây lắc phô mai', 'Hộp vừa', 'Khoai tây chiên giòn lắc bột phô mai mặn ngọt', 29000.0, NULL, 'tuongca.jpg', 1, 1, 1)," +
                            "(6, 'Gà popcorn', 'Hộp', 'Thịt viên gà chiên giòn nhỏ xinh dễ ăn', 30000.0, NULL, 'sup_bi_do.jpg', 0, 0, 1)," +

                            // CATEGORY 7: Món tráng miệng (category_id = 7)
                            "(7, 'Kem hình nón', 'Cây', 'Kem cây vị vani thơm ngon mềm mịn', 8000.0, NULL, 'kemvani.jpg', 0, 1, 1)," +
                            "(7, 'Bánh pie khoai môn', 'Cái', 'Bánh pie chiên giòn rụm nhân khoai môn nóng hổi', 18000.0, 15000.0, 'kem_socola.jpg', 0, 0, 1)," +
                            "(7, 'Kem sundae dâu', 'Ly', 'Kem ly vani phủ sốt mứt dâu tây ngọt ngào', 18000.0, NULL, 'kem_dau.jpg', 0, 1, 1)," +

                            // CATEGORY 8: Thức uống (category_id = 8)
                            "(8, 'Pepsi tươi', 'Ly vừa', 'Nước ngọt có ga giải nhiệt cực đã', 15000.0, NULL, 'pepsi.png', 0, 1, 1)," +
                            "(8, 'Trà chanh', 'Ly lớn', 'Trà chanh thanh mát kết hợp hạt chia bổ dưỡng', 25000.0, 22000.0, 'tra_chanh.png', 1, 1, 1)," +
                            "(8, 'Nước cam ép', 'Ly', 'Nước cam nguyên chất giàu vitamin C', 22000.0, NULL, 'nuoc_cam.png', 0, 0, 1);");

                    // 4. Chèn Coupons
                    db.execSQL("INSERT INTO Coupons (coupon_code, discount_percent, discount_amount, min_order_value, expiry_date, is_active) VALUES " +
                            "('GIAP2026', 20, 0.0, 100000.0, '2026-12-31', 1), " +
                            "('FREESHIP', 0, 30000.0, 50000.0, '2026-06-01', 1), " +
                            "('NEWUSER', 10, 0, 0, '2024-12-31', 1), " +
                            "('SALE50K', 0, 50000, 200000, '2024-05-01', 1), " +
                            "('SUMMER', 15, 0, 150000, '2024-08-31', 0);");

                    // 5. Chèn Orders (status: 0-Pending, 1-Confirmed, 2-Delivering, 3-Completed, 4-Cancelled)
                    db.execSQL("INSERT INTO Orders (user_id, coupon_id, customer_name, customer_phone, custome_address, order_date, total_amount, delivery_address, status, method_payment, note) VALUES " +
                            "(2, 1, 'Hoàng Giáp', '0987654321', 'Vinh, Nghệ An', datetime('now'), 159000, 'Vinh, Nghệ An', 3, 0, 'Giao trước 12h'), " +
                            "(3, 2, 'Giang Nguyễn', '0111222333', 'Hà Nội', datetime('now'), 55000, 'Hà Nội', 2, 1, ''), " +
                            "(4, 1, 'Thắng Lê', '0444555666', 'Hồ Chí Minh', datetime('now'), 95000, 'Hồ Chí Minh', 1, 0, 'Gọi trước khi giao'), " +
                            "(5, 4, 'Người Dùng 5', '0999888777', 'Đà Nẵng', datetime('now'), 200000, 'Đà Nẵng', 0, 1, 'Cẩn thận đồ dễ vỡ'), " +
                            "(2, 2, 'Hoàng Giáp', '0987654321', 'Vinh, Nghệ An', datetime('now'), 80000, 'Vinh, Nghệ An', 4, 0, 'Huỷ do đặt nhầm');");

                    // 6. Chèn OrderDetails
                    db.execSQL("INSERT INTO OrderDetails (order_id, food_id, quantity, price_at_time) VALUES " +
                            "(1, 1, 1, 159000), " +
                            "(2, 2, 1, 55000), " +
                            "(3, 3, 1, 95000), " +
                            "(4, 1, 1, 159000), " +
                            "(5, 2, 1, 55000);");

                    // 7. Chèn Notifications
                    db.execSQL("INSERT INTO Notifications (title, content, created_at) VALUES " +
                            "('Khuyến mãi sốc', 'Giảm giá 50% toàn bộ thực đơn ngày hôm nay!', '2024-03-20'), " +
                            "('Món mới ra mắt', 'Thử ngay Pizza Hải Sản size L cực đã.', '2024-03-21'), " +
                            "('Freeship cuối tuần', 'Nhập mã FREESHIP để được miễn phí vận chuyển.', '2024-03-22'), " +
                            "('Bảo trì hệ thống', 'Ứng dụng sẽ bảo trì vào lúc 0h sáng mai.', '2024-03-23'), " +
                            "('Chúc mừng sinh nhật', 'Tặng bạn voucher giảm 20% cho đơn hàng tiếp theo.', '2024-03-24');");

                    // 8. Chèn Cart
                    db.execSQL("INSERT INTO Cart (user_id, food_id, quantity, price_at_time) VALUES " +
                            "(2, 1, 2, 159000.0), " +
                            "(3, 2, 1, 55000.0), " +
                            "(4, 3, 3, 95000.0), " +
                            "(5, 4, 1, 45000.0), " +
                            "(2, 5, 2, 15000.0);");

                    // 9. Chèn DeliveryLog
                    db.execSQL("INSERT INTO DeliveryLog (order_id, status_description, log_time) VALUES " +
                            "(1, 'Đơn hàng đã được tiếp nhận', datetime('now')), " +
                            "(1, 'Đang chuẩn bị món ăn', datetime('now')), " +
                            "(2, 'Shipper đã lấy hàng', datetime('now')), " +
                            "(3, 'Đang giao hàng', datetime('now')), " +
                            "(4, 'Đã giao hàng thành công', datetime('now'));");

                    // 10. Chèn Favorites
                    db.execSQL("INSERT INTO Favorites (user_id, food_id) VALUES " +
                            "(2, 1), (2, 2), (3, 1), (4, 4), (5, 5);");

                    // 11. Chèn ProductReview
                    db.execSQL("INSERT INTO ProductReview (user_id, food_id, order_id, rating, comment, image_url, review_date) VALUES " +
                            "(2, 1, 1, 5, 'Rất ngon, giao hàng nhanh!', 'review1.png', datetime('now')), " +
                            "(3, 2, 2, 4, 'Trà sữa hơi ngọt nhưng vẫn ổn.', 'review2.png', datetime('now')), " +
                            "(4, 3, 3, 3, 'Burger hơi nguội.', NULL, datetime('now')), " +
                            "(5, 4, 4, 5, 'Phở rất đậm đà.', 'review4.png', datetime('now')), " +
                            "(2, 5, 5, 2, 'Bánh hơi bé so với giá.', NULL, datetime('now'));");

                    // 12. Chèn SearchHistory
                    db.execSQL("INSERT INTO SearchHistory (user_id, search_query, search_time) VALUES " +
                            "(2, 'Pizza', datetime('now')), " +
                            "(2, 'Burger', datetime('now')), " +
                            "(3, 'Trà sữa', datetime('now')), " +
                            "(4, 'Phở', datetime('now')), " +
                            "(5, 'Lẩu', datetime('now'));");

                } catch (Exception e) {
                    Log.e("FoodDB", "Error inserting initial data", e);
                }
            });
        }
    };
}
