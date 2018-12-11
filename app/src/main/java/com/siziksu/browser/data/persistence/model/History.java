package com.siziksu.browser.data.persistence.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class History {

    @PrimaryKey
    @NonNull
    public String url;
    public String title;
    public long date;

    @Override
    public boolean equals(Object obj) {
        if (title == null || !(obj instanceof History)) {
            return false;
        }
        return title.equals(((History) obj).title) && url.equals(((History) obj).url);
    }

    @Override
    public int hashCode() {
        return 31 * 17 + title.length() + url.length();
    }
}
