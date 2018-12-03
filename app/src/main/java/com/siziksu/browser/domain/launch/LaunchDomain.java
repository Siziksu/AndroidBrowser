package com.siziksu.browser.domain.launch;

import com.siziksu.browser.App;
import com.siziksu.browser.common.utils.Print;
import com.siziksu.browser.data.RepositoryContract;
import com.siziksu.browser.domain.manager.DisposablesManager;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LaunchDomain implements LaunchDomainContract {

    @Inject
    RepositoryContract repository;
    @Inject
    DisposablesManager disposablesManager;

    public LaunchDomain() {
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
        disposablesManager.add(0, repository.clearLastPageVisited()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {}, throwable -> Print.error("Error clearing the last visited page: " + throwable.getMessage(), throwable))
        );
    }
}
