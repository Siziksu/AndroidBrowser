package com.siziksu.browser.presenter.bookmarks;

import com.siziksu.browser.common.function.Action;
import com.siziksu.browser.common.function.Consumer;
import com.siziksu.browser.presenter.BasePresenterContract;
import com.siziksu.browser.presenter.BaseViewContract;
import com.siziksu.browser.ui.common.model.Page;

import java.util.List;

public interface BookmarksPresenterContract<V extends BaseViewContract> extends BasePresenterContract<V> {

    void getBookmarks(Consumer<List<Page>> result);

    void deleteBookmark(Page page, Action action);
}
