package com.siziksu.browser.presenter.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;

import com.siziksu.browser.common.function.Action;
import com.siziksu.browser.presenter.BasePresenterContract;
import com.siziksu.browser.presenter.BaseViewContract;
import com.siziksu.browser.ui.view.main.webView.MainWebView;

public interface BrowserPresenterContract<V extends BaseViewContract> extends BasePresenterContract<V> {

    void init(MainWebView webView, View actionMoreView);

    void setIntentData(Bundle bundle);

    void onBackPressed();

    void onRefresh(Action stopRefreshing);

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void onActionMoreClick(Action onMenuShown, Action onMenuDismiss);

    void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo);

    void onKeyboardGoClick(String url);
}
