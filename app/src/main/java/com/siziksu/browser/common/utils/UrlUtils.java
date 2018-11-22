package com.siziksu.browser.common.utils;

import com.siziksu.browser.common.Constants;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class UrlUtils {

    private UrlUtils() {}

    public static String getUrlToShow(String url) {
        return url;
    }

    public static String getUrlToShowForBookmarks(String url) {
        return url;
    }

    public static String validateUrl(String string) {
        if (string == null) { return null; }
        if (string.startsWith("file:///") || string.startsWith("data:")) { return string; }
        if (string.contains(".")) {
            if (!string.toLowerCase().contains("http://") && !string.toLowerCase().contains("https://")) {
                string = "http://" + string;
            }
        }
        try {
            URL url = new URL(string);
            return url.toString();
        } catch (MalformedURLException e) {
            try {
                return googleSearch(URLEncoder.encode(string, "UTF-8"));
            } catch (UnsupportedEncodingException unsupported) {
                Print.error(unsupported.getMessage(), unsupported);
                return null;
            }
        }
    }

    public static boolean isDataImage(String url) {
        return url != null && url.startsWith("data:image");
    }

    private static String googleSearch(String userSearch) {
        return "https://" + Constants.URL_GOOGLE_SEARCH + "?q=" + userSearch + Constants.GOOGLE_SAFE_SEARCH;
    }
}
