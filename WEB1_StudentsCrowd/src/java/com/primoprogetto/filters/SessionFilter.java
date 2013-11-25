/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primoprogetto.filters;

import com.primoprogetto.database.User;
import java.io.IOException;
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
    HttpSession session;
    User user;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        session = null;
        user = null;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;
        
        session = req.getSession(false);
        user = (User)session.getAttribute("user");
        
        if (user==null){
            resp.sendRedirect(req.getContextPath()+"/LogoutServlet"); //redirect to landing page
        } else {
            chain.doFilter(request, response);
        }
        
    }

    @Override
    public void destroy() {

    }
    
    
}
