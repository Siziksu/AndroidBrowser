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
import com.siziksu.browser.presenter.main.MainPresenterContract;
import com.siziksu.browser.presenter.main.MainViewContract;
import com.siziksu.browser.ui.common.utils.ActivityUtils;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public final class MainActivity extends AppCompatActivity implements MainViewContract, BrowserActivityContract {

    @BindView(R.id.urlEditText)
    EditText urlEditText;
    @BindView(R.id.actionMore)
    ImageView actionMore;

    @Inject
    MainPresenterContract<MainViewContract> presenter;

    private BrowserFragment fragment;
    private boolean isMenuShowing;
    private boolean isKeyboardGoClicked;

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
            fragment.onBackPressed();
        } else {
            super.onBackPressed();
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
    public void clearEditText() {
        urlEditText.setText("");
        urlEditText.requestFocus();
    }

    @Override
    public EditText getEditText() {
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
    public void keyboardGoClicked() {
        isKeyboardGoClicked = true;
    }

    @Override
    public void inLastPageVisitedStored(boolean isLastPageVisitedStored) {
        if (!isMenuShowing && !isLastPageVisitedStored) {
            super.onBackPressed();
        }
    }

    @Override
    public void superOnBackPressed() {
        super.onBackPressed();
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

    private void initializeViews() {
        ButterKnife.bind(this);
        KeyboardVisibilityEvent.setEventListener(this, keyboardIsVisible -> {
            if (!isKeyboardGoClicked) {
                if (!keyboardIsVisible) {
                    presenter.isLastPageVisitedStored();
                }
            }
        });
    }
}
