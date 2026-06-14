package com.example.nhom33.db;

public class OnOrder {
    private String category;
    private String storeName;
    private String orderId;
    private String price;
    private String itemCount;
    private String status;
    private String dateTime;
    private int imageResId;

    public OnOrder(String category, String storeName, String orderId, String price, String itemCount, String status, String dateTime, int imageResId) {
        this.category = category;
        this.storeName = storeName;
        this.orderId = orderId;
        this.price = price;
        this.itemCount = itemCount;
        this.status = status;
        this.dateTime = dateTime;
        this.imageResId = imageResId;
    }

    public String getCategory() { return category; }
    public String getStoreName() { return storeName; }
    public String getOrderId() { return orderId; }
    public String getPrice() { return price; }
    public String getItemCount() { return itemCount; }
    public String getStatus() { return status; }
    public String getDateTime() { return dateTime; }
    public int getImageResId() { return imageResId; }
}
