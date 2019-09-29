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
@Order(200)
@WebFilter(urlPatterns = "/*")
@Slf4j
public class PostFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("PostFilter....");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
