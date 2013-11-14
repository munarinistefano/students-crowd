/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primoprogetto.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Stefano
 */
public class Invitation {
    private int GroupID,UserID;
    private static final String InvitationTable = "INVITATIONS";
    private String addInvitation = "INSERT INTO "+InvitationTable+" (user_id, group_id) VALUES (?,?)";
    
    
    public void setGroupID(int GroupID){
        this.GroupID=GroupID;
    }
    
    public void setUserID(int UserID){
        this.UserID=UserID;
    }
    
    public int getGroupID(){
        return this.GroupID;
    }
    
    public int getUserID(){
        return this.UserID;
    }
    
    public void addInvitation (int USER_ID, int GROUP_ID) throws SQLException{
        PreparedStatement stm = DBManager.executeInsertQuery(addInvitation);
        try {
            stm.setInt(1, USER_ID);
            stm.setInt(2, GROUP_ID);
            stm.executeUpdate();
            System.out.println("addInvitation");
        } finally { // ricordarsi SEMPRE di chiudere i PreparedStatement in un blocco finally
            stm.close();
        }
    }
}
