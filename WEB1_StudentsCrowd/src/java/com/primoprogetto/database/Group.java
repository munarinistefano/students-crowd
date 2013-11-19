/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primoprogetto.database;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Stefano
 */
public class Group {
    
    private int ID,OwnerID;
    private String name;
    private Date creationDate;
    private String ownerName;
    private String getOwnerName = "SELECT USERNAME FROM USERS JOIN GROUPS ON USERS.ID = GROUPS.OWNER_ID WHERE GROUPS.ID = ?";
    //////////////
    
    //////////////////////////
    public void setOwnerName(int GroupID) throws SQLException{
        ResultSet rs = DBManager.executeSelectQuery(getOwnerName, GroupID);
        try {
          while (rs.next()) {
            this.ownerName = rs.getString(1);
            System.err.println(ownerName);
          }
        } finally {
          rs.close();
        }
    }
    //////////////////////////
    
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
    
    public String getOwnerName(){
        return this.ownerName;
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
}
