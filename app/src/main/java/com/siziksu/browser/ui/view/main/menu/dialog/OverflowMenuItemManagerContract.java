package com.siziksu.browser.ui.view.main.menu.dialog;

import com.siziksu.browser.presenter.model.OverflowMenuItem;

import java.util.List;

interface OverflowMenuItemManagerContract {

    void showItems(OverflowMenuAdapterContract adapter, List<OverflowMenuItem> list);

    List<OverflowMenuItem> getItems();
}
