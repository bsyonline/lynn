package com.rolex.lynn.filter;

/**
 * @author rolex
 * @Since 29/09/2019
 */

import com.rolex.lynn.context.RequestContext;
import com.rolex.lynn.servlet.ServletRunner;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
public abstract class GenericFilter implements Filter {
    
    ServletRunner servletRunner;
    protected static final ThreadLocal<? extends RequestContext> threadLocal = new ThreadLocal<RequestContext>() {
    };
    
    abstract public String filterType();
    
    abstract public int filterOrder();
    
    abstract boolean shouldFilter();
    
    abstract Object run() throws Exception;
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("GenericFilter.init()");
        servletRunner = new ServletRunner();
    }
    
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("LynnServlet started");
        init((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse);
        try {
            pre();
        } catch (Exception e) {
            error(e);
            post();
            return;
        }
        try {
            routing();
        } catch (Exception e) {
            error(e);
            post();
            return;
        }
        try {
            post();
        } catch (Exception e) {
            error(e);
            return;
        } finally {
            threadLocal.remove();
        }
    }
    
    public FilterResult runFilter() throws Exception {
        FilterResult result = new FilterResult();
        if(shouldFilter()){
            Object res = run();
            result = new FilterResult(res, FilterResult.ExecutionStatus.SUCCESS);
        }
        return result;
    }
    
    @Override
    public void destroy() {
        log.info("LynnServlet.destroy()");
    }
    
    private void init(HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        servletRunner.init(servletRequest, servletResponse);
    }
    
    private void pre() {
        log.info("GenericFilter.init()");
        servletRunner.pre();
    }
    
    private void routing() {
        log.info("GenericFilter.routing()");
        servletRunner.routing();
    }
    
    private void post() {
        log.info("GenericFilter.post()");
        servletRunner.post();
    }
    
    private void error(Exception e) {
        log.error("GenericFilter.error::{}", e);
    }
}
