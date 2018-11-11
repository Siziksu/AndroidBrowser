package com.siziksu.browser.data.mapper;

import com.siziksu.browser.common.mapper.Mapper;
import com.siziksu.browser.data.client.model.BookmarkClient;
import com.siziksu.browser.data.model.BookmarkData;

public final class BookmarkMapper extends Mapper<BookmarkClient, BookmarkData> {

    @Override
    public BookmarkData map(BookmarkClient unmapped) {
        BookmarkData mapped = new BookmarkData();
        mapped.titleToShow = unmapped.titleToShow;
        mapped.title = unmapped.title;
        mapped.url = unmapped.url;
        return mapped;
    }

    @Override
    public BookmarkClient unMap(BookmarkData mapped) {
        BookmarkClient unmapped = new BookmarkClient();
        unmapped.titleToShow = mapped.titleToShow;
        unmapped.title = mapped.title;
        unmapped.url = mapped.url;
        return unmapped;
    }
}
