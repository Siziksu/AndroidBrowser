package com.siziksu.browser.dagger.module;

import android.content.Context;

import com.siziksu.browser.data.Repository;
import com.siziksu.browser.data.RepositoryContract;
import com.siziksu.browser.data.client.PreferencesClient;
import com.siziksu.browser.data.client.PreferencesClientContract;
import com.siziksu.browser.data.client.service.PreferencesService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public final class DataModule {

    @Provides
    PreferencesService providesPreferencesService(Context context) {
        return new PreferencesService(context);
    }

    @Singleton
    @Provides
    PreferencesClientContract providesPreferencesClient() {
        return new PreferencesClient();
    }

    @Singleton
    @Provides
    RepositoryContract providesRepository() {
        return new Repository();
    }
}
