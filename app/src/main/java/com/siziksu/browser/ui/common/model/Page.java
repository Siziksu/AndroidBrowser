package com.siziksu.browser.ui.common.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Page implements Parcelable {

    public String titleToShow;
    public String title;
    public String url;

    public Page() {}

    @Override
    public String toString() {
        return "{\"titleToShow\" : \"" +  titleToShow + "\", \"title\" : \"" + title + "\", \"url\" : \"" + url + "\"}";
    }

    @Override
    public boolean equals(Object obj) {
        if (title == null || url == null || !(obj instanceof Page)) {
            return false;
        }
        return title.equals(((Page) obj).title) && url.equals(((Page) obj).url);
    }

    @Override
    public int hashCode() {
        return 31 * 17 + title.length() + url.length();
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.titleToShow);
        dest.writeString(this.title);
        dest.writeString(this.url);
    }

    protected Page(Parcel in) {
        this.titleToShow = in.readString();
        this.title = in.readString();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<Page> CREATOR = new Parcelable.Creator<Page>() {
        @Override
        public Page createFromParcel(Parcel source) {return new Page(source);}

        @Override
        public Page[] newArray(int size) {return new Page[size];}
    };
}
