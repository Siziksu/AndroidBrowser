package com.siziksu.browser.data.persistence.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Bookmark {

    @PrimaryKey
    @NonNull
    public String url;
    public String title;
    public String titleToShow;

    @Override
    public boolean equals(Object obj) {
        if (title == null || !(obj instanceof Bookmark)) {
            return false;
        }
        return title.equals(((Bookmark) obj).title) && url.equals(((Bookmark) obj).url);
    }

    @Override
    public int hashCode() {
        return 31 * 17 + title.length() + url.length();
    }
}
