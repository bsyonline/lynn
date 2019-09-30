package com.rolex.lynn.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.annotation.WebFilter;

/**
 * @author rolex
 * @Since 30/09/2019
 */
@Slf4j
@WebFilter(urlPatterns = "/*")
public class ResponseResultFilter extends GenericFilter {
    
    @Override
    public String filterType() {
        return "post";
    }
    
    @Override
    public int filterOrder() {
        return 0;
    }
    
    @Override
    boolean shouldFilter() {
        return true;
    }
    
    @Override
    Object run() throws Exception {
        log.info("ResponseResultFilter.......................");
        return null;
    }
}
