package com.siziksu.browser.dagger.module;

import com.siziksu.browser.domain.bookmarks.BookmarksDomain;
import com.siziksu.browser.domain.bookmarks.BookmarksDomainContract;
import com.siziksu.browser.domain.history.HistoryDomain;
import com.siziksu.browser.domain.history.HistoryDomainContract;
import com.siziksu.browser.domain.launch.LaunchDomain;
import com.siziksu.browser.domain.launch.LaunchDomainContract;
import com.siziksu.browser.domain.main.BrowserDomain;
import com.siziksu.browser.domain.main.BrowserDomainContract;
import com.siziksu.browser.domain.main.MainDomain;
import com.siziksu.browser.domain.main.MainDomainContract;
import com.siziksu.browser.domain.main.MainWebViewDomain;
import com.siziksu.browser.domain.main.MainWebViewDomainContract;

import dagger.Module;
import dagger.Provides;

@Module
public final class DomainModule {

    @Provides
    LaunchDomainContract providesLaunchDomain() {
        return new LaunchDomain();
    }

    @Provides
    MainDomainContract providesMainDomain() {
        return new MainDomain();
    }

    @Provides
    BrowserDomainContract providesBrowserDomain() {
        return new BrowserDomain();
    }

    @Provides
    MainWebViewDomainContract providesWebViewDomain() {
        return new MainWebViewDomain();
    }

    @Provides
    BookmarksDomainContract providesBookmarksDomain() {
        return new BookmarksDomain();
    }

    @Provides
    HistoryDomainContract providesHistoryDomain() {
        return new HistoryDomain();
    }
}
