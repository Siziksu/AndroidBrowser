package com.siziksu.browser.domain.model;

public class BookmarkDomain {

    public String titleToShow;
    public String title;
    public String url;

    public BookmarkDomain() {}

    @Override
    public boolean equals(Object obj) {
        if (title == null || url == null || !(obj instanceof BookmarkDomain)) {
            return false;
        }
        return title.equals(((BookmarkDomain) obj).title) && url.equals(((BookmarkDomain) obj).url);
    }

    @Override
    public int hashCode() {
        return 31 * 17 + title.length() + url.length();
    }
}
