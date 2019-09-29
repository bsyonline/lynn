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
    
    public void pre(){
        log.info("FilterProcessor.pre()");
        try{
            runFilters("pre");
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void post(){
        log.info("FilterProcessor.post()");
    }
    
    public void routing(){
        log.info("FilterProcessor.routing()");
    }

    private void runFilters(String filterType) {
        List<GenericFilter> filters = FilterLoader.getInstance().getFiltersByType(filterType);
    }
}
