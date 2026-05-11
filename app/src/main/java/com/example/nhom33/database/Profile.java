package com.example.nhom33.database;

public class Profile {
    private String title;
    private int iconResId;
    public Profile(String title, int iconResId) {
        this.title = title;
        this.iconResId = iconResId;
    }
    public String getTitle() {
        return title;
    }
    public int getIconResId() {
        return iconResId;
    }
}
