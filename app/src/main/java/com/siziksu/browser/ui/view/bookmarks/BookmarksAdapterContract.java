package com.siziksu.browser.ui.view.bookmarks;

import android.support.v7.widget.RecyclerView;

import com.siziksu.browser.common.function.Consumer;
import com.siziksu.browser.ui.common.model.Page;

import java.util.List;

interface BookmarksAdapterContract {

    void init(Consumer<Page> itemClick, Consumer<Page> delete);

    RecyclerView.LayoutManager getLayoutManager();

    RecyclerView.Adapter getAdapter();

    void notifyDataSetChanged();

    void showBookmarks(List<Page> list);

    void deleteItem(Page page);
}
