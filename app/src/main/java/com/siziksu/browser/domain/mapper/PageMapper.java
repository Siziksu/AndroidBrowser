package com.siziksu.browser.domain.mapper;

import com.siziksu.browser.common.mapper.Mapper;
import com.siziksu.browser.data.model.PageData;
import com.siziksu.browser.domain.model.PageDomain;

public final class PageMapper extends Mapper<PageData, PageDomain> {

    @Override
    public PageDomain map(PageData unmapped) {
        PageDomain mapped = new PageDomain();
        mapped.titleToShow = unmapped.titleToShow;
        mapped.title = unmapped.title;
        mapped.url = unmapped.url;
        return mapped;
    }

    @Override
    public PageData unMap(PageDomain mapped) {
        PageData unmapped = new PageData();
        unmapped.titleToShow = mapped.titleToShow;
        unmapped.title = mapped.title;
        unmapped.url = mapped.url;
        return unmapped;
    }
}
