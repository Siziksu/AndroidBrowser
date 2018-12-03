package com.siziksu.browser.domain.manager;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public final class DisposablesManager {

    private Disposable[] disposables;

    @Inject
    public DisposablesManager() {}

    public void setSize(int size) {
        disposables = new Disposable[size];
    }

    public void add(int index, Disposable disposable) {
        dispose(index);
        if (disposables != null && disposables[index] != null) {
            disposables[index] = disposable;
        }
    }

    public void dispose() {
        if (disposables != null) {
            for (Disposable disposable : disposables) {
                if (disposable != null) {
                    disposable.dispose();
                }
            }
            disposables = null;
        }
    }

    public void dispose(int index) {
        if (disposables != null && disposables[index] != null) {
            disposables[index].dispose();
            disposables[index] = null;
        }
    }
}
