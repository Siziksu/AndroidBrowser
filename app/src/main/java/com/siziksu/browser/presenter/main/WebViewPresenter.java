package com.siziksu.browser.presenter.main;

import com.siziksu.browser.App;
import com.siziksu.browser.common.Constants;
import com.siziksu.browser.domain.main.MainWebViewDomainContract;

import javax.inject.Inject;

public final class WebViewPresenter implements WebViewPresenterContract {

    @Inject
    MainWebViewDomainContract domain;

    private WebViewViewContract view;

    public WebViewPresenter() {
        App.get().getApplicationComponent().inject(this);
    }

    public void register(WebViewViewContract view) {
        this.view = view;
    }

    @Override
    public void filterUrl(String url) {
        if (domain == null) { return; }
        if (url.length() == 0) {
            url = Constants.URL_GOOGLE_SEARCH;
        }
        domain.filterUrl(url, filteredUrl -> {
            if (view != null) {
                view.onUrlFiltered(filteredUrl);
            }
        });
    }
}
