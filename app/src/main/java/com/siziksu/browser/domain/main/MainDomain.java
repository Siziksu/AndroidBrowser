package com.siziksu.browser.domain.main;

import com.siziksu.browser.App;
import com.siziksu.browser.common.DisposablesManager;
import com.siziksu.browser.common.function.Consumer;
import com.siziksu.browser.common.utils.Print;
import com.siziksu.browser.data.RepositoryContract;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainDomain implements MainDomainContract {

    @Inject
    RepositoryContract repository;
    @Inject
    DisposablesManager disposablesManager;

    public MainDomain() {
        App.get().getApplicationComponent().inject(this);
    }

    @Override
    public void register() {
        disposablesManager.setSize(1);
    }

    @Override
    public void unregister() {
        disposablesManager.dispose();
    }

    @Override
    public void clearLastPageVisited() {
        if (repository == null) { return; }
        repository.clearLastPageVisited();
    }

    @Override
    public void getLastPageVisited(Consumer<String> result) {
        if (repository == null) { return; }
        disposablesManager.add(0, repository.getLastPageVisited()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result::accept,
                        throwable -> Print.error("Error getting the last visited page: " + throwable.getMessage(), throwable)
                )
        );
    }
}
