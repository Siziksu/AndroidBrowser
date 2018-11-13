package com.siziksu.browser.data.client.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PageClient {

    @SerializedName("titleToShow")
    @Expose
    public String titleToShow;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("url")
    @Expose
    public String url;

    public PageClient() {}

    @Override
    public boolean equals(Object obj) {
        if (title == null || url == null || !(obj instanceof PageClient)) {
            return false;
        }
        return title.equals(((PageClient) obj).title) && url.equals(((PageClient) obj).url);
    }

    @Override
    public int hashCode() {
        return 31 * 17 + title.length() + url.length();
    }
}
