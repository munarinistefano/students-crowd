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
public class HTML_CreateGroup extends HttpServlet {
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
        
        ArrayList<User> userList = new ArrayList();
        try {
            userList = manager.getAllUser();
        } catch (SQLException ex) {
            Logger.getLogger(Servlet_CreateGroup.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
            out.println("       <a href=\"LandingPage\"><div id=\"logo\">");
            out.println("         <img src=\"img/icon.png\">");
            out.println("       </div></a>");
            out.println("       <div id=\"title\">");
            out.println("         <a href=\"LandingPage\"><h1>Students Crowd</h1></a>&nbsp;&nbsp;"
                    + "<h2>&nbsp;&nbsp;>&nbsp;&nbsp;CREATE GROUP</h2>");
            out.println("       <div id=\"userbar\">");
            out.println("         <h1>" + user.getUsername() + " (<a href=\"LogoutServlet\">logout</a>)</h1>");
            // out.println("       </div>");
            out.println("     </header>");
            out.println("   </div>");
            
            
            out.println("<div class=\"content\">");
            out.println(" <div class=\"error_container\">");
            out.println("   <div id=\"invite_image\">\n");
            out.println("   <img src=\"img/groups.png\"></div><div id=\"invite_text\">");
            out.println("     <form name=\"input\" action=\"CreateGroupServlet\" method=\"POST\" accept-charset=\"ISO-8859-1\">\n");
            
            out.println("<p>");
            out.println("<label for=\"username\" class=\"uname\" data-icon=\"g\"> Group name </label>");
            out.println("<input id=\"username\" name=\"groupname\" required=\"required\" type=\"text\" placeholder=\"insert your group name here\" />");
            out.println("</p>");
                        
                        //out.println("       GroupName: <input type=\"text\" name=\"groupname\">");
            out.println("       <p> Owner: " + user.getUsername() + "</p>");
            out.println("</div>");
            out.println("		<div id=\"invite_users\">\n");
            out.println("     <ul>");
            
            for (int i=0; i<userList.size(); i++) {
                if (userList.get(i).getID() == user.getID()) {
                  out.println("&nbsp;");
                } else {
                  out.println("<li><input type=\"checkbox\" name=" + userList.get(i).getID() +">"+ userList.get(i).getUsername() + "</li>");
                }
            }
            
            out.println("</ul>");
            out.println("<input type=\"submit\" value=\"Submit\">\n");
            out.println("</form>");
            out.println("		</div>\n");
      
            
            
            
            
            
            
            
            
            //out.println("<form name=\"input\" action=\"CreateGroupServlet\" method=\"POST\">\n");
            //out.println("GroupName: <input type=\"text\" name=\"groupname\">");
            //out.println("<br> Owner: " + user.getUsername() + "<br>");
            /*
            out.println("<ul>");
            for (int i=0; i<userList.size(); i++){
                if (userList.get(i).getID() == user.getID()) {
                  out.println("&nbsp;");
                } else {
                  out.println("<li><input type=\"checkbox\" name=" + userList.get(i).getID() +">"+ userList.get(i).getUsername() + "</li>");
                }
            }
            out.println("</ul>");
            out.println("<input type=\"submit\" value=\"Submit\">\n");
            out.println("</form>");*/
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
