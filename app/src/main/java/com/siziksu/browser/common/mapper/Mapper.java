package com.siziksu.browser.common.mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @param <O> object
 * @param <M> mapped object
 */
public abstract class Mapper<O, M> implements BaseMapper<O, M> {

    @Override
    public List<M> map(List<O> unmappedList) {
        List<M> mappedList = new ArrayList<>();
        for (O unmapped : unmappedList) {
            mappedList.add(map(unmapped));
        }
        return mappedList;
    }

    @Override
    public List<O> unMap(List<M> mappedList) {
        List<O> unmappedList = new ArrayList<>();
        for (M mapped : mappedList) {
            unmappedList.add(unMap(mapped));
        }
        return unmappedList;
    }
}
