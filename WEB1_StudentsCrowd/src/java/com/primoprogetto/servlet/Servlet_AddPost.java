/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primoprogetto.servlet;

import com.oreilly.servlet.MultipartRequest;
import com.primoprogetto.database.User;
import com.primoprogetto.database.interaction.Post;
import com.primoprogetto.database.interaction.PostFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Stefano
 */
public class Servlet_AddPost extends HttpServlet {
    private String dirName;
    private String fileName;
    private int postID;
    private int group_id;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        fileName = null;
        // read the uploadDir from the servlet parameters
        /*dirName = config.getInitParameter("uploadDirectory");
        if (dirName == null) {
            throw new ServletException("Please supply uploadDir parameter");
        }*/
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
        
        response.sendRedirect(request.getContextPath() + "/Group?id="+group_id); //redirect to landing page
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
        
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        
        group_id = Integer.parseInt(request.getParameter("group_id"));
        
        MultipartRequest multi = new MultipartRequest(request,".");
        File f = multi.getFile("file1");
        String text = multi.getParameter("text");
        fileName = multi.getFilesystemName("file1");
        
        // Get the system date and time.
        java.util.Date utilDate = new java.util.Date();
        // Convert it to java.sql.Date
        java.sql.Date date = new java.sql.Date(utilDate.getTime());
        
        try {
            postID = Post.addPost(text, user.getID(), group_id ,date);                      //add post to DB
        } catch (SQLException ex) {
            Logger.getLogger(Servlet_AddPost.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (fileName!=null){
            fileName = formatFileName(fileName);
            //check if "Resources" folder exists. If not, create it!
            dirName = request.getServletContext().getRealPath("Resources/");
            //System.err.println(request.getServletContext().getRealPath("/web/Resources"));
            File file = new File(dirName);
            if (!file.exists()){
                file.mkdir();
            }

            //check if "Resources/File" folder exists. If not, create it!
            dirName = request.getServletContext().getRealPath("Resources/File/");
            //System.err.println(request.getServletContext().getRealPath("/web/Resources"));
            File file2 = new File(dirName);
            if (!file2.exists()){
                file2.mkdir();
            }

            //check if "Resources/File/#groupID" folder exists. If not, create it!
            dirName = request.getServletContext().getRealPath("Resources/File/" + group_id + "/");
            File file3 = new File(dirName);
            if (!file3.exists()){
                file3.mkdir();
            }
            
            if (uploadFile(f,user)){
                try {
                    PostFile.addPostFile(fileName, postID);                                     //add file to DB
                } catch (SQLException ex) {
                    Logger.getLogger(Servlet_AddPost.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        processRequest(request, response);
    }

    /*
    *  Used to upload a file to the server.
    *  @return true if succesfully uploaded, false otherwise
    */
    private boolean uploadFile(File f, User user) throws FileNotFoundException, IOException {
        if (f!=null) { 
            fileName = user.getID() + "_" +fileName;       //FILENAME = 'GROUPID'_'USERID'_FILENAME
            File fOUT = new File(dirName,fileName);
            FileInputStream fIS = new FileInputStream(f); 
            FileOutputStream fOS = new FileOutputStream(fOUT); 
            while (fIS.available()>0) {
                fOS.write(fIS.read()); 
            }
            fIS.close(); 
            fOS.close();
            return true;
        }
        return false;
    }

    /*
    *  Delete white spaces from file name
    */
    private String formatFileName(String fileName) {
        fileName = fileName.replaceAll(" ", "");
        return fileName;
    }
}
