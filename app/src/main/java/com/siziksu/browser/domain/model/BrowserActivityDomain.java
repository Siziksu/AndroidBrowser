package com.siziksu.browser.domain.model;

public class BrowserActivityDomain {

    public String title;
    public String url;
    public long date;

    @Override
    public String toString() {
        return "{" + title + ", " + url + ", " + date + "}";
    }
}
