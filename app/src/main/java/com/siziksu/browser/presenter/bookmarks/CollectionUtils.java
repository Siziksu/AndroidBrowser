package com.siziksu.browser.presenter.bookmarks;

import com.siziksu.browser.ui.common.model.Page;

import java.util.Collections;
import java.util.List;

final class CollectionUtils {

    private CollectionUtils() {}

    static void sortUsersByName(List<Page> pages) {
        Collections.sort(pages, (page1, page2) -> page1.titleToShow.compareToIgnoreCase(page2.titleToShow));
    }
}
