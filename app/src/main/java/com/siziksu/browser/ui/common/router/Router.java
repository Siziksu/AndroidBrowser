package com.siziksu.browser.ui.common.router;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.siziksu.browser.R;
import com.siziksu.browser.common.Constants;
import com.siziksu.browser.ui.view.bookmarks.BookmarksActivity;

import javax.inject.Inject;

public final class Router implements RouterContract {

    @Inject
    RouterHelper routerHelper;

    public Router(RouterHelper routerHelper) {
        this.routerHelper = routerHelper;
    }

    @Override
    public void loadFragment(AppCompatActivity activity, int container, Fragment fragment) {
        activity.getSupportFragmentManager().beginTransaction().add(container, fragment).commit();
    }

    @Override
    public void goToBookmarks(AppCompatActivity activity) {
        routerHelper
                .forResult(Constants.REQUEST_CODE_BOOKMARKS)
                .animateTransition(R.anim.slide_enter_from_right, R.anim.slide_exit_to_left)
                .route(activity, BookmarksActivity.class);
    }
}