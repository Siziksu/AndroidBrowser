package com.siziksu.browser.presenter.mapper;

import com.siziksu.browser.common.mapper.Mapper;
import com.siziksu.browser.domain.model.BrowserActivityDomain;
import com.siziksu.browser.ui.common.model.BrowserActivity;

public final class BrowserActivityMapper extends Mapper<BrowserActivityDomain, BrowserActivity> {

    @Override
    public BrowserActivity map(BrowserActivityDomain unmapped) {
        BrowserActivity mapped = new BrowserActivity();
        mapped.title = unmapped.title;
        mapped.url = unmapped.url;
        mapped.date = unmapped.date;
        return mapped;
    }

    @Override
    public BrowserActivityDomain unMap(BrowserActivity mapped) {
        BrowserActivityDomain unmapped = new BrowserActivityDomain();
        unmapped.title = mapped.title;
        unmapped.url = mapped.url;
        unmapped.date = mapped.date;
        return unmapped;
    }
}
