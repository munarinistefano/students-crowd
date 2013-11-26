/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primoprogetto.servlet;

import com.primoprogetto.database.DBManager;
import com.primoprogetto.database.Invitation;
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
public class HTML_Invitation extends HttpServlet {

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
        
        ArrayList<Invitation> invitations = new ArrayList();
        try {
            Invitation invitation = new Invitation();
            invitations = com.primoprogetto.database.interaction.Invitation.getInvitation(user.getID());
        } catch (SQLException ex) {
            Logger.getLogger(Servlet_Invitation.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Invitation</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<ul>");
            for (int i=0; i<invitations.size(); i++){
                if (invitations.get(i).getState()==0){
                    out.println("<li> GroupID: "+invitations.get(i).getGroupID()+" <a href=\"GroupServlet?id=" + invitations.get(i).getGroupID() + "\"> GroupName: "+invitations.get(i).getGroupName()+" </a> Owner:"+invitations.get(i).getOwner()+"    PENDING");
                    out.println("<form name=\"input\" action=\"InvitationServlet\" method=\"POST\"> <input name=\""+invitations.get(i).getGroupID()+"\" type=\"submit\" value=\"accept\">");
                    out.println("<input name=\""+invitations.get(i).getGroupID()+"\" type=\"submit\" value=\"refuse\">");
                    out.println("</li>");
                } else if (invitations.get(i).getState()==1){
                    out.println("<li> GroupID: "+invitations.get(i).getGroupID()+" <a href=\"Group?id=" + invitations.get(i).getGroupID() + "\"> GroupName: "+invitations.get(i).getGroupName()+" </a> Owner:"+invitations.get(i).getOwner()+"   <p> ACCEPTED </p></li>");
                }else if (invitations.get(i).getState()==2){
                    out.println("<li> GroupID: "+invitations.get(i).getGroupID()+" GroupName: "+invitations.get(i).getGroupName()+" Owner:"+invitations.get(i).getOwner()+"   <p> REFUSED </p></li>");
                }
            }
            out.println("</ul>");
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
