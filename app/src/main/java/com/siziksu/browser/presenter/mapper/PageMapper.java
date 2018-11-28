package com.siziksu.browser.presenter.mapper;

import com.siziksu.browser.common.mapper.Mapper;
import com.siziksu.browser.domain.model.PageDomain;
import com.siziksu.browser.ui.common.model.Page;

public final class PageMapper extends Mapper<PageDomain, Page> {

    @Override
    public Page map(PageDomain unmapped) {
        Page mapped = new Page();
        mapped.titleToShow = unmapped.titleToShow;
        mapped.title = unmapped.title;
        mapped.url = unmapped.url;
        return mapped;
    }

    @Override
    public PageDomain unMap(Page mapped) {
        PageDomain unmapped = new PageDomain();
        unmapped.titleToShow = mapped.titleToShow;
        unmapped.title = mapped.title;
        unmapped.url = mapped.url;
        return unmapped;
    }
}
