package com.rolex.lynn.servlet;

import com.rolex.lynn.filter.FilterProcessor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author rolex
 * @Since 29/09/2019
 */
@Slf4j
public class ServletRunner {
    
    
    public void init(HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        log.info("ServletRunner.init()");
    }
    
    public void pre() {
        log.info("ServletRunner.pre()");
        FilterProcessor.getInstance().pre();
    }
    
    public void routing() {
        log.info("ServletRunner.routing()");
        FilterProcessor.getInstance().routing();
    }
    
    public void post() {
        log.info("ServletRunner.post()");
        FilterProcessor.getInstance().post();
    }
    
    
}
