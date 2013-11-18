/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primoprogetto.servlet;

import com.primoprogetto.database.DBManager;
import com.primoprogetto.database.Group;
import com.primoprogetto.database.User;
import com.primoprogetto.database.Invitation;
import com.primoprogetto.database.User_Group;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
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
public class Servlet_Invitation extends HttpServlet {
    private HttpSession session;
    private User user;
    DBManager manager;
    
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
        
        while(paramNames.hasMoreElements()) {
            String paramName = (String)paramNames.nextElement();

            User_Group user_group = new User_Group();
            Invitation invitation = new Invitation();

            String[] paramValues = request.getParameterValues(paramName);
            for(int i=0; i < paramValues.length; i++) {
                if (paramValues[i].equals("accept")){
                    try {
                        invitation.changeState(user.getID(),Integer.parseInt(paramName),1);
                        user_group.add(user.getID(),Integer.parseInt(paramName),0);
                    } catch (SQLException ex) {
                        Logger.getLogger(Servlet_Invitation.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (paramValues[i].equals("refuse")){
                    try {
                        invitation.changeState(user.getID(),Integer.parseInt(paramName),2);
                    } catch (SQLException ex) {
                        Logger.getLogger(Servlet_Invitation.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        response.sendRedirect(request.getContextPath() + "/Invitation"); //redirect to landing page
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
