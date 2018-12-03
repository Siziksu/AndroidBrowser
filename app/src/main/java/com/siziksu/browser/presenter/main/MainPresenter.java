package com.siziksu.browser.presenter.main;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.siziksu.browser.App;
import com.siziksu.browser.R;
import com.siziksu.browser.domain.main.MainDomainContract;
import com.siziksu.browser.ui.common.router.RouterContract;
import com.siziksu.browser.ui.view.main.BrowserFragment;

import javax.inject.Inject;

public final class MainPresenter implements MainPresenterContract<MainViewContract> {

    @Inject
    RouterContract router;
    @Inject
    MainDomainContract domain;

    private MainViewContract view;

    public MainPresenter() {
        App.get().getApplicationComponent().inject(this);
    }

    @Override
    public void onCreate(Activity activity) {
        if (activity == null) { return; }
        router.loadFragment((AppCompatActivity) activity, R.id.webContent, new BrowserFragment());
    }

    @Override
    public void onResume(MainViewContract view) {
        this.view = view;
        if (domain != null) {
            domain.register();
        }
    }

    @Override
    public void onDestroy() {
        view = null;
        if (domain != null) {
            domain.unregister();
        }
    }

    @Override
    public void isLastPageVisitedStored() {
        if (domain == null) { return; }
        domain.getLastPageVisited(lastVisited -> {
            if (view != null) {
                view.inLastPageVisitedStored(!TextUtils.isEmpty(lastVisited));
            }
        });
    }
}
