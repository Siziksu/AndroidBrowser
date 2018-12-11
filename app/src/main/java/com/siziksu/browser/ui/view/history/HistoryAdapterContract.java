package com.siziksu.browser.ui.view.history;

import android.support.v7.widget.RecyclerView;

import com.siziksu.browser.common.function.Consumer;
import com.siziksu.browser.ui.common.model.BrowserActivity;

import java.util.List;

interface HistoryAdapterContract {

    void init(Consumer<BrowserActivity> itemClick, Consumer<BrowserActivity> delete);

    RecyclerView.LayoutManager getLayoutManager();

    RecyclerView.Adapter getAdapter();

    void notifyDataSetChanged();

    void showHistory(List<BrowserActivity> list);

    void deleteItem(BrowserActivity activity);
}
