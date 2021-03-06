package com.siziksu.browser.dagger.component;

import com.siziksu.browser.App;
import com.siziksu.browser.dagger.module.ApplicationModule;
import com.siziksu.browser.dagger.module.DataModule;
import com.siziksu.browser.dagger.module.DomainModule;
import com.siziksu.browser.dagger.module.PresenterModule;
import com.siziksu.browser.dagger.module.RoomModule;
import com.siziksu.browser.data.Repository;
import com.siziksu.browser.data.client.PreferencesClient;
import com.siziksu.browser.data.persistence.BrowserDatabase;
import com.siziksu.browser.domain.bookmarks.BookmarksDomain;
import com.siziksu.browser.domain.history.HistoryDomain;
import com.siziksu.browser.domain.launch.LaunchDomain;
import com.siziksu.browser.domain.main.BrowserDomain;
import com.siziksu.browser.domain.main.MainDomain;
import com.siziksu.browser.domain.main.MainWebViewDomain;
import com.siziksu.browser.domain.utils.UrlUtils;
import com.siziksu.browser.presenter.bookmarks.BookmarksPresenter;
import com.siziksu.browser.presenter.history.HistoryPresenter;
import com.siziksu.browser.presenter.launch.LaunchPresenter;
import com.siziksu.browser.presenter.main.BrowserPresenter;
import com.siziksu.browser.presenter.main.MainPresenter;
import com.siziksu.browser.presenter.main.WebViewPresenter;
import com.siziksu.browser.ui.view.bookmarks.BookmarksActivity;
import com.siziksu.browser.ui.view.history.HistoryActivity;
import com.siziksu.browser.ui.view.launch.LaunchActivity;
import com.siziksu.browser.ui.view.main.BrowserFragment;
import com.siziksu.browser.ui.view.main.MainActivity;
import com.siziksu.browser.ui.view.main.webView.MainWebView;
import com.siziksu.browser.ui.view.main.webView.clients.MainWebChromeClient;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {
                ApplicationModule.class,
                DataModule.class,
                DomainModule.class,
                PresenterModule.class,
                RoomModule.class
        }
)
public interface ApplicationComponent {

    void inject(App target);

    void inject(LaunchActivity target);

    void inject(LaunchPresenter target);

    void inject(LaunchDomain target);

    void inject(MainActivity target);

    void inject(MainPresenter target);

    void inject(MainDomain target);

    void inject(BrowserFragment target);

    void inject(BrowserPresenter target);

    void inject(BrowserDomain target);

    void inject(BrowserDatabase target);

    void inject(BookmarksActivity target);

    void inject(BookmarksPresenter target);

    void inject(BookmarksDomain target);

    void inject(Repository target);

    void inject(PreferencesClient target);

    void inject(UrlUtils target);

    void inject(MainWebView target);

    void inject(WebViewPresenter target);

    void inject(MainWebViewDomain target);

    void inject(MainWebChromeClient target);

    void inject(HistoryActivity target);

    void inject(HistoryPresenter target);

    void inject(HistoryDomain target);
}
