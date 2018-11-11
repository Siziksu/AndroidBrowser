package com.siziksu.browser.ui.view.bookmarks;

import com.siziksu.browser.presenter.model.Bookmark;

import java.util.List;

interface BookmarksItemManagerContract {

    void showItems(BookmarksAdapterContract adapter, List<Bookmark> list);

    List<Bookmark> getItems();

    void deleteItem(BookmarksAdapter adapter, Bookmark bookmark);
}
