package com.siziksu.browser.domain.bookmarks;

import com.siziksu.browser.common.function.Action;
import com.siziksu.browser.common.function.Consumer;
import com.siziksu.browser.domain.BaseDomainContract;
import com.siziksu.browser.domain.model.PageDomain;

import java.util.List;

public interface BookmarksDomainContract extends BaseDomainContract {

    void getBookmarks(Consumer<List<PageDomain>> result);

    void deleteBookmark(PageDomain bookmark, Action action);
}
