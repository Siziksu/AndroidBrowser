package com.siziksu.browser.ui.view.main.menu.dialog;

import android.support.v7.widget.RecyclerView;

import com.siziksu.browser.common.function.Consumer;
import com.siziksu.browser.ui.common.model.OverflowMenuItem;

import java.util.List;

interface OverflowMenuAdapterContract {

    void init(Consumer<Integer> listener);

    RecyclerView.LayoutManager getLayoutManager();

    RecyclerView.Adapter getAdapter();

    void notifyDataSetChanged();

    void addItems(List<OverflowMenuItem> list);
}
