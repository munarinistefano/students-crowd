/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primoprogetto.servlet;

import com.primoprogetto.database.DBManager;
import com.primoprogetto.database.Group;
import com.primoprogetto.database.User;
import com.primoprogetto.database.interaction.User_Group;
import com.primoprogetto.filters.GroupFilter;
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
 * @author alan
 */
public class HTML_GroupsList extends HttpServlet {
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
        
        ArrayList <Group> groupList = new ArrayList();
        try {
            groupList = User_Group.getMyGroups(user.getID());
        } catch (SQLException ex) {
            Logger.getLogger(Group.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet GroupList</title>");      
            out.println("</head>");
            out.println("<body>");
            for (int i=0; i<groupList.size(); i++){
                //System.err.println(groupList.get(i).getName());
                //System.err.println(groupList.get(i).getOwnerName());
                out.println(i+1 + ". <a href=Group?id=" + groupList.get(i).getID() + ">" + 
                        groupList.get(i).getName() + "</a> created by "
                        + groupList.get(i).getOwnerName() + " in date: "
                        + groupList.get(i).getCreationDate() + ";<br />");
                if (isAdmin(groupList.get(i).getID())){
                    out.println("<a href=GeneratePdfServlet?id=" + groupList.get(i).getID() + ">PDF</a><br /><br />");
                }
            }
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

    private boolean isAdmin(int group_id) {
        boolean isAdmin = false;
        try {
                isAdmin = User_Group.isAdmin(user.getID(), group_id);
            } catch (SQLException ex) {
                Logger.getLogger(GroupFilter.class.getName()).log(Level.SEVERE, null, ex);
            }
        return isAdmin;
    }
}
