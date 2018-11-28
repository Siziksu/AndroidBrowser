package com.siziksu.browser.presenter.bookmarks;

import android.app.Activity;

import com.siziksu.browser.App;
import com.siziksu.browser.R;
import com.siziksu.browser.common.Constants;
import com.siziksu.browser.common.function.Action;
import com.siziksu.browser.common.function.Consumer;
import com.siziksu.browser.common.utils.CollectionsUtils;
import com.siziksu.browser.domain.bookmarks.BookmarksDomainContract;
import com.siziksu.browser.presenter.BaseViewContract;
import com.siziksu.browser.presenter.mapper.PageMapper;
import com.siziksu.browser.ui.common.dialog.DialogYesNo;
import com.siziksu.browser.ui.common.manager.PermissionsManager;
import com.siziksu.browser.ui.common.model.Page;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public final class BookmarksPresenter implements BookmarksPresenterContract<BaseViewContract> {

    @Inject
    BookmarksDomainContract domain;

    private BaseViewContract view;

    public BookmarksPresenter() {
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
    public void getBookmarks(Consumer<List<Page>> result) {
        if (domain == null) { return; }
        domain.getBookmarks(bookmarks -> {
            if (view == null) { return; }
            List<Page> list = new ArrayList<>(new PageMapper().map(bookmarks));
            CollectionsUtils.sortUsersByName(list);
            result.accept(list);
        });
    }

    @Override
    public void deleteBookmark(Page page, Action result) {
        new DialogYesNo().setAcceptCallback(() -> {
            if (domain == null) { return; }
            domain.deleteBookmark(new PageMapper().unMap(page), () -> {
                if (view == null) { return; }
                result.execute();
            });
        }).setMessage(view.getAppCompatActivity().getString(R.string.are_you_sure))
                .show(view.getAppCompatActivity().getSupportFragmentManager(), Constants.YES_NO_DIALOG_FRAGMENT_TAG);
    }
}
