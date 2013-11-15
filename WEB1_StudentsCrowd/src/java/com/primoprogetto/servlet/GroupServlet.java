/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primoprogetto.servlet;

import com.primoprogetto.database.Post;
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

/**
 *
 * @author Stefano
 */
public class GroupServlet extends HttpServlet {

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
        
        int group_id = Integer.parseInt(request.getParameter("id"));
        
        Post post = new Post();
        ArrayList<Post> posts = null;
        try {
            posts = post.getAllPosts(group_id);
        } catch (SQLException ex) {
            Logger.getLogger(GroupServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet GroupServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Group id: " + group_id + "</h1>");
            out.println("<br><br><ul>");
            System.err.println("posts "+posts.size());
            if (posts!=null){
                for (int i=0; i<posts.size(); i++){
                    out.println("<li>");
                    out.println("Post id: "+posts.get(i).getID());
                    out.println("Post text: "+posts.get(i).getText());
                    out.println("Post group_id: "+posts.get(i).getGroupId());
                    out.println("User id: "+posts.get(i).getUserID());
                    out.println("Post date: "+posts.get(i).getDate());
                    out.println("</li>");
                }
            }
            out.println("</ul>");
            out.println("<form name=\"input\" action=\"AddPostServlet?group_id="+group_id+"\" method=\"POST\">\n");
            out.println("Text: <input type=\"text\" name=\"text\">");
            out.println("<input type=\"submit\" value=\"Submit\">\n");
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
