package com.siziksu.browser.presenter.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;

import com.siziksu.browser.common.function.Action;
import com.siziksu.browser.common.function.Consumer;
import com.siziksu.browser.presenter.BasePresenterContract;
import com.siziksu.browser.presenter.BaseViewContract;
import com.siziksu.browser.ui.common.model.WebViewBack;
import com.siziksu.browser.ui.view.main.webView.MainWebView;

public interface BrowserPresenterContract<V extends BaseViewContract> extends BasePresenterContract<V> {

    void init(MainWebView webView, View actionMoreView, Action onHomeClickListener, Action clearTextListener);

    void setPageListeners(Consumer<String> onPageStarted, Consumer<String> onPageFinished);

    void setProgressListener(Consumer<Integer> progress);

    void setIntentData(Bundle bundle);

    void webViewCanGoBack(Consumer<WebViewBack> callback);

    void onRefresh(Action stopRefreshing);

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void onActionMoreClick(Action onMenuShown, Action onMenuDissmiss);

    void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo);

    void onKeyboardGoClick(String url);
}
