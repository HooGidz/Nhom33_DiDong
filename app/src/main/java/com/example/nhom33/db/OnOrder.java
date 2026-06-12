package com.example.nhom33.db;

public class OnOrder {
    private String category;
    private String storeName;
    private String orderId;
    private String price;
    private String itemCount;
    private int imageResId;
    public OnOrder(String category, String storeName, String orderId, String price, String itemCount, int imageResId)
    {
        this.category = category;
        this.storeName = storeName;
        this.orderId = orderId;
        this.price = price;
        this.itemCount = itemCount;
        this.imageResId = imageResId;
    }
    public String getCategory() {
        return category;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getPrice() {
        return price;
    }

    public String getItemCount() {
        return itemCount;
    }

    public int getImageResId() {
        return imageResId;
    }

}

