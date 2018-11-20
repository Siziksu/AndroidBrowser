package com.siziksu.browser.dagger.module;

import com.siziksu.browser.domain.bookmarks.BookmarksDomain;
import com.siziksu.browser.domain.bookmarks.BookmarksDomainContract;
import com.siziksu.browser.domain.main.BrowserDomain;
import com.siziksu.browser.domain.main.BrowserDomainContract;

import dagger.Module;
import dagger.Provides;

@Module
public final class DomainModule {

    @Provides
    BrowserDomainContract providesBrowserDomain() {
        return new BrowserDomain();
    }

    @Provides
    BookmarksDomainContract providesBookmarksDomain() {
        return new BookmarksDomain();
    }
}
