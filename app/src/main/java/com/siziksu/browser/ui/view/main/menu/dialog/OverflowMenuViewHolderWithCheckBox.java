package com.siziksu.browser.ui.view.main.menu.dialog;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.siziksu.browser.R;
import com.siziksu.browser.common.function.Consumer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

class OverflowMenuViewHolderWithCheckBox extends RecyclerView.ViewHolder {

    @BindView(R.id.overflowMenuItem)
    View overflowMenuItem;
    @BindView(R.id.overflowMenuItemName)
    TextView overflowMenuItemName;
    @BindView(R.id.overflowMenuItemCheckBox)
    CheckBox overflowMenuItemCheckBox;

    private Consumer<Integer> listener;

    OverflowMenuViewHolderWithCheckBox(View view, Consumer<Integer> listener) {
        super(view);
        ButterKnife.bind(this, view);
        this.listener = listener;
    }

    @OnClick({R.id.overflowMenuItem, R.id.overflowMenuItemCheckBox})
    public void onClick() {
        if (listener != null) {
            listener.accept(getAdapterPosition());
        }
    }
}
