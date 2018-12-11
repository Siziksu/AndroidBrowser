package com.siziksu.browser.ui.common.model;

import android.os.Parcel;
import android.os.Parcelable;

public class BrowserActivity implements Parcelable {

    public String title;
    public String url;
    public long date;

    @Override
    public String toString() {
        return "{" + title + ", " + url + ", " + date + "}";
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.url);
        dest.writeLong(this.date);
    }

    public BrowserActivity() {}

    protected BrowserActivity(Parcel in) {
        this.title = in.readString();
        this.url = in.readString();
        this.date = in.readLong();
    }

    public static final Parcelable.Creator<BrowserActivity> CREATOR = new Parcelable.Creator<BrowserActivity>() {

        @Override
        public BrowserActivity createFromParcel(Parcel source) {return new BrowserActivity(source);}

        @Override
        public BrowserActivity[] newArray(int size) {return new BrowserActivity[size];}
    };
}
