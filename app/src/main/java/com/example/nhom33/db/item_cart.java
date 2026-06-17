package com.example.nhom33.db;

public class item_cart {
    private int foodId;
    private String name;
    private String quantity;
    private int price;
    private int originalPrice;
    private String imageUrl;

    public item_cart(int foodId, String name, String quantity, int price, int originalPrice, String imageUrl) {
        this.foodId = foodId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.originalPrice = originalPrice;
        this.imageUrl = imageUrl;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(int originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
