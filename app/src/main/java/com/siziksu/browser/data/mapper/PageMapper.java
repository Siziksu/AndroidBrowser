package com.siziksu.browser.data.mapper;

import com.siziksu.browser.common.mapper.Mapper;
import com.siziksu.browser.data.model.PageData;
import com.siziksu.browser.data.persistence.model.Bookmark;

public final class PageMapper extends Mapper<Bookmark, PageData> {

    @Override
    public PageData map(Bookmark unmapped) {
        PageData mapped = new PageData();
        mapped.titleToShow = unmapped.titleToShow;
        mapped.title = unmapped.title;
        mapped.url = unmapped.url;
        return mapped;
    }

    @Override
    public Bookmark unMap(PageData mapped) {
        Bookmark unmapped = new Bookmark();
        unmapped.titleToShow = mapped.titleToShow;
        unmapped.title = mapped.title;
        unmapped.url = mapped.url;
        return unmapped;
    }
}
