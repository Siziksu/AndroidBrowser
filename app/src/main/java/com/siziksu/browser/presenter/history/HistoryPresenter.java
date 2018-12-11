package com.siziksu.browser.presenter.history;

import android.app.Activity;

import com.siziksu.browser.App;
import com.siziksu.browser.R;
import com.siziksu.browser.common.Constants;
import com.siziksu.browser.common.function.Action;
import com.siziksu.browser.domain.history.HistoryDomainContract;
import com.siziksu.browser.presenter.mapper.BrowserActivityMapper;
import com.siziksu.browser.ui.common.dialog.DialogYesNo;
import com.siziksu.browser.ui.common.manager.PermissionsManager;
import com.siziksu.browser.ui.common.model.BrowserActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public final class HistoryPresenter implements HistoryPresenterContract<HistoryViewContract> {

    @Inject
    HistoryDomainContract domain;

    private HistoryViewContract view;

    public HistoryPresenter() {
        App.get().getApplicationComponent().inject(this);
    }

    @Override
    public void onCreate(Activity activity) {
        if (activity == null) { return; }
        PermissionsManager.checkPermissions(activity);
    }

    @Override
    public void onResume(HistoryViewContract view) {
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
    public void getHistory() {
        if (domain == null) { return; }
        domain.getHistory(history -> {
            if (view == null) { return; }
            List<BrowserActivity> list = new ArrayList<>(new BrowserActivityMapper().map(history));
            CollectionUtils.sortActivityByDate(list);
            view.showHistory(list);
        });

    }

    @Override
    public void deleteHistory(BrowserActivity activity, Action onDeleted) {
        new DialogYesNo().setAcceptCallback(() -> {
            if (domain == null) { return; }
            domain.deleteHistory(new BrowserActivityMapper().unMap(activity), () -> {
                if (view == null) { return; }
                onDeleted.execute();
            });
        }).setMessage(view.getAppCompatActivity().getString(R.string.are_you_sure_to_delete_this_item))
                .show(view.getAppCompatActivity().getSupportFragmentManager(), Constants.YES_NO_DIALOG_FRAGMENT_TAG);
    }
}
