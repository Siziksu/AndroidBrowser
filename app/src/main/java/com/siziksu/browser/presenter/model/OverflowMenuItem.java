package com.siziksu.browser.presenter.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OverflowMenuItem {

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("name")
    @Expose
    public String name;

    public OverflowMenuItem(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
