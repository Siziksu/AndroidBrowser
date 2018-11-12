package com.siziksu.browser.presenter.main;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import com.siziksu.browser.App;
import com.siziksu.browser.R;
import com.siziksu.browser.presenter.BasePresenterContract;
import com.siziksu.browser.presenter.BaseViewContract;
import com.siziksu.browser.ui.common.manager.PermissionsManager;
import com.siziksu.browser.ui.common.router.RouterContract;
import com.siziksu.browser.ui.view.main.BrowserFragment;

import javax.inject.Inject;

public final class MainPresenter implements BasePresenterContract<BaseViewContract> {

    @Inject
    RouterContract router;

    public MainPresenter() {
        App.get().getApplicationComponent().inject(this);
    }

    @Override
    public void onCreate(Activity activity) {
        if (activity == null) { return; }
        PermissionsManager.checkPermissions(activity);
        router.loadFragment((AppCompatActivity) activity, R.id.webContent, new BrowserFragment());
    }

    @Override
    public void onResume(BaseViewContract view) {}

    @Override
    public void onDestroy() {}
}
