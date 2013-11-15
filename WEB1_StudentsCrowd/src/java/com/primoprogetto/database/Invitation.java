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
public class Invitation {
    private int GroupID,UserID;
    private String groupName,owner;
    private int state;                  //o pending - 1 accepted - 2 refused (DEFAULT 0)
    
    private String user_id = "user_id";
    private String group_id = "group_id";
    private static final String InvitationTable = "INVITATIONS";
    private String addInvitation = "INSERT INTO "+InvitationTable+" (" + user_id + "," + group_id+ ") VALUES (?,?)";
    private String addInvitationWithState = "INSERT INTO "+InvitationTable+" (" + user_id + "," + group_id + ",state) VALUES (?,?,?)";
    private String getInvitation = "SELECT * FROM INVITATIONS JOIN GROUPS ON INVITATIONS.GROUP_ID = GROUPS.ID JOIN USERS ON USERS.ID = GROUPS.OWNER_ID WHERE INVITATIONS.USER_ID = ? ORDER BY INVITATIONS.state";
    String changeState = "UPDATE INVITATIONS set STATE = ? where USER_ID = ? AND GROUP_ID = ?";
    
    
    public void setGroupID(int GroupID){
        this.GroupID=GroupID;
    }
    
    public void setState(int state){
        this.state=state;
    }
    
    public void setUserID(int UserID){
        this.UserID=UserID;
    }
    
    public void setGroupName(String name){
        this.groupName=name;
    }
    
    public void setOwnerName(String name){
        this.owner=name;
    }
    
    public int getGroupID(){
        return this.GroupID;
    }
    
    public int getUserID(){
        return this.UserID;
    }
    
    public int getState(){
        return this.state;
    }
    
    public String getGroupName(){
        return this.groupName;
    }
    
    public String getOwner(){
        return this.owner;
    }
    
    /*
     * Send an invitation
     */
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
    
    /*
     * Send an invitation
     */
    public void addInvitation (int USER_ID, int GROUP_ID, int state) throws SQLException{
        PreparedStatement stm = DBManager.executeInsertQuery(addInvitationWithState);
        try {
            stm.setInt(1, USER_ID);
            stm.setInt(2, GROUP_ID);
            stm.setInt(3, state);
            stm.executeUpdate();
            System.out.println("addInvitation");
        } finally { // ricordarsi SEMPRE di chiudere i PreparedStatement in un blocco finally
            stm.close();
        }
    }

    /*
     * Get all invitations
     */
    public ArrayList<Invitation> getInvitation(int id) throws SQLException {
        ArrayList<Invitation> invitations = new ArrayList();
        ResultSet rs = DBManager.executeSelectQuery(getInvitation, id);
        try {
            while (rs.next()) {
                Invitation invitation = new Invitation();
                invitation.setUserID(rs.getInt(1));
                invitation.setGroupID(rs.getInt(2));
                invitation.setState(rs.getInt(3));
                invitation.setGroupName(rs.getString(5));
                invitation.setOwnerName(rs.getString(9));
                invitations.add(invitation);
            }
        } finally {
            rs.close();
        }
        return invitations;
    }

    /*
     * Change state of an invitation request: 0 pending, 1 accepted, 2 refused
     */
    public void changeState(int user_id, int group_id, int state) throws SQLException {
        PreparedStatement stm = DBManager.executeInsertQuery(changeState);
        try {
            stm.setInt(1, state);
            stm.setInt(2, user_id);
            stm.setInt(3, group_id);
            stm.executeUpdate();
            System.err.println("changeStateInvitation");
        } finally { // ricordarsi SEMPRE di chiudere i PreparedStatement in un blocco finally
            stm.close();
        }
    }
}

