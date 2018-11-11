package com.siziksu.browser.common.utils;

import com.siziksu.browser.presenter.model.Bookmark;

import java.util.Collections;
import java.util.List;

public final class CollectionsUtils {

    private CollectionsUtils() {}

    public static void sortUsersByName(List<Bookmark> bookmarks) {
        Collections.sort(bookmarks, (page1, page2) -> page1.titleToShow.compareToIgnoreCase(page2.titleToShow));
    }
}
