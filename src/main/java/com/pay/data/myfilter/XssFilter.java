package com.pay.data.myfilter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
 * @createTime: 2018/1/4
 * @author: HingLo
 * @description: Xss攻击过滤器
 */
@WebFilter
@Slf4j
public class XssFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(new com.pay.data.myfilter.XssHttpServletRequestWrapperNew(
                (HttpServletRequest) request), response);
    }

    @Override
    public void destroy() {

    }
}
