package com.siziksu.browser.data.client;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface PreferencesClientContract {

    Single<String> getLastPageVisited();

    Completable setLastPageVisited(String url);
}
