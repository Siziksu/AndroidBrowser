package com.siziksu.browser.data.model;

public class BrowserActivityData {

    public String title;
    public String url;
    public long date;

    @Override
    public String toString() {
        return "{" + title + ", " + url + ", " + date + "}";
    }
}
