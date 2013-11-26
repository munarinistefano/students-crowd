/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
public class PdfFilter implements Filter {

    String queryString;
    User user;
    int group_id;
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        queryString = null;
        user = null;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;
        HttpSession session = req.getSession();
        
        user = (User)session.getAttribute("user");
                
        if (request instanceof HttpServletRequest) {
            queryString = ((HttpServletRequest)request).getQueryString();
        }
        
        boolean malformedUrl = false;
        if (!queryString.substring(0, 3).equals("id=")){
            malformedUrl = true;
            //resp.sendRedirect(req.getContextPath() + "/ERROR.html");
        }
        
        queryString = queryString.substring(3);
        
        NumberFormatException error = null;
        try {
            group_id = Integer.parseInt(queryString);
        } catch (NumberFormatException e) {
            error = e;
        }
        
        boolean isAdmin = false;
        if (error!=null){
            isAdmin = false;
            //resp.sendRedirect(req.getContextPath() + "/ERROR.html");
            //REDIRECT TO INVALID PAGE
        } else {
            try {
                isAdmin = User_Group.isAdmin(user.getID(), group_id);
            } catch (SQLException ex) {
                Logger.getLogger(GroupFilter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
        if (isAdmin && !malformedUrl){
            chain.doFilter(request, response);
        } else {
            resp.sendRedirect(req.getContextPath() + "/ERROR.html");
            //REDIRECT TO FORBIDDEN PAGE
        }
    }

    @Override
    public void destroy() {
    }
    
}
