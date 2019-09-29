package com.rolex.lynn.servlet;

/**
 * @author rolex
 * @Since 29/09/2019
 */

import com.rolex.lynn.context.RequestContext;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static sun.management.Agent.error;

@Slf4j
@WebFilter(urlPatterns = "/*")
public class LynnServlet implements Filter {
    
    ServletRunner servletRunner;
    protected static final ThreadLocal<? extends RequestContext> threadLocal = new ThreadLocal<RequestContext>() {
    };
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("LynnServlet.init()");
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
    
    @Override
    public void destroy() {
        log.info("LynnServlet.destroy()");
    }
    
    private void init(HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        servletRunner.init(servletRequest, servletResponse);
    }
    
    private void pre() {
        log.info("LynnServlet.init()");
        servletRunner.pre();
    }
    
    private void routing() {
        log.info("LynnServlet.routing()");
        servletRunner.routing();
    }
    
    private void post() {
        log.info("LynnServlet.post()");
        servletRunner.post();
    }
}
