package com.pay.data.myfilter;

import lombok.extern.java.Log;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @createTime: 2018/1/4
 * @author: HingLo
 * @description: 字符编码过滤器
 */
@WebFilter
@Log
public class EncodingFilter implements Filter {
    private final String encoding = "utf-8";

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        httpServletResponse.setHeader("Access-Control-Expose-Headers", "Authorization");
        filterChain.doFilter(servletRequest, httpServletResponse);

    }

    @Override
    public void destroy() {

    }
}
