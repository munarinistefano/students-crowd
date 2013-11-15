/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primoprogetto.database;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Stefano
 */
public class Group {
    ResultSet generatedKey = null;
    private static final String GroupTable = "GROUPS";
    private int ID,OwnerID;
    private String name;
    private Date creationDate;
    private String getAllGroups = "SELECT * FROM "+ GroupTable;
    private String insertQuery = "INSERT INTO "+ GroupTable + " (NAME,OWNER_ID,CREATION_DATE) VALUES (?,?,?)";
    private String getIDcreatedGroup = "SELECT id FROM "+ GroupTable + " WHERE name = ? AND owner_id = ? AND creation_date = ?";
    
    //////////////
    private String ownerName;
    private String getOwnerName = "SELECT USERNAME FROM USERS JOIN GROUPS ON USERS.ID = GROUPS.OWNER_ID WHERE GROUPS.ID = ?";
    //////////////
    
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
    
        public String getOwnerName(){
        return this.ownerName;
    }
    //////////////////////////
    
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
    
    /*
     * Get all Groups
     */
    public Group getGroup() throws SQLException {
        Group group = new Group();
        ResultSet rs = DBManager.executeSelectQuery(getAllGroups);
        try {
            while (rs.next()) {
              group.setID(rs.getInt(1));
              group.setName(rs.getString(2));
              group.setOwnerID(rs.getInt(3));
              group.setCreationDate(rs.getDate(4));
            }
        } finally {
          rs.close();
        }
        return group;
    }
    
    /*
     * Create a new Group
     */
    public int addGroup(String name, int ownerID, Date creationDate) throws SQLException {
        System.out.println("addGroup");
        int id = 0;
        PreparedStatement stm = DBManager.executeInsertQuery(insertQuery);
        try {
            //stm.setString(1, DBName);
            stm.setString(1, name);
            stm.setInt(2, ownerID);
            stm.setDate(3, creationDate);
            stm.executeUpdate();
            id = getId(stm);
        } finally { // ricordarsi SEMPRE di chiudere i PreparedStatement in un blocco finally
            stm.close();
            if (generatedKey != null) try {generatedKey.close();} catch (SQLException logOrIgnore) {}
        }
        return id;
    }

    /*
     * Get id of just created group. Used to get the id of the just created Group
     */
    private int getId(PreparedStatement stm) throws SQLException {
        int id = 0;
        try {
            generatedKey = stm.getGeneratedKeys();
        } catch (SQLException ex) {
            Logger.getLogger(Group.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (generatedKey.next()) {
            id = generatedKey.getInt(1);
        } else {
            throw new SQLException("add group failed, no generated key obtained.");
        }
        return id;
    }
}
