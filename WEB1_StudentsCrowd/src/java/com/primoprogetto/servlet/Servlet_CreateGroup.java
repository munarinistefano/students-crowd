/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primoprogetto.servlet;

import com.primoprogetto.database.DBManager;
import com.primoprogetto.database.User;
import com.primoprogetto.database.interaction.Group;
import com.primoprogetto.database.interaction.Invitation;
import com.primoprogetto.database.interaction.User_Group;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Stefano
 */
public class Servlet_CreateGroup extends HttpServlet {
    DBManager manager;
    HttpSession session;
    User user;

    @Override
    public void init() throws ServletException {
    // inizializza il DBManager dagli attributi di Application
      this.manager = (DBManager)super.getServletContext().getAttribute("dbmanager");
    }
    
    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        session = request.getSession();
        user = (User)session.getAttribute("user");
        
        Enumeration paramNames = request.getParameterNames();
        
        int group_id = 0;
        while(paramNames.hasMoreElements()) {
            String paramName = (String)paramNames.nextElement();  //get single parameter
            
            java.sql.Date date;
            // Get the system date and time.
            java.util.Date utilDate = new java.util.Date();
            // Convert it to java.sql.Date
            date = new java.sql.Date(utilDate.getTime());     //set creation date

            String[] paramValues = request.getParameterValues(paramName);
            for(int i=0; i < paramValues.length; i++) {
                if (paramName.equals("groupname")){                 //get group name
                    try {
                        group_id = Group.addGroup(paramValues[i], user.getID(), date);
                        Invitation.addInvitation(user.getID(), group_id,1);
                        User_Group.add(user.getID(),group_id,1);
                    } catch (SQLException ex) {
                        Logger.getLogger(Servlet_CreateGroup.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {                                            //get selected checkboxes
                    if (group_id!=0){
                        try {                               
                            Invitation.addInvitation(Integer.parseInt(paramName), group_id);
                        } catch (SQLException ex) {
                            Logger.getLogger(Servlet_CreateGroup.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }
        response.sendRedirect(request.getContextPath() + "/GroupList"); //redirect to group list page
    }
    

    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
