package com.example.nhom33.db;

public class hisOrder {
    private String category;
    private String status;
    private String storeName;
    private String orderId;
    private String price;
    private String dateTime;
    private String itemCount;
    private int imageResId;
    private boolean isReviewed;

    public hisOrder(String category, String status, String storeName, String orderId, String price, String dateTime, String itemCount, int imageResId, boolean isReviewed) {
        this.category = category;
        this.status = status;
        this.storeName = storeName;
        this.orderId = orderId;
        this.price = price;
        this.dateTime = dateTime;
        this.itemCount = itemCount;
        this.imageResId = imageResId;
        this.isReviewed = isReviewed;
    }

    public String getCategory() { return category; }
    public String getStatus() { return status; }
    public String getStoreName() { return storeName; }
    public String getOrderId() { return orderId; }
    public String getPrice() { return price; }
    public String getDateTime() { return dateTime; }
    public String getItemCount() { return itemCount; }
    public int getImageResId() { return imageResId; }
    public boolean isReviewed() { return isReviewed; }
    public void setReviewed(boolean reviewed) { isReviewed = reviewed; }
}