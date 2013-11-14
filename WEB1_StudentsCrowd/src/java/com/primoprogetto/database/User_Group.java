/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primoprogetto.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Stefano
 */
public class User_Group {
    private int UserID,GroupID;
    private int role;                       //0 normal user - 1 administrator
    
    private String insertQuery = "INSERT INTO USERGROUP (USER_ID,GROUP_ID,ROLE) VALUES (?,?,?)";

    
    public void setUserID(int UserID){
        this.UserID=UserID;
    }
    
    public void setGroupID(int GroupID){
        this.GroupID=GroupID;
    }
    
    public void setRole(int role){
        this.role=role;
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
}
