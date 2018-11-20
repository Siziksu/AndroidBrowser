package com.siziksu.browser.domain.main;

import com.siziksu.browser.common.function.Consumer;
import com.siziksu.browser.domain.BaseDomainContract;
import com.siziksu.browser.domain.model.PageDomain;

public interface BrowserDomainContract extends BaseDomainContract {

    void setPageVisited(String url);

    void getLastPageVisited(Consumer<String> result);

    void manageBookmark(PageDomain bookmark);

    void isUrlBookmarked(String url, Consumer<Boolean> result);
}
