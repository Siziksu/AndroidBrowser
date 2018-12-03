package com.siziksu.browser.dagger.module;

import com.siziksu.browser.presenter.bookmarks.BookmarksPresenter;
import com.siziksu.browser.presenter.bookmarks.BookmarksPresenterContract;
import com.siziksu.browser.presenter.bookmarks.BookmarksViewContract;
import com.siziksu.browser.presenter.launch.LaunchPresenter;
import com.siziksu.browser.presenter.launch.LaunchPresenterContract;
import com.siziksu.browser.presenter.launch.LaunchViewContract;
import com.siziksu.browser.presenter.main.BrowserPresenter;
import com.siziksu.browser.presenter.main.BrowserPresenterContract;
import com.siziksu.browser.presenter.main.BrowserViewContract;
import com.siziksu.browser.presenter.main.MainPresenter;
import com.siziksu.browser.presenter.main.MainPresenterContract;
import com.siziksu.browser.presenter.main.MainViewContract;
import com.siziksu.browser.presenter.main.WebViewPresenter;
import com.siziksu.browser.presenter.main.WebViewPresenterContract;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public final class PresenterModule {

    @Singleton
    @Provides
    LaunchPresenterContract<LaunchViewContract> providesLaunchPresenter() {
        return new LaunchPresenter();
    }

    @Singleton
    @Provides
    MainPresenterContract<MainViewContract> providesMainPresenter() {
        return new MainPresenter();
    }

    @Singleton
    @Provides
    BrowserPresenterContract<BrowserViewContract> providesBrowserPresenter() {
        return new BrowserPresenter();
    }

    @Singleton
    @Provides
    BookmarksPresenterContract<BookmarksViewContract> providesBookmarksPresenter() {
        return new BookmarksPresenter();
    }

    @Singleton
    @Provides
    WebViewPresenterContract providesWebViewPresenter() {
        return new WebViewPresenter();
    }
}
