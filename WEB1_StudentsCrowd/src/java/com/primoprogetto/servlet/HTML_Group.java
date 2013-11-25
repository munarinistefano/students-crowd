/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primoprogetto.servlet;

import com.primoprogetto.database.Post;
import com.primoprogetto.database.User;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
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
public class HTML_Group extends HttpServlet {

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
        
        int group_id = Integer.parseInt(request.getParameter("id"));
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        
        ArrayList<Post> posts = null;
        
        try {
            posts = com.primoprogetto.database.interaction.Post.getAllPosts(group_id,user.getID());
        } catch (SQLException ex) {
            Logger.getLogger(HTML_Group.class.getName()).log(Level.SEVERE, null, ex);
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
            if (posts!=null){
                for (int i=0; i<posts.size(); i++){
                    String text = formatText(posts.get(i).getText(),posts.get(i).getGroupId(), request);
                    out.println("<li>");
                    out.println("Post id: " + posts.get(i).getID());
                    out.println("Post text: " + text);
                    out.println("Group_id: " + posts.get(i).getGroupId());
                    out.println("User id: " + posts.get(i).getUserID());
                    out.println("Post date: " + posts.get(i).getDate());
                    if (posts.get(i).getFile()!=null){
                        String path = "Resources/File/" + group_id + "/" + posts.get(i).getFile();
                        out.println("<a href=\""+ path +"\" target='blank'>" + posts.get(i).getFile() + "</a>");
                    }
                    out.println("</li>");
                }
            }
            out.println("</ul>");
            out.println("<form name=\"input\" action=\"AddPostServlet?group_id="+group_id+"\" method=\"POST\" enctype='multipart/form-data'>");
            out.println("Text: <input type=\"text\" name=\"text\"> <br>");
            out.println("File: <input type=\"file\" name=\"file1\"> <br/>");
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

    private String formatText(String text, int group_id, HttpServletRequest request) {
        
        String formattedText="";
        String delims = "[ ]+";
        String [] token = text.split(delims);
        for (int i=0; i<token.length; i++){
            if (token[i].startsWith("$$www") && token[i].endsWith("$$")){
                formattedText += " <a href=\"http://" + token[i].substring(2, token[i].length()-2) + "\" target='blank'>" + token[i].substring(2, token[i].length()-2) + " </a> ";
            } else if (token[i].startsWith("$$http://") && token[i].endsWith("$$")){
                formattedText += " <a href=\"" + token[i].substring(2, token[i].length()-2) + "\" target='blank'>" + token[i].substring(2, token[i].length()-2) + " </a> ";
            }else if (token[i].startsWith("$$") && token[i].endsWith("$$")){
                if (fileExistsInThisGroup(token[i],group_id,request)){
                    String path = "Resources/File/" + group_id;
                    formattedText += " <a href=\"" + path + "/" + token[i].substring(2, token[i].length()-2) + "\" target='blank'>" + token[i].substring(2, token[i].length()-2) + " </a> ";
                } else {
                    formattedText += token[i].substring(2, token[i].length()-2) + " ";
                }
            } else {
                formattedText += token[i] + " ";
            }
        }
        
        
        /*int start = text.indexOf("$$");
        int end = text.indexOf("$$", start+1);
        
        String file=null;
        if (start!=-1 && end!=-1){
            String path = "Resources/File/" + group_id;
            return text.substring(0, start) + "<a href=\"" + path + "/" + text.substring(start+2, end) + "\" target='blank'>" + text.substring(start+2,end) + " </a> " + text.substring(end+2);
        } else {
            return text;
        }*/
        return formattedText;
    }

    private boolean fileExistsInThisGroup(String fileName, int group_id, HttpServletRequest request) {
        //check if "Resources/File/#groupID" folder exists. If not, create it!
        String filePath = request.getServletContext().getRealPath("Resources/File/" + group_id + "/" + fileName);
        //System.err.println(request.getServletContext().getRealPath("/web/Resources"));
        File file = new File(filePath);
        if (file.exists()){
            return true;
        }
        return false;
    }
}
