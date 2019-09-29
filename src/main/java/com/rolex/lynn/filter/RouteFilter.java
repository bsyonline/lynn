package com.rolex.lynn.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author rolex
 * @Since 29/09/2019
 */
@Order(100)
@WebFilter(urlPatterns = "/*")
@Slf4j
public class RouteFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("RouteFilter....");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
