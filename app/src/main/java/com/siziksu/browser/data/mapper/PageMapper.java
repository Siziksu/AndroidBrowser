package com.siziksu.browser.data.mapper;

import com.siziksu.browser.common.mapper.Mapper;
import com.siziksu.browser.data.client.model.PageClient;
import com.siziksu.browser.data.model.PageData;

public final class PageMapper extends Mapper<PageClient, PageData> {

    @Override
    public PageData map(PageClient unmapped) {
        PageData mapped = new PageData();
        mapped.titleToShow = unmapped.titleToShow;
        mapped.title = unmapped.title;
        mapped.url = unmapped.url;
        return mapped;
    }

    @Override
    public PageClient unMap(PageData mapped) {
        PageClient unmapped = new PageClient();
        unmapped.titleToShow = mapped.titleToShow;
        unmapped.title = mapped.title;
        unmapped.url = mapped.url;
        return unmapped;
    }
}
