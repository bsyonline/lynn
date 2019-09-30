package com.rolex.lynn.filter;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author rolex
 * @Since 29/09/2019
 */
@Slf4j
public class FilterProcessor {
    
    static FilterProcessor INSTANCE = new FilterProcessor();
    
    public static FilterProcessor getInstance() {
        return INSTANCE;
    }
    
    public void pre() {
        log.info("FilterProcessor.pre()");
        try {
            runFilters("pre");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public void post() {
        log.info("FilterProcessor.post()");
        try {
            runFilters("post");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public void routing() {
        runFilters("route");
        log.info("FilterProcessor.routing()");
    }
    
    private void runFilters(String filterType) {
        List<GenericFilter> filters = FilterLoader.getInstance().getFiltersByType(filterType);
        for (GenericFilter filter : filters) {
            processFilter(filter);
        }
    }
    
    private Object processFilter(GenericFilter filter) {
        String filterName = "";
        Throwable t = null;
        Object o = null;
        try {
            filterName = filter.getClass().getSimpleName();
            FilterResult result = filter.runFilter();
            switch (result.getStatus()) {
                case FAILED:
                    t = result.getException();
                    log.warn("{} has an exception::{}", filterName, t);
                    break;
                case SUCCESS:
                    o = result.getResult();
                    break;
                default:
                    break;
            }
            if (t != null) {
                throw t;
            }
            return o;
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }
}
