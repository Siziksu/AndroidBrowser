package com.siziksu.browser.domain.bookmarks;

import com.siziksu.browser.common.function.Action;
import com.siziksu.browser.common.function.Consumer;
import com.siziksu.browser.domain.BaseDomainContract;
import com.siziksu.browser.domain.model.BookmarkDomain;

import java.util.List;

public interface BookmarksDomainContract extends BaseDomainContract {

    void getBookmarks(Consumer<List<BookmarkDomain>> result);

    void deleteBookmark(BookmarkDomain bookmark, Action action);
}
