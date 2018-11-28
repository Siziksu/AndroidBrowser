package com.siziksu.browser.ui.view.main.menu.dialog;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.siziksu.browser.R;
import com.siziksu.browser.common.function.Consumer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

final class OverflowMenuViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.overflowMenuItem)
    View overflowMenuItem;
    @BindView(R.id.overflowMenuItemName)
    TextView overflowMenuItemName;

    private Consumer<Integer> listener;

    OverflowMenuViewHolder(View view, Consumer<Integer> listener) {
        super(view);
        ButterKnife.bind(this, view);
        this.listener = listener;
    }

    @OnClick(R.id.overflowMenuItem)
    public void onClick() {
        if (listener != null) {
            listener.accept(getAdapterPosition());
        }
    }
}
