/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primoprogetto.filters;

import com.primoprogetto.database.User;
import com.primoprogetto.database.User_Group;
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
public class GroupFilter implements Filter {
    String url = null;
    String queryString = null;
    int user_id,group_id;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("user");
        
        if (request instanceof HttpServletRequest) {
            url = req.getRequestURL().toString();
            queryString = ((HttpServletRequest)request).getQueryString();
        }
        
        if (!queryString.substring(0, 3).equals("id=")){
            resp.sendRedirect(req.getContextPath() + "/ERROR.html");
            //redirect to ERROR PAGE
        }
        
        //System.err.println("URL: "+url+" queryString: "+queryString);
        
        queryString = queryString.substring(3); 
        user_id = user.getID();
        NumberFormatException error = null;
        try {
            group_id = Integer.parseInt(queryString); //get group ID
        } catch (NumberFormatException e) {
            error = e;
        }
        
        boolean isPartOfAGroup = false;
        User_Group user_group = new User_Group();
        
        if (error!=null){
            resp.sendRedirect(req.getContextPath() + "/ERROR.html");
            //REDIRECT TO INVALID PAGE
        } else {
            try {
                isPartOfAGroup = user_group.isPartOfAGroup(user_id, group_id);
            } catch (SQLException ex) {
                Logger.getLogger(GroupFilter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        //System.err.println("queryString[MOD]: "+queryString);
        
        if (isPartOfAGroup){
            chain.doFilter(request, response);
        } else {
            resp.sendRedirect(req.getContextPath() + "/ERROR.html");
            //REDIRECT TO FORBIDDEN PAGE
        }
        
        
        /*if (error==null){
            chain.doFilter(request, response);
            if (isPartOfAGroup){
                
            } else {
                //PAGINA DI ACCESSO NEGATO
            }
        } else {
            //PAGINA D'ERRORE
            System.err.println(error);
        }*/
    }

    @Override
    public void destroy() {
    }
    
    
}
