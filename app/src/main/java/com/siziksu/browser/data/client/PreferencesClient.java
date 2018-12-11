package com.siziksu.browser.data.client;

import com.siziksu.browser.App;
import com.siziksu.browser.data.client.service.PreferencesService;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public final class PreferencesClient implements PreferencesClientContract {

    private static final String LAST_PAGE_VISITED_KEY = "last_page_visited";

    @Inject
    PreferencesService service;

    public PreferencesClient() {
        App.get().getApplicationComponent().inject(this);
    }

    @Override
    public Single<String> getLastPageVisited() {
        return Single.create(emitter -> {
            try {
                String string = service.useDefaultSharedPreferences().getValue(LAST_PAGE_VISITED_KEY, "");
                emitter.onSuccess(string);
            } catch (Exception exception) {
                emitter.onError(exception);
            }
        });
    }

    @Override
    public Completable setLastPageVisited(String url) {
        return Completable.create(emitter -> {
            String string = service.useDefaultSharedPreferences().getValue(LAST_PAGE_VISITED_KEY, "");
            if (string == null || !string.equals(url)) {
                service.useDefaultSharedPreferences().applyValue(LAST_PAGE_VISITED_KEY, url);
            }
            emitter.onComplete();
        });
    }
}
