package com.siziksu.browser.domain.main;

import com.siziksu.browser.App;
import com.siziksu.browser.common.function.Consumer;
import com.siziksu.browser.domain.utils.UrlUtils;

import javax.inject.Inject;

public final class MainWebViewDomain implements MainWebViewDomainContract {

    @Inject
    UrlUtils urlUtils;

    public MainWebViewDomain() {
        App.get().getApplicationComponent().inject(this);
    }

    @Override
    public void filterUrl(String url, Consumer<String> filteredUrl) {
        urlUtils.filterUrl(url, filteredUrl);
    }
}
