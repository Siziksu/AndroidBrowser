package com.siziksu.browser.data.model;

public class BookmarkData {

    public String titleToShow;
    public String title;
    public String url;

    public BookmarkData() {}

    @Override
    public boolean equals(Object obj) {
        if (title == null || url == null || !(obj instanceof BookmarkData)) {
            return false;
        }
        return title.equals(((BookmarkData) obj).title) && url.equals(((BookmarkData) obj).url);
    }

    @Override
    public int hashCode() {
        return 31 * 17 + title.length() + url.length();
    }
}
