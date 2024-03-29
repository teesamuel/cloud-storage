package com.udacity.jwdnd.course1.cloudstorage.controller.tutorial;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(filterName="helloFilter", urlPatterns="/helloServlet2")
public class HelloFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        System.out.println("Executing doFilter method");
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("Done executing doFilter method");
    }
}
