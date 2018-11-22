package com.siziksu.browser.ui.view.launch;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.siziksu.browser.App;
import com.siziksu.browser.R;
import com.siziksu.browser.presenter.BaseViewContract;
import com.siziksu.browser.presenter.launch.LaunchPresenterContract;
import com.siziksu.browser.ui.common.utils.ActivityUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public final class LaunchActivity extends AppCompatActivity implements BaseViewContract {

    @BindView(R.id.urlEditText)
    EditText urlEditText;

    @Inject
    LaunchPresenterContract<BaseViewContract> presenter;

    private boolean alreadyStarted;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtils.setWhiteStatusBar(getWindow());
        App.get().getApplicationComponent().inject(this);
        setContentView(R.layout.activity_launch);
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
        if (!alreadyStarted) {
            alreadyStarted = true;
            presenter.init();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @OnClick(R.id.urlEditText)
    public void onClick() {
        presenter.onUrlEditTextClick(urlEditText);
    }

    private void initializeViews() {
        ButterKnife.bind(this);
    }

    @Override
    public AppCompatActivity getAppCompatActivity() {
        return this;
    }
}
