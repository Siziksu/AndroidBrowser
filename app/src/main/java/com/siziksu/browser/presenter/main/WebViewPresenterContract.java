package com.siziksu.browser.presenter.main;

import com.siziksu.browser.common.function.Consumer;

public interface WebViewPresenterContract {

    void filterUrl(String url, Consumer<String> filteredUrl);
}
