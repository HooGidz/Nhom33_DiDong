package com.example.nhom33.db;

public class item_food {
    int img_food;
    String txt_food, txt_price, txt_tag;

    public item_food() {
    }

    public item_food(int img_food, String txt_food, String txt_price, String txt_tag) {
        this.img_food = img_food;
        this.txt_food = txt_food;
        this.txt_price = txt_price;
        this.txt_tag = txt_tag;
    }

    public int getImg_food() {
        return img_food;
    }

    public void setImg_food(int img_food) {
        this.img_food = img_food;
    }

    public String getTxt_food() {
        return txt_food;
    }

    public void setTxt_food(String txt_food) {
        this.txt_food = txt_food;
    }

    public String getTxt_price() {
        return txt_price;
    }

    public void setTxt_price(String txt_price) {
        this.txt_price = txt_price;
    }

    public String getTxt_tag() {
        return txt_tag;
    }

    public void setTxt_tag(String txt_tag) {
        this.txt_tag = txt_tag;
    }
}
