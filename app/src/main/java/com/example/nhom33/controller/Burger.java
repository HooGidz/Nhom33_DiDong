package com.example.nhom33.controller;

public class Burger {
    private String name;
    private String restaurant;
    private String price;
    private int imageRes;

    public Burger(String name, String restaurant, String price, int imageRes) {
        this.name = name;
        this.restaurant = restaurant;
        this.price = price;
        this.imageRes = imageRes;
    }

    public String getName() { return name; }
    public String getRestaurant() { return restaurant; }
    public String getPrice() { return price; }
    public int getImageRes() { return imageRes; }
}