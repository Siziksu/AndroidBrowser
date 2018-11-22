package com.siziksu.browser.domain.launch;

import com.siziksu.browser.common.function.Consumer;
import com.siziksu.browser.domain.BaseDomainContract;

public interface LaunchDomainContract extends BaseDomainContract {

    void clearLastPageVisited();

    void getLastPageVisited(Consumer<String> result);
}
