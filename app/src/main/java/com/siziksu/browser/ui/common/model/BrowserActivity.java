package com.siziksu.browser.ui.common.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class BrowserActivity implements Parcelable {

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
                    TYPE_DATE,
                    TYPE_ACTIVITY
            })
    public @interface ObjectType {}

    public static final int TYPE_DATE = 0;
    public static final int TYPE_ACTIVITY = 1;

    public String title;
    public String url;
    public long date;
    private String dateFormatted;
    private int type;

    @Override
    public String toString() {
        return "{" + title + ", " + url + ", " + date + "}";
    }

    public BrowserActivity setType(@ObjectType int type) {
        this.type = type;
        return this;
    }

    public int getType() {
        return type;
    }

    public BrowserActivity setDateFormatted(String dateFormatted) {
        this.dateFormatted = dateFormatted;
        return this;
    }

    public String getDateFormatted() {
        return dateFormatted;
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
