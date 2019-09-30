package com.rolex.lynn.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;

import javax.servlet.annotation.WebFilter;

/**
 * @author rolex
 * @Since 29/09/2019
 */
@Order(1)
@WebFilter(urlPatterns = "/*")
@Slf4j
public class PreFilter extends GenericFilter{
    @Override
    public String filterType() {
        return "pre";
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
        log.info("PreFilter");
        return null;
    }
}
