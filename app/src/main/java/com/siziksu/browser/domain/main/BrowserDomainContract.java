package com.siziksu.browser.domain.main;

import com.siziksu.browser.common.function.Consumer;
import com.siziksu.browser.domain.BaseDomainContract;
import com.siziksu.browser.domain.model.BrowserActivityDomain;
import com.siziksu.browser.domain.model.PageDomain;

public interface BrowserDomainContract extends BaseDomainContract {

    void onPageVisited(String url);

    void onBookmarkButtonClicked(PageDomain page);

    void isUrlBookmarked(String url, Consumer<Boolean> result);

    void onBrowserActivity(BrowserActivityDomain activity);

    void isDataImage(String url, Consumer<Boolean> result);
}
