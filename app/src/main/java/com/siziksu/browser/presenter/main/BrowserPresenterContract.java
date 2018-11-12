package com.siziksu.browser.presenter.main;

import com.siziksu.browser.common.function.Consumer;
import com.siziksu.browser.presenter.BasePresenterContract;
import com.siziksu.browser.presenter.BaseViewContract;
import com.siziksu.browser.presenter.model.Bookmark;

public interface BrowserPresenterContract<V extends BaseViewContract> extends BasePresenterContract<V> {

    void setUrlVisited(String url);

    void getLastVisited(Consumer<String> lastVisited);

    void download(String url);

    void manageBookmark(Bookmark bookmark);

    void checkIfItIsBookmarked(String url, Consumer<Boolean> isBookmarked);

    void copyToClipboard(String title, String text);

    void onBookmarksClick();
}
