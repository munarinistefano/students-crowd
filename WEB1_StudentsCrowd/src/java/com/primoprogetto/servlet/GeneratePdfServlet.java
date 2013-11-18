/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primoprogetto.servlet;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.primoprogetto.database.Group;
import com.primoprogetto.database.Post;
import com.primoprogetto.database.User;
import com.primoprogetto.database.User_Group;
import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
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
 * Servlet che genera un report in pdf indicante, per il gruppo in questione: userneme degli utenti
 * partecipanti, data del loro ultimo post all'interno del gruppo e numero totale di post inseriti.
 * @author alan
 */
public class GeneratePdfServlet extends HttpServlet {
  HttpSession session;
  User user;
  
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
    // Setto il content type come application pdf
    response.setContentType("application/pdf");
        
    session = request.getSession();
    user = (User)session.getAttribute("user");
    
    
        
    int group_id = Integer.parseInt(request.getParameter("id"));
    int postsNumber = -1;
    int user_id;
    User_Group userGroup = new User_Group();
    ArrayList <User> userList = new ArrayList();
    
    try {
      userList = userGroup.getMembersNames(group_id);
    } catch (SQLException ex) {
      Logger.getLogger(Group.class.getName()).log(Level.SEVERE, null, ex);
    }

    Post post = new Post();
        
    // Prendo lo stream dell'output per scrivere in un oggetto PDF.
    OutputStream out = response.getOutputStream();
        
    try {
      Document document = new Document();
      PdfWriter.getInstance(document, out);
      document.open();
      
      Paragraph title = new Paragraph("Group #" + group_id + " Report",
              FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD));
      
      // Header del documento: numero del gruppo del report
      title.setAlignment(Element.ALIGN_CENTER);
      document.add(title);
      document.add(new Paragraph("---------------------------------"));
            
      for (int i=0; i<userList.size(); i++) {
        // 1. Stampo lo username dell'utente
        document.add(new Paragraph("user: " + userList.get(i).getUsername()));
        user_id = userList.get(i).getID();

        try {
          post.setNumberOfPostsOfAUser(user_id, group_id);
          post.setDateOfLastPostOfAUser(user_id, group_id, user_id, group_id);
        } catch (SQLException ex) {
          Logger.getLogger(GeneratePdfServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
              
        // 2. Stampo il numero di posts dell'utente in questione
        document.add(new Paragraph("number of posts: " + post.getNumberOfPostsOfAUser()));
              
        // 3. Se esistono posts a nome dell'utente, stampo la data dell'ultimo post
        if (post.getNumberOfPostsOfAUser() == 0) {
          document.add(new Paragraph("No post inserted!"));
        } else {
          document.add(new Paragraph("Last post: " + post.getDateOfLastPostOfAUser()));
        }
            
        document.add(new Paragraph("---------------------------------"));
      }
            
      document.close();
    } catch (DocumentException exc) {
      throw new IOException(exc.getMessage());
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
