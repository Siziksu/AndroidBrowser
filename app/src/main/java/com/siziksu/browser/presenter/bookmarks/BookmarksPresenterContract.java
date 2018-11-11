package com.siziksu.browser.presenter.bookmarks;

import com.siziksu.browser.common.function.Action;
import com.siziksu.browser.common.function.Consumer;
import com.siziksu.browser.presenter.BasePresenterContract;
import com.siziksu.browser.presenter.BaseViewContract;
import com.siziksu.browser.presenter.model.Bookmark;

import java.util.List;

public interface BookmarksPresenterContract<V extends BaseViewContract> extends BasePresenterContract<V> {

    void getBookmarks(Consumer<List<Bookmark>> result);

    void deleteBookmark(Bookmark bookmark, Action action);
}
