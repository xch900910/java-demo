package com.example.day.core;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author: xiongchaohua
 * @Des : 登录过滤器
 * @create: 2020-07-06 11:25
 **/
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
