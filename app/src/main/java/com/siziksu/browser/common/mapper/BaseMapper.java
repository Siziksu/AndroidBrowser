package com.siziksu.browser.common.mapper;

import java.util.List;

/**
 * @param <O> object
 * @param <M> mapped object
 */
public interface BaseMapper<O, M> {

    List<M> map(List<O> unmappedList);

    List<O> unMap(List<M> mappedList);

    M map(O unmapped);

    O unMap(M mapped);
}

