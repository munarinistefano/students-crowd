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

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;
        
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("user");
        boolean isPartOfAGroup = false;
        User_Group user_group = new User_Group();
        int group_id = 0;
                
        Error error = null;
        try {
            group_id = Integer.parseInt(req.getParameter("id"));
        } catch (Error e){
            error=e;
        }
        
        /*if (error==null){
            if (group_id!=0){
                try {
                    isPartOfAGroup = user_group.isPartOfAGroup(user.getID(), group_id);
                } catch (SQLException ex) {
                    Logger.getLogger(GroupFilter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }*/

        
        if (error==null){
            chain.doFilter(request, response);
            /*if (isPartOfAGroup){
                
            } else {
                //PAGINA DI ACCESSO NEGATO
            }*/
        } else {
            //PAGINA D'ERRORE
            System.err.println(error);
        }
    }

    @Override
    public void destroy() {
    }
    
    
}
