package com.siziksu.browser.ui.view.main.menu.dialog;

import com.siziksu.browser.presenter.model.OverflowMenuItem;

import java.util.ArrayList;
import java.util.List;

class OverflowMenuItemManager implements OverflowMenuItemManagerContract {

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
}
