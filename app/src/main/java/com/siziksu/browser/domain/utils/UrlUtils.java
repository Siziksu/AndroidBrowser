package com.siziksu.browser.domain.utils;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import com.siziksu.browser.common.Constants;
import com.siziksu.browser.common.function.Consumer;
import com.siziksu.browser.common.utils.Print;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.inject.Inject;

public final class UrlUtils {

    @Inject
    public UrlUtils() {}

    public boolean isDataImage(String url) {
        return url != null && url.startsWith("data:image");
    }

    @SuppressLint("CheckResult")
    public void filterUrl(String string, Consumer<String> result) {
        result.accept(validateUrl(string));
    }

    private String validateUrl(String string) {
        try {
            if (string == null) { return null; }
            if (checkIfLocalUrlOrData(string)) { return string; }
            if (!string.contains(" ")) {
                string = format(string);
                URL url = new URL(string);
                return url.toString();
            } else {
                return onMalformedString(string);
            }
        } catch (MalformedURLException e) {
            return onMalformedString(string);
        }
    }

    @NonNull
    private String onMalformedString(String string) {
        try {
            return googleSearch(URLEncoder.encode(string, "UTF-8"));
        } catch (UnsupportedEncodingException unsupported) {
            Print.error(unsupported.getMessage(), unsupported);
            return Constants.URL_GOOGLE;
        }
    }

    private boolean checkIfLocalUrlOrData(String string) {
        return string.startsWith("file:///") || string.startsWith("data:");
    }

    private String format(String string) {
        if (string.contains(".")) {
            if (!string.toLowerCase().contains("http://") && !string.toLowerCase().contains("https://")) {
                return "http://" + string;
            }
        }
        return string;
    }

    private String googleSearch(String userSearch) {
        return "https://" + Constants.URL_GOOGLE_SEARCH + "?q=" + userSearch;
    }
}
