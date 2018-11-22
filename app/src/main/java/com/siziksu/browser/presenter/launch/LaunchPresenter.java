package com.siziksu.browser.presenter.launch;

import android.app.Activity;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.widget.EditText;

import com.siziksu.browser.App;
import com.siziksu.browser.domain.launch.LaunchDomainContract;
import com.siziksu.browser.presenter.BaseViewContract;
import com.siziksu.browser.ui.common.manager.PermissionsManager;
import com.siziksu.browser.ui.common.router.RouterContract;

import javax.inject.Inject;

public final class LaunchPresenter implements LaunchPresenterContract<BaseViewContract> {

    @Inject
    RouterContract router;
    @Inject
    LaunchDomainContract domain;

    private BaseViewContract view;

    public LaunchPresenter() {
        App.get().getApplicationComponent().inject(this);
    }

    @Override
    public void onCreate(Activity activity) {
        if (activity == null) { return; }
        PermissionsManager.checkPermissions(activity);
    }

    @Override
    public void onResume(BaseViewContract view) {
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
    public void init() {
        if (domain == null) { return; }
        domain.getLastPageVisited(lastVisited -> {
            if (view == null) { return; }
            if (!TextUtils.isEmpty(lastVisited)) {
                router.goToMainActivity(view.getAppCompatActivity());
            } else {
                domain.clearLastPageVisited();
            }
        });
    }

    @Override
    public void onUrlEditTextClick(EditText urlEditText) {
        if (view == null) { return; }
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(view.getAppCompatActivity(), urlEditText, ViewCompat.getTransitionName(urlEditText));
        router.goToMainActivity(view.getAppCompatActivity(), options.toBundle());
    }
}
