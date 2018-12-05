package com.siziksu.browser.ui.view.main.menu.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.siziksu.browser.R;
import com.siziksu.browser.common.function.Consumer;
import com.siziksu.browser.ui.common.model.OverflowMenuItem;

import java.lang.ref.WeakReference;
import java.util.List;

final class OverflowMenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OverflowMenuAdapterContract {

    private WeakReference<Context> context;
    private Consumer<Integer> listener;
    private LinearLayoutManager layoutManager;
    private OverflowMenuItemManagerContract manager;

    OverflowMenuAdapter(WeakReference<Context> context, OverflowMenuItemManagerContract manager) {
        this.context = context;
        this.manager = manager;
    }

    public void init(Consumer<Integer> listener) {
        this.listener = listener;
        layoutManager = new LinearLayoutManager(context.get(), LinearLayoutManager.VERTICAL, false);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case OverflowMenuItem.CHECKBOX:
                view = LayoutInflater.from(context.get()).inflate(R.layout.dialog_fragment_menu_item_checkbox, parent, false);
                return new OverflowMenuViewHolderWithCheckBox(view, position -> {
                    if (listener != null) {
                        listener.accept(manager.getItem(position).id);
                    }
                });
            case OverflowMenuItem.DEFAULT:
            default:
                view = LayoutInflater.from(context.get()).inflate(R.layout.dialog_fragment_menu_item, parent, false);
                return new OverflowMenuViewHolder(view, position -> {
                    if (listener != null) {
                        listener.accept(manager.getItem(position).id);
                    }
                });
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final OverflowMenuItem item = manager.getItem(position);
        switch (holder.getItemViewType()) {
            case OverflowMenuItem.CHECKBOX:
                OverflowMenuViewHolderWithCheckBox checkboxHolder = (OverflowMenuViewHolderWithCheckBox) holder;
                checkboxHolder.overflowMenuItemName.setText(item.name);
                checkboxHolder.overflowMenuItemCheckBox.setChecked(item.isChecked);
                checkboxHolder.overflowMenuItemCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> item.isChecked = isChecked);
                break;
            case OverflowMenuItem.DEFAULT:
            default:
                OverflowMenuViewHolder defaultHolder = (OverflowMenuViewHolder) holder;
                defaultHolder.overflowMenuItemName.setText(item.name);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return manager.getItem(position).type;
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
