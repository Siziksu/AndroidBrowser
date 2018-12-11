package com.siziksu.browser.ui.view.history;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.siziksu.browser.App;
import com.siziksu.browser.R;
import com.siziksu.browser.common.Constants;
import com.siziksu.browser.presenter.history.HistoryPresenterContract;
import com.siziksu.browser.presenter.history.HistoryViewContract;
import com.siziksu.browser.ui.common.model.BrowserActivity;
import com.siziksu.browser.ui.common.utils.ActivityUtils;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public final class HistoryActivity extends AppCompatActivity implements HistoryViewContract {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.bookmarksRecycler)
    RecyclerView recyclerView;

    @Inject
    HistoryPresenterContract<HistoryViewContract> presenter;

    private boolean alreadyStarted;
    private HistoryAdapterContract adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtils.setWhiteStatusBar(getWindow());
        setContentView(R.layout.activity_bookmarks);
        App.get().getApplicationComponent().inject(this);
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
            presenter.getHistory();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAnimation();
    }

    @Override
    public void showHistory(List<BrowserActivity> history) {
        if (adapter != null) {
            adapter.showHistory(history);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public AppCompatActivity getAppCompatActivity() {
        return this;
    }

    private void initializeViews() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setTitle(Constants.TITLE_ACTIVITY_HISTORY);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
        adapter = new HistoryAdapter(new WeakReference<>(this), new HistoryItemManager());
        adapter.init(
                item -> {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra(Constants.EXTRA_KEY_HISTORY, item);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                    finishAnimation();
                },
                activity -> presenter.deleteHistory(activity, () -> adapter.deleteItem(activity))
        );
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter.getAdapter());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(adapter.getLayoutManager());
    }

    private void finishAnimation() {
        overridePendingTransition(R.anim.slide_enter_from_left, R.anim.slide_exit_to_right);
    }
}
