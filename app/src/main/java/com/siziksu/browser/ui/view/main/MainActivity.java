package com.siziksu.browser.ui.view.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.siziksu.browser.App;
import com.siziksu.browser.R;
import com.siziksu.browser.presenter.BaseViewContract;
import com.siziksu.browser.presenter.main.MainPresenterContract;
import com.siziksu.browser.ui.common.utils.ActivityUtils;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public final class MainActivity extends AppCompatActivity implements BaseViewContract, BrowserActivityContract {

    @BindView(R.id.urlEditText)
    EditText urlEditText;
    @BindView(R.id.actionMore)
    ImageView actionMore;

    @Inject
    MainPresenterContract<BaseViewContract> presenter;

    private BrowserFragment fragment;
    private boolean isMenuShowing;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtils.setWhiteStatusBar(getWindow());
        App.get().getApplicationComponent().inject(this);
        setContentView(R.layout.activity_main);
        presenter.onCreate(this);
        initializeViews();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (fragment != null) {
            fragment.webViewCanGoBack(callback -> {
                if (!callback.webViewCanGoBack && callback.isExternalLink) {
                    presenter.clearLastPageVisited();
                    finish();
                } else if (!callback.webViewCanGoBack) {
                    goBack();
                } else {
                    presenter.clearLastPageVisited();
                }
            });
        } else {
            goBack();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public AppCompatActivity getActivity() {
        return this;
    }

    @Override
    public void onAttach(BrowserFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public EditText getEditTed() {
        return urlEditText;
    }

    @Override
    public View getActionMoreView() {
        return actionMore;
    }

    @Override
    public void onMenuShow() {
        isMenuShowing = true;
    }

    @Override
    public void onMenuDismiss() {
        isMenuShowing = false;
    }

    @Override
    public AppCompatActivity getAppCompatActivity() {
        return this;
    }

    @OnClick(R.id.actionMore)
    public void onActionMoreClick() {
        if (fragment != null) {
            fragment.onActionMoreClick();
        }
    }

    private void goBack() {
        presenter.clearLastPageVisited();
        super.onBackPressed();
    }

    private void initializeViews() {
        ButterKnife.bind(this);
        KeyboardVisibilityEvent.setEventListener(this, isOpen -> {
            if (!isOpen) {
                presenter.isLastPageVisitedStored(isLastPageVisitedStored -> {
                    if (!isMenuShowing && !isLastPageVisitedStored) { super.onBackPressed(); }
                });
            }
        });
    }
}
