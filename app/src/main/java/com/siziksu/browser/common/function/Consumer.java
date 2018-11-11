package com.siziksu.browser.common.function;

@FunctionalInterface
public interface Consumer<T> {

    void accept(T t);
}
