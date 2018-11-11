package com.siziksu.browser.ui.view.bookmarks;

import com.siziksu.browser.presenter.model.Bookmark;

import java.util.ArrayList;
import java.util.List;

class BookmarksItemManager implements BookmarksItemManagerContract {

    private List<Bookmark> items = new ArrayList<>();

    BookmarksItemManager() {}

    @Override
    public void showItems(BookmarksAdapterContract adapter, List<Bookmark> list) {
        items.clear();
        items.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public List<Bookmark> getItems() {
        return items;
    }

    @Override
    public void deleteItem(BookmarksAdapter adapter, Bookmark bookmark) {
        int index = items.indexOf(bookmark);
        items.remove(index);
        adapter.notifyItemRemoved(index);
    }
}
