package com.siziksu.browser.dagger.module;

import com.siziksu.browser.domain.bookmarks.BookmarksDomain;
import com.siziksu.browser.domain.bookmarks.BookmarksDomainContract;
import com.siziksu.browser.domain.main.MainDomain;
import com.siziksu.browser.domain.main.MainDomainContract;

import dagger.Module;
import dagger.Provides;

@Module
public final class DomainModule {

    @Provides
    MainDomainContract providesMainDomain() {
        return new MainDomain();
    }

    @Provides
    BookmarksDomainContract providesBookmarksDomain() {
        return new BookmarksDomain();
    }
}
