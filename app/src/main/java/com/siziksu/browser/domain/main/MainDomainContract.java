package com.siziksu.browser.domain.main;

import com.siziksu.browser.common.function.Consumer;
import com.siziksu.browser.domain.BaseDomainContract;
import com.siziksu.browser.domain.model.BookmarkDomain;

public interface MainDomainContract extends BaseDomainContract {

    void setUrlVisited(String url);

    void getLastVisited(Consumer<String> result);

    void manageBookmark(BookmarkDomain bookmark);

    void checkIfItIsBookmarked(String url, Consumer<Boolean> result);
}
