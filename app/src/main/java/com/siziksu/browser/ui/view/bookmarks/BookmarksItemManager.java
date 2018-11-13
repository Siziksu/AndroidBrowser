package com.siziksu.browser.ui.view.bookmarks;

import com.siziksu.browser.ui.common.model.Page;

import java.util.ArrayList;
import java.util.List;

class BookmarksItemManager implements BookmarksItemManagerContract {

    private List<Page> items = new ArrayList<>();

    BookmarksItemManager() {}

    @Override
    public void showItems(BookmarksAdapterContract adapter, List<Page> list) {
        items.clear();
        items.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public List<Page> getItems() {
        return items;
    }

    @Override
    public void deleteItem(BookmarksAdapter adapter, Page page) {
        int index = items.indexOf(page);
        items.remove(index);
        adapter.notifyItemRemoved(index);
    }
}
