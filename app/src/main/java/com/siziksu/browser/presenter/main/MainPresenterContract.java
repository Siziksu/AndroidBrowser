package com.siziksu.browser.presenter.main;

import com.siziksu.browser.common.function.Consumer;
import com.siziksu.browser.presenter.BasePresenterContract;
import com.siziksu.browser.presenter.BaseViewContract;

public interface MainPresenterContract<V extends BaseViewContract> extends BasePresenterContract<V> {

    void isLastPageVisitedStored(Consumer<Boolean> result);
}
