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
import com.siziksu.browser.presenter.mapper.BookmarkMapper;
import com.siziksu.browser.presenter.model.Bookmark;
import com.siziksu.browser.ui.common.dialog.DialogYesNo;
import com.siziksu.browser.ui.common.manager.PermissionsManager;

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
        domain.register();
    }

    @Override
    public void onDestroy() {
        view = null;
        domain.unregister();
    }

    @Override
    public void getBookmarks(Consumer<List<Bookmark>> result) {
        if (domain == null) { return; }
        domain.getBookmarks(bookmarks -> {
            if (view == null) { return; }
            List<Bookmark> list = new ArrayList<>(new BookmarkMapper().map(bookmarks));
            CollectionsUtils.sortUsersByName(list);
            result.accept(list);
        });
    }

    @Override
    public void deleteBookmark(Bookmark bookmark, Action result) {
        if (domain == null) { return; }
        if (view == null) { return; }
        DialogYesNo fragment = new DialogYesNo();
        fragment.setAcceptCallback(() -> domain.deleteBookmark(new BookmarkMapper().unMap(bookmark), () -> {
                    if (view == null) { return; }
                    result.execute();
                })
        ).setMessage(view.getAppCompatActivity().getString(R.string.are_you_sure));
        fragment.show(view.getAppCompatActivity().getSupportFragmentManager(), Constants.YES_NO_DIALOG_FRAGMENT_TAG);
    }
}
