package com.rolex.lynn.filter;

import lombok.extern.slf4j.Slf4j;

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
    }
    
    public void post(){
        log.info("FilterProcessor.post()");
    }
    
    public void routing(){
        log.info("FilterProcessor.routing()");
    }
    
}
