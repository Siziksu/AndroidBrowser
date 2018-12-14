package com.siziksu.browser.presenter.history;

import com.siziksu.browser.ui.common.model.BrowserActivity;
import com.siziksu.browser.ui.common.utils.DateUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

final class CollectionUtils {

    private CollectionUtils() {}

    static void sortActivityByDate(List<BrowserActivity> activities) {
        Collections.sort(activities, (activity1, activity2) -> Long.compare(activity2.date, activity1.date));
    }

    static List<BrowserActivity> groupItemsByDate(List<BrowserActivity> activities) {
        List<BrowserActivity> list = new ArrayList<>();
        String control = "";
        String date;
        for (int i = 0; i < activities.size(); i++) {
            date = DateUtils.convertLongDateToFormattedString(activities.get(i).date);
            if (control.equals(date)) {
                list.add(activities.get(i).setType(BrowserActivity.TYPE_ACTIVITY).setDateFormatted(date));
            } else {
                list.add(new BrowserActivity().setType(BrowserActivity.TYPE_DATE).setDateFormatted(date));
                list.add(activities.get(i).setType(BrowserActivity.TYPE_ACTIVITY).setDateFormatted(date));
                control = date;
            }
        }
        return list;
    }
}
