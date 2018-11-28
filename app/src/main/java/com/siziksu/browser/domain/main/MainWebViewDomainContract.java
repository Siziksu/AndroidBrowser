package com.siziksu.browser.domain.main;

import com.siziksu.browser.common.function.Consumer;

public interface MainWebViewDomainContract {

    void filterUrl(String url, Consumer<String> filteredUrl);
}
