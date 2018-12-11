package com.siziksu.browser.domain.main;

import com.siziksu.browser.common.function.Consumer;
import com.siziksu.browser.domain.BaseDomainContract;

public interface MainDomainContract extends BaseDomainContract {

    void getLastPageVisited(Consumer<String> result);
}
