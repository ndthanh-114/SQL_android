package com.example.sqliteexamples;

import android.widget.TextView;

public class ItemModel {
    public String mssv;
    public String hoTen;
    public String email;

    public ItemModel() {

    }

    public ItemModel(String mssv, String hoTen, String email) {
        this.mssv = mssv;
        this.hoTen = hoTen;
        this.email = email;
    }
}
