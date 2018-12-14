package com.siziksu.browser.ui.view.history;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.siziksu.browser.R;
import com.siziksu.browser.common.function.Consumer;
import com.siziksu.browser.ui.common.model.BrowserActivity;

import java.lang.ref.WeakReference;
import java.util.List;

final class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements HistoryAdapterContract {

    private WeakReference<Context> context;
    private Consumer<BrowserActivity> itemClick;
    private Consumer<BrowserActivity> deleteClick;
    private LinearLayoutManager layoutManager;
    private HistoryItemManagerContract manager;

    HistoryAdapter(WeakReference<Context> context, HistoryItemManagerContract manager) {
        this.context = context;
        this.manager = manager;
    }

    public void init(Consumer<BrowserActivity> itemClick, Consumer<BrowserActivity> deleteClick) {
        this.itemClick = itemClick;
        this.deleteClick = deleteClick;
        layoutManager = new LinearLayoutManager(context.get(), LinearLayoutManager.VERTICAL, false);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == BrowserActivity.TYPE_DATE) {
            view = LayoutInflater.from(context.get()).inflate(R.layout.history_date_item, parent, false);
            return new HistoryDateViewHolder(view);
        } else {
            view = LayoutInflater.from(context.get()).inflate(R.layout.history_item, parent, false);
            return new HistoryViewHolder(
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
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BrowserActivity item = manager.getItems().get(position);
        if (holder.getItemViewType() == BrowserActivity.TYPE_DATE) {
            HistoryDateViewHolder localHolder = (HistoryDateViewHolder) holder;
            localHolder.date.setText(item.getDateFormatted());
        } else {
            HistoryViewHolder localHolder = (HistoryViewHolder) holder;
            localHolder.bookmarksTitle.setText(item.title);
            localHolder.bookmarksUrl.setText(item.url);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return manager.getItemType(position);
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
    public void showHistory(List<BrowserActivity> list) {
        manager.showItems(this, list);
    }

    @Override
    public void deleteItem(BrowserActivity activity) {
        manager.deleteItem(this, activity);
    }
}
