/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primoprogetto.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Stefano
 */
public class User_Group {
    private int UserID,GroupID;
    private int role;                       //0 normal user - 1 administrator
    private String ownerName;
    
    private String insertQuery = "INSERT INTO USERGROUP (USER_ID,GROUP_ID,ROLE) VALUES (?,?,?)";
    private String getMyGroups = "SELECT * FROM USERGROUP JOIN USERS ON USERGROUP.USER_ID = USERS.ID JOIN GROUPS ON USERGROUP.GROUP_ID = GROUPS.ID WHERE USERGROUP.USER_ID = ?";
    private String getOwnerName = "SELECT USERNAME FROM USERS JOIN GROUPS ON USERS.ID = GROUPS.OWNER_ID WHERE GROUPS.ID = ?";
    
    public void setUserID(int UserID){
        this.UserID=UserID;
    }
    
    public void setGroupID(int GroupID){
        this.GroupID=GroupID;
    }
    
    public void setRole(int role){
        this.role=role;
    }
    
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
    
    public int getUserId(){
        return this.UserID;
    }
    
    public int getGroupId(){
        return this.GroupID;
    }
    
    public int getRole(){
        return this.role;
    }

    public String getOwnerName(){
        return this.ownerName;
    }

    public void add(int user_id, int group_id, int role) throws SQLException {
        PreparedStatement stm = DBManager.executeInsertQuery(insertQuery);
        try {
            stm.setInt(1, user_id);
            stm.setInt(2, group_id);
            stm.setInt(3, role);
            stm.executeUpdate();
        } finally { // ricordarsi SEMPRE di chiudere i PreparedStatement in un blocco finally
            stm.close();
        }
    }
    
    public ArrayList<Group> getMyGroups(int id) throws SQLException {
      ArrayList<Group> groups = new ArrayList();
      ResultSet rs = DBManager.executeSelectQuery(getMyGroups, id);
      try {
        while (rs.next()) {
          Group group = new Group();
          group.setID(rs.getInt(7));
          group.setName(rs.getString(8));
          //group.setOwnerID(rs.getInt(5));
          // group.setID(rs.getInt(2));
          group.setCreationDate(rs.getDate(10));
          group.setOwnerName(rs.getInt(7));
          //User_Group userGroup = new User_Group();
          //userGroup.setOwnerName(rs.getInt(7));
          
          groups.add(group);
        }
      } finally {
        rs.close();
      }
      return groups;
    }
    
    public String getOwnerName(int id) throws SQLException {
      ResultSet rs = DBManager.executeSelectQuery(getOwnerName);
      String ownerName = "ciao";
      try {
        while (rs.next()) {

        }
      } finally {
        rs.close();
      }
      return ownerName;
    }
}
