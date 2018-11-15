package com.siziksu.browser.ui.common.model;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class OverflowMenuItem {

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
            DEFAULT,
            CHECKBOX,
    })
    public @interface MenuItemDef {}

    public static final int DEFAULT = 0;
    public static final int CHECKBOX = 1;

    public int id;
    public String name;
    public int type;
    public boolean isChecked;

    public OverflowMenuItem(int id, String name, @MenuItemDef int type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }
}
