/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primoprogetto.servlet;

import com.primoprogetto.database.DBManager;
import com.primoprogetto.database.User;
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
public class CreateGroup extends HttpServlet {
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
        
        session = request.getSession();
        user = (User)session.getAttribute("user");
        
        ArrayList <User> userList = new ArrayList();
        try {
            userList = manager.getAllUser();
        } catch (SQLException ex) {
            Logger.getLogger(CreateGroup.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CreateGroup</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<form name=\"input\" action=\"CreateGroup\" method=\"POST\">\n");
            out.println("GroupName: <input type=\"text\" name=\"username\">");
            out.println("<br> Owner: " + user.getUsername() + "<br>");
            out.println("<ul>");
            for (int i=0; i<userList.size(); i++){
                out.println("<li><input type=\"checkbox\" name=" + userList.get(i).getUsername() +">"+ userList.get(i).getUsername() + "</li>");
            }
            out.println("</ul>");
            out.println("<input type=\"submit\" value=\"Submit\">\n");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
        } finally {            
            out.close();
        }
    }
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        //processRequest(request, response);

        
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CreateGroup</title>");            
            out.println("</head>");
            out.println("<body>");
            
            out.println("<br> Owner: " + user.getUsername() + "<br>");
            
            out.println("<ul>");
            out.println("</ul>");
            out.println("</body>");
            out.println("</html>");
        } finally {            
            out.close();
        }
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
