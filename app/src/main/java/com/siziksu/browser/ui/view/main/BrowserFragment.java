package com.siziksu.browser.ui.view.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;

import com.siziksu.browser.App;
import com.siziksu.browser.R;
import com.siziksu.browser.common.function.Consumer;
import com.siziksu.browser.presenter.BaseViewContract;
import com.siziksu.browser.presenter.main.BrowserPresenterContract;
import com.siziksu.browser.ui.common.model.WebViewBack;
import com.siziksu.browser.ui.view.main.webView.MainWebView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public final class BrowserFragment extends Fragment implements BaseViewContract, BrowserFragmentContract {

    private static final Integer MAX_SWIPE_REFRESH_PROGRESS_VALUE = 60;

    @BindView(R.id.webViewProgressBar)
    ProgressBar webViewProgressBar;
    @BindView(R.id.webSwipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.webView)
    MainWebView webView;

    @Inject
    BrowserPresenterContract<BaseViewContract> presenter;

    private boolean alreadyStarted;
    private BrowserActivityContract browserActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            browserActivity = (BrowserActivityContract) context;
            browserActivity.onAttach(this);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.get().getApplicationComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_browser, container, false);
        ButterKnife.bind(this, inflatedView);
        return inflatedView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initializeViews();
        presenter.onCreate(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume(this);
        if (!alreadyStarted) {
            initPresenter();
            alreadyStarted = true;
            if (getActivity() != null) {
                presenter.setIntentData(getActivity().getIntent().getData());
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void webViewCanGoBack(Consumer<WebViewBack> callback) {
        presenter.webViewCanGoBack(callback);
    }

    @Override
    public void onActionMoreClick() {
        presenter.onActionMoreClick(
                () -> browserActivity.onMenuShow(),
                () -> browserActivity.onMenuDismiss()
        );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        presenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public AppCompatActivity getAppCompatActivity() {
        return browserActivity.getActivity();
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        super.onCreateContextMenu(contextMenu, view, contextMenuInfo);
        presenter.onCreateContextMenu(contextMenu, view, contextMenuInfo);
    }

    private void initializeViews() {
        registerForContextMenu(webView);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(() -> presenter.onRefresh(() -> swipeRefreshLayout.setRefreshing(false)));
        browserActivity.getEditText().setOnEditorActionListener(
                (v, actionId, event) -> {
                    if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_SEARCH) {
                        browserActivity.keyboardGoClicked();
                        presenter.onKeyboardGoClick(browserActivity.getEditText().getText().toString());
                    }
                    return false;
                }
        );
    }

    private void initPresenter() {
        presenter.init(
                webView,
                browserActivity.getActionMoreView(),
                () -> browserActivity.finish(),
                () -> browserActivity.clearEditText()
        );
        presenter.setPageListeners(
                url -> {
                    browserActivity.getEditText().setText(url);
                    swipeRefreshLayout.setRefreshing(true);
                    webViewProgressBar.setVisibility(View.VISIBLE);
                },
                url -> {
                    browserActivity.getEditText().setText(url);
                    swipeRefreshLayout.setRefreshing(false);
                    webViewProgressBar.setVisibility(View.GONE);
                }
        );
        presenter.setProgressListener(
                progress -> {
                    webViewProgressBar.setProgress(progress);
                    if (progress >= MAX_SWIPE_REFRESH_PROGRESS_VALUE && swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
        );
    }
}
