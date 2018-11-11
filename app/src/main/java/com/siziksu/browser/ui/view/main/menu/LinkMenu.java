package com.siziksu.browser.ui.view.main.menu;

import android.support.v7.app.AppCompatActivity;

import com.siziksu.browser.common.Constants;
import com.siziksu.browser.common.function.Consumer;
import com.siziksu.browser.ui.view.main.menu.dialog.LinkMenuDialogFragment;

public class LinkMenu {

    private AppCompatActivity activity;
    private Consumer<Integer> listener;
    private boolean isCancelable;
    private int x;
    private int y;

    private LinkMenu(Builder builder) {
        this.activity = builder.activity;
        this.listener = builder.listener;
        this.isCancelable = builder.isCancelable;
    }

    public LinkMenu setTouchPosition(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public void show() {
        LinkMenuDialogFragment fragment = new LinkMenuDialogFragment();
        if (listener != null) {
            fragment.setListener(listener);
        }
        fragment.setCancelable(isCancelable);
        fragment.setTouchPosition(x, y);
        fragment.show(activity.getSupportFragmentManager(), Constants.OVERFLOW_MENU_DIALOG_FRAGMENT_TAG);
    }

    public static class Builder {

        private AppCompatActivity activity;
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

        public Builder setListener(Consumer<Integer> listener) {
            this.listener = listener;
            return this;
        }

        public LinkMenu create() {
            return new LinkMenu(this);
        }
    }
}
