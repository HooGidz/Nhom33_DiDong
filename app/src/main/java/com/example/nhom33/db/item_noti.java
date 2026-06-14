package com.example.nhom33.db;

public class item_noti {
    int img_user;
    String txt_title, txt_content, txt_time;


    public item_noti(int img_user, String txt_title, String txt_content, String txt_time) {
        this.img_user = img_user;
        this.txt_title = txt_title;
        this.txt_content = txt_content;
        this.txt_time = txt_time;
    }

    public int getImg_user() {
        return img_user;
    }

    public void setImg_user(int img_user) {
        this.img_user = img_user;
    }



    public String getTxt_title() {
        return txt_title;
    }

    public void setTxt_title(String txt_title) {
        this.txt_title = txt_title;
    }
    public String getTxt_content() {
        return txt_content;
    }

    public void setTxt_content(String txt_content) {
        this.txt_content = txt_content;
    }



    public String getTxt_time() {
        return txt_time;
    }

    public void setTxt_time(String txt_time) {
        this.txt_time = txt_time;
    }
}
