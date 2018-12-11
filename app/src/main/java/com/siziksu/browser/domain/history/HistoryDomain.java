package com.siziksu.browser.domain.history;

import com.siziksu.browser.App;
import com.siziksu.browser.common.function.Action;
import com.siziksu.browser.common.function.Consumer;
import com.siziksu.browser.data.RepositoryContract;
import com.siziksu.browser.domain.manager.DisposablesManager;
import com.siziksu.browser.domain.mapper.BrowserActivityMapper;
import com.siziksu.browser.domain.model.BrowserActivityDomain;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public final class HistoryDomain implements HistoryDomainContract {

    @Inject
    RepositoryContract repository;
    @Inject
    DisposablesManager disposablesManager;

    public HistoryDomain() {
        App.get().getApplicationComponent().inject(this);
    }

    @Override
    public void register() {
        disposablesManager.setSize(2);
    }

    @Override
    public void unregister() {
        disposablesManager.dispose();
    }

    @Override
    public void getHistory(Consumer<List<BrowserActivityDomain>> onHistory) {
        if (repository == null) { return; }
        disposablesManager.add(0, repository.getHistory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(history -> onHistory.accept(new BrowserActivityMapper().map(history)))
        );
    }

    @Override
    public void deleteHistory(BrowserActivityDomain history, Action onDone) {
        if (repository == null) { return; }
        disposablesManager.add(1, repository.deleteHistoryItem(new BrowserActivityMapper().unMap(history))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onDone::execute)
        );
    }
}
