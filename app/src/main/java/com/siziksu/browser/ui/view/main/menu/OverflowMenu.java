package com.siziksu.browser.ui.view.main.menu;

import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import com.siziksu.browser.common.Constants;
import com.siziksu.browser.common.function.Action;
import com.siziksu.browser.common.function.Consumer;
import com.siziksu.browser.ui.common.model.OverflowMenuItem;
import com.siziksu.browser.ui.view.main.menu.dialog.OverflowMenuDialogFragment;

import java.lang.ref.WeakReference;
import java.util.List;

public final class OverflowMenu {

    private WeakReference<AppCompatActivity> activity;
    private View view;
    private List<OverflowMenuItem> items;
    private Consumer<Integer> listener;
    private boolean isCancelable;
    private boolean canGoForward;
    private boolean isBookmarked;
    private Action dismissListener;
    private boolean canBeBookmarked;

    private OverflowMenu(Builder builder) {
        this.activity = builder.activity;
        this.view = builder.view;
        this.items = builder.items;
        this.listener = builder.listener;
        this.isCancelable = builder.isCancelable;
    }

    public OverflowMenu setOnDismissListener(Action dismissListener) {
        this.dismissListener = dismissListener;
        return this;
    }

    public OverflowMenu setCanGoForward(boolean canGoForward) {
        this.canGoForward = canGoForward;
        return this;
    }

    public OverflowMenu setUrlValidated(String url) {
        canBeBookmarked = !TextUtils.isEmpty(url);
        return this;
    }

    public OverflowMenu setIsBookmarked(boolean isBookmarked) {
        this.isBookmarked = isBookmarked;
        return this;
    }

    public void show() {
        OverflowMenuDialogFragment fragment = new OverflowMenuDialogFragment();
        if (view != null) {
            fragment.setSource(view);
            fragment.setItems(items);
        }
        if (listener != null) {
            fragment.setListener(listener);
        }
        if (dismissListener != null) {
            fragment.setOnDismissListener(dismissListener);
        }
        fragment.setCancelable(isCancelable);
        fragment.setCanGoForward(canGoForward);
        fragment.setIsBookmarked(isBookmarked);
        fragment.setCanBeBookmarked(canBeBookmarked);
        fragment.show(activity.get().getSupportFragmentManager(), Constants.OVERFLOW_MENU_DIALOG_FRAGMENT_TAG);
    }

    public static class Builder {

        private WeakReference<AppCompatActivity> activity;
        private View view;
        private List<OverflowMenuItem> items;
        private Consumer<Integer> listener;
        private boolean isCancelable;

        public Builder setActivity(WeakReference<AppCompatActivity> activity) {
            this.activity = activity;
            return this;
        }

        public Builder setCancelable(boolean isCancelable) {
            this.isCancelable = isCancelable;
            return this;
        }

        public Builder setSourceView(View view) {
            this.view = view;
            return this;
        }

        public Builder setItems(List<OverflowMenuItem> items) {
            this.items = items;
            return this;
        }

        public Builder setListener(Consumer<Integer> listener) {
            this.listener = listener;
            return this;
        }

        public OverflowMenu create() {
            return new OverflowMenu(this);
        }
    }
}
