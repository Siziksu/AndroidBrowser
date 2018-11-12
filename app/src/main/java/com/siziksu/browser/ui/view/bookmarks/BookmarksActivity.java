package com.siziksu.browser.ui.view.bookmarks;

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
import com.siziksu.browser.presenter.BaseViewContract;
import com.siziksu.browser.presenter.bookmarks.BookmarksPresenterContract;
import com.siziksu.browser.ui.common.utils.ActivityUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public final class BookmarksActivity extends AppCompatActivity implements BaseViewContract {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.bookmarksRecycler)
    RecyclerView recyclerView;

    @Inject
    BookmarksPresenterContract<BaseViewContract> presenter;

    private boolean alreadyStarted;
    private BookmarksAdapterContract adapter;

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
            presenter.getBookmarks(bookmarks -> adapter.addItems(bookmarks));
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
        setTitle(Constants.TITLE_ACTIVITY_BOOKMARKS);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
        adapter = new BookmarksAdapter(this, new BookmarksItemManager());
        adapter.init(
                item -> {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra(Constants.EXTRA_KEY_BOOKMARK, item);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                    finishAnimation();
                },
                bookmark -> presenter.deleteBookmark(bookmark, () -> adapter.deleteItem(bookmark))
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
