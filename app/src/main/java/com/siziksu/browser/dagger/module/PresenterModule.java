package com.siziksu.browser.dagger.module;

import com.siziksu.browser.presenter.BaseViewContract;
import com.siziksu.browser.presenter.bookmarks.BookmarksPresenter;
import com.siziksu.browser.presenter.bookmarks.BookmarksPresenterContract;
import com.siziksu.browser.presenter.main.MainPresenter;
import com.siziksu.browser.presenter.main.MainPresenterContract;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public final class PresenterModule {

    @Singleton
    @Provides
    MainPresenterContract<BaseViewContract> providesMainPresenter() {
        return new MainPresenter();
    }

    @Singleton
    @Provides
    BookmarksPresenterContract<BaseViewContract> providesBookmarksPresenter() {
        return new BookmarksPresenter();
    }
}
