package com.siziksu.browser.domain.bookmarks;

import com.siziksu.browser.App;
import com.siziksu.browser.common.DisposablesManager;
import com.siziksu.browser.common.function.Action;
import com.siziksu.browser.common.function.Consumer;
import com.siziksu.browser.data.RepositoryContract;
import com.siziksu.browser.domain.mapper.PageMapper;
import com.siziksu.browser.domain.model.PageDomain;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public final class BookmarksDomain implements BookmarksDomainContract {

    @Inject
    RepositoryContract repository;

    private DisposablesManager disposablesManager;

    public BookmarksDomain() {
        App.get().getApplicationComponent().inject(this);
    }

    @Override
    public void register() {
        disposablesManager = new DisposablesManager(2);
    }

    @Override
    public void unregister() {
        disposablesManager.dispose();
    }

    @Override
    public void getBookmarks(Consumer<List<PageDomain>> result) {
        if (repository == null) { return; }
        disposablesManager.add(0, repository.getBookmarks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bookmarks -> result.accept(new PageMapper().map(bookmarks)))
        );
    }

    @Override
    public void deleteBookmark(PageDomain bookmark, Action action) {
        if (repository == null) { return; }
        disposablesManager.add(1, repository.deleteBookmark(new PageMapper().unMap(bookmark))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action::execute)
        );
    }
}
