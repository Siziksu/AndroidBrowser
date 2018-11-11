package com.siziksu.browser.domain.mapper;

import com.siziksu.browser.common.mapper.Mapper;
import com.siziksu.browser.data.model.BookmarkData;
import com.siziksu.browser.domain.model.BookmarkDomain;

public final class BookmarkMapper extends Mapper<BookmarkData, BookmarkDomain> {

    @Override
    public BookmarkDomain map(BookmarkData unmapped) {
        BookmarkDomain mapped = new BookmarkDomain();
        mapped.titleToShow = unmapped.titleToShow;
        mapped.title = unmapped.title;
        mapped.url = unmapped.url;
        return mapped;
    }

    @Override
    public BookmarkData unMap(BookmarkDomain mapped) {
        BookmarkData unmapped = new BookmarkData();
        unmapped.titleToShow = mapped.titleToShow;
        unmapped.title = mapped.title;
        unmapped.url = mapped.url;
        return unmapped;
    }
}
