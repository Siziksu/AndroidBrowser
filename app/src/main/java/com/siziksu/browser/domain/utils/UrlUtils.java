package com.siziksu.browser.domain.utils;

import android.annotation.SuppressLint;

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

    private String googleSearch(String userSearch) {
        return "https://" + Constants.URL_GOOGLE_SEARCH + "?q=" + userSearch;
    }
}
