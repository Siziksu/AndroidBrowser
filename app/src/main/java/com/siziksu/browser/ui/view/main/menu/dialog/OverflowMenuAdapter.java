package com.siziksu.browser.ui.view.main.menu.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.siziksu.browser.R;
import com.siziksu.browser.common.function.Consumer;
import com.siziksu.browser.ui.common.model.OverflowMenuItem;

import java.util.List;

class OverflowMenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OverflowMenuAdapterContract {

    private Context context;
    private Consumer<Integer> listener;
    private LinearLayoutManager layoutManager;
    private OverflowMenuItemManagerContract manager;

    OverflowMenuAdapter(Context context, OverflowMenuItemManagerContract manager) {
        this.context = context;
        this.manager = manager;
    }

    public void init(Consumer<Integer> listener) {
        this.listener = listener;
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_fragment_menu_item, parent, false);
        return new OverflowMenuViewHolder(view, position -> {
            if (listener != null) {
                listener.accept(manager.getItems().get(position).id);
            }
        });
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof OverflowMenuViewHolder) {
            OverflowMenuViewHolder localHolder = (OverflowMenuViewHolder) holder;
            OverflowMenuItem item = manager.getItems().get(position);
            localHolder.overflowMenuItemName.setText(item.name);
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
    public void addItems(List<OverflowMenuItem> list) {
        manager.showItems(this, list);
    }
}
