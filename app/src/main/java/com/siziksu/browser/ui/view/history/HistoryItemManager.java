package com.siziksu.browser.ui.view.history;

import com.siziksu.browser.ui.common.model.BrowserActivity;

import java.util.ArrayList;
import java.util.List;

final class HistoryItemManager implements HistoryItemManagerContract {

    private List<BrowserActivity> items = new ArrayList<>();

    HistoryItemManager() {}

    @Override
    public void showItems(HistoryAdapterContract adapter, List<BrowserActivity> list) {
        items.clear();
        items.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public List<BrowserActivity> getItems() {
        return items;
    }

    @Override
    public void deleteItem(HistoryAdapter adapter, BrowserActivity activity) {
        int index = items.indexOf(activity);
        items.remove(index);
        adapter.notifyItemRemoved(index);
    }
}
