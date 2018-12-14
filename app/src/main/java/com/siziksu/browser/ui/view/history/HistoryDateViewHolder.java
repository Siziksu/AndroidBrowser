package com.siziksu.browser.ui.view.history;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.siziksu.browser.R;

import butterknife.BindView;
import butterknife.ButterKnife;

final class HistoryDateViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.date)
    TextView date;

    HistoryDateViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
