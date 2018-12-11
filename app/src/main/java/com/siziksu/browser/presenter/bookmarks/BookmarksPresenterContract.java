package com.siziksu.browser.presenter.bookmarks;

import com.siziksu.browser.common.function.Action;
import com.siziksu.browser.presenter.BasePresenterContract;
import com.siziksu.browser.presenter.BaseViewContract;
import com.siziksu.browser.ui.common.model.Page;

public interface BookmarksPresenterContract<V extends BaseViewContract> extends BasePresenterContract<V> {

    void getBookmarks();

    void deleteBookmark(Page page, Action onDeleted);
}
