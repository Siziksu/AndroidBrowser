package com.siziksu.browser.domain.launch;

import com.siziksu.browser.App;
import com.siziksu.browser.data.RepositoryContract;

import javax.inject.Inject;

public class LaunchDomain implements LaunchDomainContract {

    @Inject
    RepositoryContract repository;

    public LaunchDomain() {
        App.get().getApplicationComponent().inject(this);
    }

    @Override
    public void register() {}

    @Override
    public void unregister() {}

    @Override
    public void clearLastPageVisited() {
        if (repository == null) { return; }
        repository.clearLastPageVisited();
    }
}
