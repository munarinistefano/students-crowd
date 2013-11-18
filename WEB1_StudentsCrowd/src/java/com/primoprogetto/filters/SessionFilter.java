/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primoprogetto.filters;

import com.primoprogetto.database.User;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Stefano
 */
public class SessionFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;
        
        HttpSession session = req.getSession(false);
        /*if (session==null){
             resp.sendRedirect(req.getContextPath()+"/index.jsp"); //redirect to landing page
        }*/
        
        if ((User)session.getAttribute("user")==null){
            resp.sendRedirect(req.getContextPath()+"/index.jsp"); //redirect to landing page
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
    
    
}
