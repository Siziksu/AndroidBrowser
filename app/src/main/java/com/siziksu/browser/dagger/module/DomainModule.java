package com.siziksu.browser.dagger.module;

import com.siziksu.browser.domain.bookmarks.BookmarksDomain;
import com.siziksu.browser.domain.bookmarks.BookmarksDomainContract;
import com.siziksu.browser.domain.launch.LaunchDomain;
import com.siziksu.browser.domain.launch.LaunchDomainContract;
import com.siziksu.browser.domain.main.BrowserDomain;
import com.siziksu.browser.domain.main.BrowserDomainContract;
import com.siziksu.browser.domain.main.MainDomain;
import com.siziksu.browser.domain.main.MainDomainContract;

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
    BookmarksDomainContract providesBookmarksDomain() {
        return new BookmarksDomain();
    }
}
