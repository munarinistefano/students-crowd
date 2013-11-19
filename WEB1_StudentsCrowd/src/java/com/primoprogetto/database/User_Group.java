/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primoprogetto.database;

/**
 *
 * @author Stefano
 */
public class User_Group {
    private int UserID,GroupID;
    private int role;                       //0 normal user - 1 administrator
    
    /*private static String ownerName;
    
    private final static String getOwnerName = "SELECT USERNAME FROM USERS JOIN GROUPS ON USERS.ID = GROUPS.OWNER_ID WHERE GROUPS.ID = ?";*/
    
    public void setUserID(int UserID){
        this.UserID=UserID;
    }
    
    public void setGroupID(int GroupID){
        this.GroupID=GroupID;
    }
    
    public void setRole(int role){
        this.role=role;
    }
    
    /*public void setOwnerName(int GroupID) throws SQLException {
        ResultSet rs = DBManager.executeSelectQuery(getOwnerName, GroupID);
        while (rs.next()) {
            this.ownerName = rs.getString(1);
        }
    }
    
    public static String getOwnerName(int id) throws SQLException {
        return ownerName;
    }*/
    
    public int getUserId(){
        return this.UserID;
    }
    
    public int getGroupId(){
        return this.GroupID;
    }
    
    public int getRole(){
        return this.role;
    }
}
