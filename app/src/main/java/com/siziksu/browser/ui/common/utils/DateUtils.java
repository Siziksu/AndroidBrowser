package com.siziksu.browser.ui.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class DateUtils {

    private static DateFormat dateFormat1 = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());

    private DateUtils() {}

    public static String convertLongDateToFormattedString(long date) {
        return dateFormat1.format(new Date(date));
    }
}
