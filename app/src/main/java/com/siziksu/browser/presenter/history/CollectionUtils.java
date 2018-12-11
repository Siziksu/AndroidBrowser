package com.siziksu.browser.presenter.history;

import com.siziksu.browser.ui.common.model.BrowserActivity;

import java.util.Collections;
import java.util.List;

final class CollectionUtils {

    private CollectionUtils() {}

    static void sortActivityByDate(List<BrowserActivity> activities) {
        Collections.sort(activities, (activity1, activity2) -> Long.compare(activity2.date, activity1.date));
    }
}
