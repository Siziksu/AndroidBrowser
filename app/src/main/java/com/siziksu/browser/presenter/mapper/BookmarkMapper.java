package com.siziksu.browser.presenter.mapper;

import com.siziksu.browser.common.mapper.Mapper;
import com.siziksu.browser.domain.model.BookmarkDomain;
import com.siziksu.browser.presenter.model.Bookmark;

public final class BookmarkMapper extends Mapper<BookmarkDomain, Bookmark> {

    @Override
    public Bookmark map(BookmarkDomain unmapped) {
        Bookmark mapped = new Bookmark();
        mapped.titleToShow = unmapped.titleToShow;
        mapped.title = unmapped.title;
        mapped.url = unmapped.url;
        return mapped;
    }

    @Override
    public BookmarkDomain unMap(Bookmark mapped) {
        BookmarkDomain unmapped = new BookmarkDomain();
        unmapped.titleToShow = mapped.titleToShow;
        unmapped.title = mapped.title;
        unmapped.url = mapped.url;
        return unmapped;
    }
}
