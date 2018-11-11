package com.siziksu.browser.presenter.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Bookmark implements Parcelable {

    public String titleToShow;
    public String title;
    public String url;

    public Bookmark() {}

    @Override
    public String toString() {
        return "{\"titleToShow\" : \"" +  titleToShow + "\", \"title\" : \"" + title + "\", \"url\" : \"" + url + "\"}";
    }

    @Override
    public boolean equals(Object obj) {
        if (title == null || url == null || !(obj instanceof Bookmark)) {
            return false;
        }
        return title.equals(((Bookmark) obj).title) && url.equals(((Bookmark) obj).url);
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

    protected Bookmark(Parcel in) {
        this.titleToShow = in.readString();
        this.title = in.readString();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<Bookmark> CREATOR = new Parcelable.Creator<Bookmark>() {
        @Override
        public Bookmark createFromParcel(Parcel source) {return new Bookmark(source);}

        @Override
        public Bookmark[] newArray(int size) {return new Bookmark[size];}
    };
}
