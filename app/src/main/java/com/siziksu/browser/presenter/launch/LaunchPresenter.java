package com.siziksu.browser.presenter.launch;

import android.app.Activity;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.widget.EditText;

import com.siziksu.browser.App;
import com.siziksu.browser.common.utils.SystemUtils;
import com.siziksu.browser.domain.launch.LaunchDomainContract;
import com.siziksu.browser.ui.common.manager.PermissionsManager;
import com.siziksu.browser.ui.common.router.RouterContract;

import javax.inject.Inject;

public final class LaunchPresenter implements LaunchPresenterContract<LaunchViewContract> {

    @Inject
    RouterContract router;
    @Inject
    LaunchDomainContract domain;

    private LaunchViewContract view;

    public LaunchPresenter() {
        App.get().getApplicationComponent().inject(this);
    }

    @Override
    public void onCreate(Activity activity) {
        if (activity == null) { return; }
        PermissionsManager.checkPermissions(activity);
    }

    @Override
    public void onResume(LaunchViewContract view) {
        this.view = view;
        view.setVersionText(SystemUtils.getVersionCode(view.getAppCompatActivity()));
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
    public void onUrlEditTextClick(EditText urlEditText) {
        if (view == null) { return; }
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(view.getAppCompatActivity(), urlEditText, ViewCompat.getTransitionName(urlEditText));
        router.goToMainActivity(view.getAppCompatActivity(), options.toBundle());
    }

    @Override
    public void clearLastPageVisited() {
        if (domain == null) { return; }
        domain.clearLastPageVisited();
    }
}