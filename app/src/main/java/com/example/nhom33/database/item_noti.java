package com.example.nhom33.database;

public class item_noti {
    int img_user, img_food;
    String txt_notification, txt_time;

    public item_noti(int img_user, int img_food, String txt_notification, String txt_time) {
        this.img_user = img_user;
        this.img_food = img_food;
        this.txt_notification = txt_notification;
        this.txt_time = txt_time;
    }

    public int getImg_user() {
        return img_user;
    }

    public void setImg_user(int img_user) {
        this.img_user = img_user;
    }

    public int getImg_food() {
        return img_food;
    }

    public void setImg_food(int img_food) {
        this.img_food = img_food;
    }

    public String getTxt_notification() {
        return txt_notification;
    }

    public void setTxt_notification(String txt_notification) {
        this.txt_notification = txt_notification;
    }

    public String getTxt_time() {
        return txt_time;
    }

    public void setTxt_time(String txt_time) {
        this.txt_time = txt_time;
    }
}
