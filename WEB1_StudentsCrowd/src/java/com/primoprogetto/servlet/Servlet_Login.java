/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primoprogetto.servlet;

import com.primoprogetto.database.DBManager;
import com.primoprogetto.database.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.servlet.RequestDispatcher;
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
public class Servlet_Login extends HttpServlet {

    private DBManager manager;
    private String username,password;
    private User user = null;
    HttpSession session;
    Cookie[] cookies;
    Cookie cookie;
    String lastLogin;
    
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
        
        username = request.getParameter("username");    //get form parameters
        password = request.getParameter("password");
        session = request.getSession();                 //get an instance of the current session
        
        try { 
            user = manager.authenticate(username, password);   //try to autentichate user with username & password
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
        
        if (user==null){                                       //if user does not exists, redirect to login page
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        } else {
            
            session.setAttribute("user", user);                //set session attribute
            
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            
            cookie = null;
            cookies = null;
            cookies = request.getCookies();
            
            for (int i = 0; i < cookies.length; i++){
                cookie = cookies[i];
                if(cookie.getName().equals("lastLogin") )
                    lastLogin = cookie.getValue();
            }
            
            Cookie previousLoginCookie = new Cookie("previousLogin",lastLogin);
            previousLoginCookie.setMaxAge(60*60*24*360);
            response.addCookie(previousLoginCookie);
            
            Cookie lastLoginCookie = new Cookie("lastLogin",dateFormat.format(cal.getTime()));
            lastLoginCookie.setMaxAge(60*60*24*360);
            response.addCookie(lastLoginCookie);
            
            response.sendRedirect(request.getContextPath() + "/LandingPage"); //redirect to landing page
            
        }
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
