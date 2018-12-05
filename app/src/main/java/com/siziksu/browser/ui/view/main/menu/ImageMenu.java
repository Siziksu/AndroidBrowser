package com.siziksu.browser.ui.view.main.menu;

import android.support.v7.app.AppCompatActivity;

import com.siziksu.browser.common.Constants;
import com.siziksu.browser.common.function.Consumer;
import com.siziksu.browser.ui.view.main.menu.dialog.ImageMenuDialogFragment;

import java.lang.ref.WeakReference;

public final class ImageMenu {

    private WeakReference<AppCompatActivity> activity;
    private Consumer<Integer> listener;
    private boolean isCancelable;
    private int x;
    private int y;

    private ImageMenu(Builder builder) {
        this.activity = builder.activity;
        this.listener = builder.listener;
        this.isCancelable = builder.isCancelable;
    }

    public ImageMenu setTouchPosition(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public void show() {
        ImageMenuDialogFragment fragment = new ImageMenuDialogFragment();
        if (listener != null) {
            fragment.setListener(listener);
        }
        fragment.setCancelable(isCancelable);
        fragment.setTouchPosition(x, y);
        fragment.show(activity.get().getSupportFragmentManager(), Constants.OVERFLOW_MENU_DIALOG_FRAGMENT_TAG);
    }

    public static class Builder {

        private WeakReference<AppCompatActivity> activity;
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

        public Builder setListener(Consumer<Integer> listener) {
            this.listener = listener;
            return this;
        }

        public ImageMenu create() {
            return new ImageMenu(this);
        }
    }
}
