package com.example.nhom33.db;

public class AdProfile {
    private String title;
    private int icon;
    public AdProfile(String title, int icon) {
        this.title = title;
        this.icon = icon;
    }
    public String getTitle() {
        return title;
    }
    public int getIcon() {
        return icon;
    }
}
