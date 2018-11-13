package com.siziksu.browser.domain.model;

public class PageDomain {

    public String titleToShow;
    public String title;
    public String url;

    public PageDomain() {}

    @Override
    public boolean equals(Object obj) {
        if (title == null || url == null || !(obj instanceof PageDomain)) {
            return false;
        }
        return title.equals(((PageDomain) obj).title) && url.equals(((PageDomain) obj).url);
    }

    @Override
    public int hashCode() {
        return 31 * 17 + title.length() + url.length();
    }
}
