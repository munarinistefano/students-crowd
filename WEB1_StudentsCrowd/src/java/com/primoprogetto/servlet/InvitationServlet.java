/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primoprogetto.servlet;

import com.primoprogetto.database.DBManager;
import com.primoprogetto.database.Group;
import com.primoprogetto.database.User;
import com.primoprogetto.database.Invitation;
import com.primoprogetto.database.User_Group;
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
public class InvitationServlet extends HttpServlet {
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
        
        session = request.getSession();
        
        user = (User)session.getAttribute("user");
        
        ArrayList<Invitation> invitations = new ArrayList();
        try {
            Invitation invitation = new Invitation();
            invitations = invitation.getInvitation(user.getID());
        } catch (SQLException ex) {
            Logger.getLogger(InvitationServlet.class.getName()).log(Level.SEVERE, null, ex);
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
                    out.println("<li> GroupID: "+invitations.get(i).getGroupID()+" GroupName: "+invitations.get(i).getGroupName()+" Owner:"+invitations.get(i).getOwner()+"    PENDING");
                    out.println("<form name=\"input\" action=\"Invitation\" method=\"POST\"> <input name=\""+invitations.get(i).getGroupID()+"\" type=\"submit\" value=\"accept\">");
                    out.println("<input name=\""+invitations.get(i).getGroupID()+"\" type=\"submit\" value=\"refuse\">");
                    //out.println("Refuse <form name=\""+invitations.get(i).getGroupID()+"\" action=\"Invitation\" method=\"POST\" value=\"refuse\">");
                    //out.println("Accept <input type=\"submit\" name=\""+invitations.get(i).getGroupID()+"\" value=\"accept\"/>");
                    //out.println("Refuse  <input type=\"radio\" name=\""+invitations.get(i).getGroupID()+"\" value=\"refuse\"/>");
                    //out.println("</fieldset>");
                    out.println("</li>");
                } else if (invitations.get(i).getState()==1)
                    out.println("<li> GroupID: "+invitations.get(i).getGroupID()+" GroupName: "+invitations.get(i).getGroupName()+" Owner:"+invitations.get(i).getOwner()+"   <p> ACCEPTED </p></li>");
                else if (invitations.get(i).getState()==2)
                    out.println("<li> GroupID: "+invitations.get(i).getGroupID()+" GroupName: "+invitations.get(i).getGroupName()+" Owner:"+invitations.get(i).getOwner()+"   <p> REFUSED </p></li>");
            }
            out.println("</ul>");
            out.println("<form name=\"input\" action=\"Invitation\" method=\"POST\">\n");
            out.println("<input type=\"submit\" value=\"Submit\">\n");
            out.println("</body>");
            out.println("</html>");
        } finally {            
            out.close();
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
        
        Enumeration paramNames = request.getParameterNames();
        
        while(paramNames.hasMoreElements()) {
            String paramName = (String)paramNames.nextElement();

            User_Group user_group = new User_Group();
            Invitation invitation = new Invitation();

            String[] paramValues = request.getParameterValues(paramName);
            for(int i=0; i < paramValues.length; i++) {
                System.out.println("paramName: " + paramName + " paramValue: " + paramValues[i]);
                if (paramValues[i].equals("accept")){
                    try {
                        invitation.changeState(user.getID(),Integer.parseInt(paramName),1);
                        user_group.add(user.getID(),Integer.parseInt(paramName),0);
                    } catch (SQLException ex) {
                        Logger.getLogger(InvitationServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (paramValues[i].equals("refuse")){
                    try {
                        invitation.changeState(user.getID(),Integer.parseInt(paramName),2);
                    } catch (SQLException ex) {
                        Logger.getLogger(InvitationServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
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
