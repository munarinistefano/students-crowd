/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.primoprogetto.filters;

import com.primoprogetto.database.User;
import com.primoprogetto.database.interaction.User_Group;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class FileFilter implements Filter {
    
    String uri;
    int group_id;
    User user;
    HttpSession session;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        session = null;
        user = null;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;
        
        session = req.getSession();
        user = (User)session.getAttribute("user");
        
        if (request instanceof HttpServletRequest) {
            //url = req.getRequestURL().toString();
            uri = ((HttpServletRequest)request).getRequestURI();
        }
        
        System.err.println(uri);
        
        String group = uri.substring(35,37);
        
        NumberFormatException error = null;
        try {
            group_id = Integer.parseInt(group); //get group ID
        } catch (NumberFormatException e) {
            error = e;
        }
        
        System.err.println(group_id);
        
        boolean isPartOfAGroup = false;
        try {
            isPartOfAGroup = User_Group.isPartOfAGroup(user.getID(), group_id);
        } catch (SQLException ex) {
            Logger.getLogger(GroupFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (!isPartOfAGroup){
            resp.sendRedirect(req.getContextPath() + "/permissiondenied.html");
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
    }
    
}
