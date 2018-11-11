package com.siziksu.browser.data.client.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookmarkClient {

    @SerializedName("titleToShow")
    @Expose
    public String titleToShow;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("url")
    @Expose
    public String url;

    public BookmarkClient() {}

    @Override
    public boolean equals(Object obj) {
        if (title == null || url == null || !(obj instanceof BookmarkClient)) {
            return false;
        }
        return title.equals(((BookmarkClient) obj).title) && url.equals(((BookmarkClient) obj).url);
    }

    @Override
    public int hashCode() {
        return 31 * 17 + title.length() + url.length();
    }
}
