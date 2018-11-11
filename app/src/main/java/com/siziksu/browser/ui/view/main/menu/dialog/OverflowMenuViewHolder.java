package com.siziksu.browser.ui.view.main.menu.dialog;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.siziksu.browser.R;
import com.siziksu.browser.common.function.Consumer;

import butterknife.BindView;
import butterknife.ButterKnife;

class OverflowMenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.overflowMenuItem)
    View overflowMenuItem;
    @BindView(R.id.overflowMenuItemName)
    TextView overflowMenuItemName;

    private Consumer<Integer> listener;

    OverflowMenuViewHolder(View view, Consumer<Integer> listener) {
        super(view);
        ButterKnife.bind(this, view);
        this.listener = listener;
        overflowMenuItem.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.accept(getAdapterPosition());
        }
    }
}
