/*
 * Copyright (C) 2019 bsyonline
 */
package com.rolex.lynn.filter;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author rolex
 * @since 2019
 */
public class FilterLoader {

    private final ConcurrentHashMap<String, List<GenericFilter>> filtersByType = new ConcurrentHashMap<>();

    private static FilterLoader INSTANCE = new FilterLoader();

    public static FilterLoader getInstance() {
        return INSTANCE;
    }

    public List<GenericFilter> getFiltersByType(String filterType) {
        return filtersByType.get(filterType);
    }

    public static void putFilter(String type, GenericFilter filter) {
        List<GenericFilter> list = INSTANCE.filtersByType.get(type);
        if (list == null) {
            List<GenericFilter> filters = new ArrayList<>();
            filters.add(filter);
            INSTANCE.filtersByType.put(type, filters);
        } else {
            INSTANCE.filtersByType.get(type).add(filter);
        }
    }
}
