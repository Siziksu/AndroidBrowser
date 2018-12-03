package com.siziksu.browser.presenter.bookmarks;

import com.siziksu.browser.presenter.BaseViewContract;
import com.siziksu.browser.ui.common.model.Page;

import java.util.List;

public interface BookmarksViewContract extends BaseViewContract {

    void showBookmarks(List<Page> bookmarks);
}
