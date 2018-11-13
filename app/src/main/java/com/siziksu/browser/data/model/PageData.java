package com.siziksu.browser.data.model;

public class PageData {

    public String titleToShow;
    public String title;
    public String url;

    public PageData() {}

    @Override
    public boolean equals(Object obj) {
        if (title == null || url == null || !(obj instanceof PageData)) {
            return false;
        }
        return title.equals(((PageData) obj).title) && url.equals(((PageData) obj).url);
    }

    @Override
    public int hashCode() {
        return 31 * 17 + title.length() + url.length();
    }
}
