package com.example.nhom33.db;

public class item_food {
    private int food_id;
    private String img_url; // Đổi từ int sang String
    private String txt_food;
    private String txt_price;
    private String txt_size; // Thay txt_tag bằng txt_size

    public item_food() {
    }

    public item_food(int food_id, String img_url, String txt_food, String txt_price, String txt_size) {
        this.food_id = food_id;
        this.img_url = img_url;
        this.txt_food = txt_food;
        this.txt_price = txt_price;
        this.txt_size = txt_size;
    }

    public int getFood_id() { return food_id; }
    public void setFood_id(int food_id) { this.food_id = food_id; }

    public String getImg_url() { return img_url; }
    public void setImg_url(String img_url) { this.img_url = img_url; }

    public String getTxt_food() { return txt_food; }
    public void setTxt_food(String txt_food) { this.txt_food = txt_food; }

    public String getTxt_price() { return txt_price; }
    public void setTxt_price(String txt_price) { this.txt_price = txt_price; }

    public String getTxt_size() { return txt_size; }
    public void setTxt_size(String txt_size) { this.txt_size = txt_size; }
}
