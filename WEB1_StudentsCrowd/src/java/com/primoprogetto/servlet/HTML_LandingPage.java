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
public class HTML_LandingPage extends HttpServlet {
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
            out.println("<!doctype html>\n");
            out.println("<html>");
            out.println("	<head>");
            out.println("   <meta charset=\"utf-8\" />");
            out.println("   <title>STUDENTS CROWD</title>");
            out.println("   <link rel=\"stylesheet\" type=\"text/css\" href=\"css/stylesheet2.css\" />");
            out.println("   <link href='http://fonts.googleapis.com/css?family=Raleway:900,500' rel='stylesheet' type='text/css'>");
            out.println("   <meta charset=\"utf-8\" />");
            out.println(" </head>");
            out.println(" <body>");
            out.println("   <div class=\"container\">");
            out.println("     <header>");
            out.println("       <div id=\"logo\">");
            out.println("         <img src=\"img/icon.png\">");
            out.println("       </div>");
            out.println("       <div id=\"title\">");
            out.println("         <h1>Students Crowd</h1>");
            out.println("       <div id=\"userbar\">");
            out.println("         <h1>" + user.getUsername() + " (<a href=\"LogoutServlet\">logout</a>)</h1>");
            // out.println("       </div>");
            out.println("     </header>");
            out.println("   </div>");
            out.println("   <div class=\"content\">");
            out.println("     <h1>Welcome back, " + user.getUsername() + " (ID: " + user.getID() + ")!</h1>");
            // questo serve ancora? intanto lo commento
            // out.println("     <p>Your last access was " + new Date(session.getLastAccessedTime()) + "</p>");
            out.println("     <p>Your last access was " + getLastLogin() + "</p>");
            out.println("     <div id=\"invitations\">");
            out.println("       <a href=\"Invitation\"><img src=\"img/icon.png\"><br /><font color=\"#12557F\">Invitations</font></a>");
            out.println("   </div>");
            out.println("   <div id=\"groups\">");
            out.println("     <a href=\"GroupList\"><img src=\"img/icon.png\"><br /><font color=\"#12557F\">Groups</font></a>");
            out.println("   </div>");
            out.println("   <div id=\"create\">");
            out.println("     <a href=\"CreateGroup\"><img src=\"img/icon.png\"><br /><font color=\"#12557F\">Create group</font></a>");
            out.println("   </div>");
            out.println(" </div>");
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
    }
}
