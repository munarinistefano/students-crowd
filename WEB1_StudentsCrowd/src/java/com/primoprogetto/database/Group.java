/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primoprogetto.database;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Stefano
 */
public class Group {
    private int ID,OwnerID;
    private String name;
    private Date creationDate;
    private String getAllGroups = "SELECT * FROM GROUPS";
    
    public void setName(String name){
        this.name=name;
    }
    
    public void setID(int ID){
        this.ID=ID;
    }
    
    public void setOwnerID(int OwnerID){
        this.OwnerID=OwnerID;
    }
    
    public void setCreationDate(Date date){
        this.creationDate=date;
    }
    
    public String getName(){
        return this.name;
    }
    
    public Date getCreationDate(){
        return this.creationDate;
    }
    
    public int getID(){
        return this.ID;
    }
    
    public int getIDOwner(){
        return this.OwnerID;
    }
    
    public ArrayList<Group> getAllGroups() throws SQLException {
      ArrayList<Group> groups = new ArrayList();
      ResultSet rs = DBManager.executeSelectQuery(getAllGroups);
      try {
        while (rs.next()) {
          Group group = new Group();
          group.setID(rs.getInt(1));
          group.setName(rs.getString(2));
          group.setOwnerID(rs.getInt(3));
          group.setCreationDate(rs.getDate(4));
          
          groups.add(group);
        }
      } finally {
        rs.close();
      }
      return groups;
    }
    
}
