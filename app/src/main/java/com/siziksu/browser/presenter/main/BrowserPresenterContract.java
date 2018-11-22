package com.siziksu.browser.presenter.main;

import com.siziksu.browser.common.function.Consumer;
import com.siziksu.browser.presenter.BasePresenterContract;
import com.siziksu.browser.presenter.BaseViewContract;
import com.siziksu.browser.ui.common.model.Page;

public interface BrowserPresenterContract<V extends BaseViewContract> extends BasePresenterContract<V> {

    void setPageVisited(String url);

    void getLastPageVisited(Consumer<String> lastVisited);

    void clearLastPageVisited();

    void download(String url);

    void manageBookmark(Page page);

    void isUrlBookmarked(String url, Consumer<Boolean> isBookmarked);

    void copyToClipboard(String title, String text);

    void onBookmarksButtonClick();
}
