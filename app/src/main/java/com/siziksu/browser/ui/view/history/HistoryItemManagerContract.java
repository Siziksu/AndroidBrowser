package com.siziksu.browser.ui.view.history;

import com.siziksu.browser.ui.common.model.BrowserActivity;

import java.util.List;

interface HistoryItemManagerContract {

    void showItems(HistoryAdapterContract adapter, List<BrowserActivity> list);

    List<BrowserActivity> getItems();

    void deleteItem(HistoryAdapter adapter, BrowserActivity activity);

    int getItemType(int position);
}
