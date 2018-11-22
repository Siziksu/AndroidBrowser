package com.siziksu.browser.domain.launch;

import com.siziksu.browser.App;
import com.siziksu.browser.common.DisposablesManager;
import com.siziksu.browser.common.function.Consumer;
import com.siziksu.browser.common.utils.Print;
import com.siziksu.browser.data.RepositoryContract;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LaunchDomain implements LaunchDomainContract {

    @Inject
    RepositoryContract repository;

    private DisposablesManager disposablesManager;

    public LaunchDomain() {
        App.get().getApplicationComponent().inject(this);
    }

    @Override
    public void register() {
        disposablesManager = new DisposablesManager(1);
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
