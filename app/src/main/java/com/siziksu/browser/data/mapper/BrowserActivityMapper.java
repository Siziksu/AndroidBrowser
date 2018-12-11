package com.siziksu.browser.data.mapper;

import com.siziksu.browser.common.mapper.Mapper;
import com.siziksu.browser.data.model.BrowserActivityData;
import com.siziksu.browser.data.persistence.model.History;

public final class BrowserActivityMapper extends Mapper<History, BrowserActivityData> {

    @Override
    public BrowserActivityData map(History unmapped) {
        BrowserActivityData mapped = new BrowserActivityData();
        mapped.url = unmapped.url;
        mapped.title = unmapped.title;
        mapped.date = unmapped.date;
        return mapped;
    }

    @Override
    public History unMap(BrowserActivityData mapped) {
        History unmapped = new History();
        unmapped.url = mapped.url;
        unmapped.title = mapped.title;
        unmapped.date = mapped.date;
        return unmapped;
    }
}
