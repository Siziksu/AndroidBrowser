package com.siziksu.browser.common.utils;

import com.siziksu.browser.ui.common.model.Page;

import java.util.Collections;
import java.util.List;

public final class CollectionsUtils {

    private CollectionsUtils() {}

    public static void sortUsersByName(List<Page> pages) {
        Collections.sort(pages, (page1, page2) -> page1.titleToShow.compareToIgnoreCase(page2.titleToShow));
    }
}
