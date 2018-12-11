package com.siziksu.browser.presenter.history;

import com.siziksu.browser.common.function.Action;
import com.siziksu.browser.presenter.BasePresenterContract;
import com.siziksu.browser.presenter.BaseViewContract;
import com.siziksu.browser.ui.common.model.BrowserActivity;

public interface HistoryPresenterContract<V extends BaseViewContract> extends BasePresenterContract<V> {

    void getHistory();

    void deleteHistory(BrowserActivity activity, Action onDeleted);
}
