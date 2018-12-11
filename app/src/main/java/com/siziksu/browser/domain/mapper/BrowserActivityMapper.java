package com.siziksu.browser.domain.mapper;

import com.siziksu.browser.common.mapper.Mapper;
import com.siziksu.browser.data.model.BrowserActivityData;
import com.siziksu.browser.domain.model.BrowserActivityDomain;

public final class BrowserActivityMapper extends Mapper<BrowserActivityData, BrowserActivityDomain> {

    @Override
    public BrowserActivityDomain map(BrowserActivityData unmapped) {
        BrowserActivityDomain mapped = new BrowserActivityDomain();
        mapped.title = unmapped.title;
        mapped.url = unmapped.url;
        mapped.date = unmapped.date;
        return mapped;
    }

    @Override
    public BrowserActivityData unMap(BrowserActivityDomain mapped) {
        BrowserActivityData unmapped = new BrowserActivityData();
        unmapped.title = mapped.title;
        unmapped.url = mapped.url;
        unmapped.date = mapped.date;
        return unmapped;
    }
}
