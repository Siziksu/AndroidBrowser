package com.siziksu.browser.ui.view.main.menu.dialog;

import com.siziksu.browser.ui.common.model.OverflowMenuItem;

import java.util.ArrayList;
import java.util.List;

final class OverflowMenuItemManager implements OverflowMenuItemManagerContract {

    private List<OverflowMenuItem> items = new ArrayList<>();

    OverflowMenuItemManager() {}

    @Override
    public void showItems(OverflowMenuAdapterContract adapter, List<OverflowMenuItem> list) {
        items.clear();
        items.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public List<OverflowMenuItem> getItems() {
        return items;
    }

    @Override
    public OverflowMenuItem getItem(int position) {
        return items.get(position);
    }
}
