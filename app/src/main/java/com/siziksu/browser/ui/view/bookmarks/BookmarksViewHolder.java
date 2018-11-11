package com.siziksu.browser.ui.view.bookmarks;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.siziksu.browser.R;
import com.siziksu.browser.common.function.Consumer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

class BookmarksViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.bookmarksTitle)
    TextView bookmarksTitle;
    @BindView(R.id.bookmarksUrl)
    TextView bookmarksUrl;

    private Consumer<Integer> itemClick;
    private Consumer<Integer> deleteClick;

    BookmarksViewHolder(View view, Consumer<Integer> itemClick, Consumer<Integer> deleteClick) {
        super(view);
        ButterKnife.bind(this, view);
        this.itemClick = itemClick;
        this.deleteClick = deleteClick;
    }

    @OnClick({R.id.bookmarksItem, R.id.bookmarksDelete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bookmarksItem:
                if (itemClick != null) {
                    itemClick.accept(getAdapterPosition());
                }
                break;
            case R.id.bookmarksDelete:
                if (deleteClick != null) {
                    deleteClick.accept(getAdapterPosition());
                }
                break;
            default:
                break;
        }
    }
}
