package com.siziksu.browser.presenter.main;

public interface WebViewPresenterContract {

    void register(WebViewViewContract view);

    void filterUrl(String url);
}
