package com.siziksu.browser.presenter.history;

import com.siziksu.browser.presenter.BaseViewContract;
import com.siziksu.browser.ui.common.model.BrowserActivity;

import java.util.List;

public interface HistoryViewContract extends BaseViewContract {

    void showHistory(List<BrowserActivity> history);
}
