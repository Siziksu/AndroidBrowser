package com.siziksu.browser.ui.view.main.menu;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.siziksu.browser.common.Constants;
import com.siziksu.browser.common.function.Consumer;
import com.siziksu.browser.ui.common.model.OverflowMenuItem;
import com.siziksu.browser.ui.view.main.menu.dialog.OverflowMenuDialogFragment;

import java.util.List;

public class OverflowMenu {

    private AppCompatActivity activity;
    private View view;
    private List<OverflowMenuItem> items;
    private Consumer<Integer> listener;
    private boolean isCancelable;
    private boolean isHome;
    private boolean canGoForward;
    private boolean isBookmarked;

    private OverflowMenu(Builder builder) {
        this.activity = builder.activity;
        this.view = builder.view;
        this.items = builder.items;
        this.listener = builder.listener;
        this.isCancelable = builder.isCancelable;
    }

    public OverflowMenu setIsHome(boolean isHome) {
        this.isHome = isHome;
        return this;
    }

    public OverflowMenu setCanGoForward(boolean canGoForward) {
        this.canGoForward = canGoForward;
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
        fragment.setCancelable(isCancelable);
        fragment.setIsHome(isHome);
        fragment.setCanGoForward(canGoForward);
        fragment.setIsBookmarked(isBookmarked);
        fragment.show(activity.getSupportFragmentManager(), Constants.OVERFLOW_MENU_DIALOG_FRAGMENT_TAG);
    }

    public static class Builder {

        private AppCompatActivity activity;
        private View view;
        private List<OverflowMenuItem> items;
        private Consumer<Integer> listener;
        private boolean isCancelable;

        public Builder setActivity(AppCompatActivity activity) {
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
