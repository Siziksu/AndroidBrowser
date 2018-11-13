package com.siziksu.browser.ui.view.bookmarks;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.siziksu.browser.R;
import com.siziksu.browser.common.function.Consumer;
import com.siziksu.browser.common.utils.UrlUtils;
import com.siziksu.browser.ui.common.model.Page;

import java.util.List;

class BookmarksAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements BookmarksAdapterContract {

    private Context context;
    private Consumer<Page> itemClick;
    private Consumer<Page> deleteClick;
    private LinearLayoutManager layoutManager;
    private BookmarksItemManagerContract manager;

    BookmarksAdapter(Context context, BookmarksItemManagerContract manager) {
        this.context = context;
        this.manager = manager;
    }

    public void init(Consumer<Page> itemClick, Consumer<Page> deleteClick) {
        this.itemClick = itemClick;
        this.deleteClick = deleteClick;
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bookmarks_item, parent, false);
        return new BookmarksViewHolder(
                view,
                item -> {
                    if (itemClick != null) {
                        itemClick.accept(manager.getItems().get(item));
                    }
                },
                delete -> {
                    if (deleteClick != null) {
                        deleteClick.accept(manager.getItems().get(delete));
                    }
                }
        );
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BookmarksViewHolder) {
            BookmarksViewHolder localHolder = (BookmarksViewHolder) holder;
            Page item = manager.getItems().get(position);
            localHolder.bookmarksTitle.setText(item.titleToShow);
            localHolder.bookmarksUrl.setText(UrlUtils.getUrlToShowForBookmarks(item.url));
        }
    }

    @Override
    public int getItemCount() {
        return manager.getItems().size();
    }

    @Override
    public LinearLayoutManager getLayoutManager() {
        return layoutManager;
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return this;
    }

    @Override
    public void addItems(List<Page> list) {
        manager.showItems(this, list);
    }

    @Override
    public void deleteItem(Page page) {
        manager.deleteItem(this, page);
    }
}
