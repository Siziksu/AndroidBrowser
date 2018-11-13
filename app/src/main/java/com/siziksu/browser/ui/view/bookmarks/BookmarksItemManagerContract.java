package com.siziksu.browser.ui.view.bookmarks;

import com.siziksu.browser.ui.common.model.Page;

import java.util.List;

interface BookmarksItemManagerContract {

    void showItems(BookmarksAdapterContract adapter, List<Page> list);

    List<Page> getItems();

    void deleteItem(BookmarksAdapter adapter, Page page);
}
