package com.siziksu.browser.dagger.module;

import android.content.Context;

import com.siziksu.browser.App;
import com.siziksu.browser.ui.common.router.Router;
import com.siziksu.browser.ui.common.router.RouterContract;
import com.siziksu.browser.ui.common.router.RouterHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public final class ApplicationModule {

    private final App application;

    public ApplicationModule(App application) {
        this.application = application;
    }

    @Provides
    Context providesContext() {
        return application.getApplicationContext();
    }

    @Singleton
    @Provides
    RouterContract providesRouter(RouterHelper routerHelper) {
        return new Router(routerHelper);
    }
}
