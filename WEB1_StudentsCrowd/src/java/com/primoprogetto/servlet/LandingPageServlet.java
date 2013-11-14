/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primoprogetto.servlet;

import com.primoprogetto.database.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Stefano
 */
public class LandingPageServlet extends HttpServlet {
    private User user;
    private HttpSession session;
    Cookie[] cookies;
    Cookie cookie;
    
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
        
        cookie = null;
        cookies = null;
        cookies = request.getCookies();
        
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LandingPage</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>User: " + user.getUsername() + " ---> UserID: " + user.getID() + "</h1>");
            out.println("<br><br>");
            out.println("<a href=\"CreateGroup\">Create Group</a>");
            out.println("<br><br>");
            out.println("<a href=\"Invitation\">Invitation</a>");
            out.println("<br><br>");
            out.println("<a href=\"GroupList\">My groups</a>");
            out.println("<br><br>");
            out.println("<h3>Ultimo Login: " + getLastLogin() + "</h3>");
            out.println("Time of Last Access" + new Date(session.getLastAccessedTime()));
            out.println("<br><br>");
            out.println("<a href=\"Logout\">Logout</a>");
            out.println("</body>");
            out.println("</html>");
        } finally {            
            out.close();
        }
    }
    
    public String getLastLogin(){
        String lastLogin = null;
        for (int i = 0; i < cookies.length; i++){
            cookie = cookies[i];
            if(cookie.getName().equals("previousLogin") )
                lastLogin = cookie.getValue();
        }
        return lastLogin;
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
