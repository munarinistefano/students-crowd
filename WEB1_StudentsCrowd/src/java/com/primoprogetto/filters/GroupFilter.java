/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primoprogetto.filters;

import com.primoprogetto.database.User;
import com.primoprogetto.database.interaction.User_Group;
import java.io.IOException;
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
public class GroupFilter implements Filter {
    
    String queryString = null;
    int user_id,group_id;
    User user;
    HttpSession session;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        user = null;
        session = null;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;
        
        session = req.getSession();
        user = (User)session.getAttribute("user");
        
        if (request instanceof HttpServletRequest) {
            //url = req.getRequestURL().toString();
            queryString = ((HttpServletRequest)request).getQueryString();
        }
        
        System.err.println(queryString.length());
        
        boolean malformedUrl = false;
        if (queryString.length()>2){
            if (!queryString.substring(0, 3).equals("id=")){
                malformedUrl = true;
                //resp.sendRedirect(req.getContextPath() + "/ERROR.html");
                //redirect to ERROR PAGE
            }
        } else {
            //resp.sendRedirect(req.getContextPath() + "/ERROR.html");
            malformedUrl = true;
        }
        
        queryString = queryString.substring(3); 
        
        
        //if (user!=null){
            user_id = user.getID();
        //}
        
        NumberFormatException error = null;
        try {
            group_id = Integer.parseInt(queryString); //get group ID
        } catch (NumberFormatException e) {
            error = e;
        }
        
        boolean isPartOfAGroup = false;
        if (error!=null){
            isPartOfAGroup = false;
            //resp.sendRedirect(req.getContextPath() + "/ERROR.html");
            //REDIRECT TO INVALID PAGE
        } else {
            try {
                isPartOfAGroup = User_Group.isPartOfAGroup(user_id, group_id);
            } catch (SQLException ex) {
                Logger.getLogger(GroupFilter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
        if (!isPartOfAGroup || malformedUrl){
            resp.sendRedirect(req.getContextPath() + "/ERROR.html");
            //REDIRECT TO FORBIDDEN PAGE
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
    }
    
    
}
