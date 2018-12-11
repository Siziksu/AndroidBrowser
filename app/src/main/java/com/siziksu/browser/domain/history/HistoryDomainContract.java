package com.siziksu.browser.domain.history;

import com.siziksu.browser.common.function.Action;
import com.siziksu.browser.common.function.Consumer;
import com.siziksu.browser.domain.BaseDomainContract;
import com.siziksu.browser.domain.model.BrowserActivityDomain;

import java.util.List;

public interface HistoryDomainContract extends BaseDomainContract {

    void getHistory(Consumer<List<BrowserActivityDomain>> onHistory);

    void deleteHistory(BrowserActivityDomain history, Action onDone);
}
