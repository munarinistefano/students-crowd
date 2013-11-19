/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.primoprogetto.database.interaction;

import com.primoprogetto.database.DBManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Stefano
 */
public class Invitation {
    
    private final static String user_id = "user_id";
    private final static String group_id = "group_id";
    private final static String InvitationTable = "INVITATIONS";
    private final static String addInvitation = "INSERT INTO "+InvitationTable+" (" + user_id + "," + group_id+ ") VALUES (?,?)";
    private final static String addInvitationWithState = "INSERT INTO "+InvitationTable+" (" + user_id + "," + group_id + ",state) VALUES (?,?,?)";
    private final static String getInvitation = "SELECT * FROM INVITATIONS JOIN GROUPS ON INVITATIONS.GROUP_ID = GROUPS.ID JOIN USERS ON USERS.ID = GROUPS.OWNER_ID WHERE INVITATIONS.USER_ID = ? ORDER BY INVITATIONS.state";
    private final static String changeState = "UPDATE INVITATIONS set STATE = ? where USER_ID = ? AND GROUP_ID = ?";
    
    /*
     * Send an invitation
     */
    public static void addInvitation (int USER_ID, int GROUP_ID) throws SQLException{
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
     * Send an invitation with state
     */
    public static void addInvitation (int USER_ID, int GROUP_ID, int state) throws SQLException{
        PreparedStatement stm = DBManager.executeInsertQuery(addInvitationWithState);
        System.err.println("user_id: "+USER_ID+" GROUP_ID: "+GROUP_ID+" STATE:"+state);
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
    public static ArrayList<com.primoprogetto.database.Invitation> getInvitation(int id) throws SQLException {
        ArrayList<com.primoprogetto.database.Invitation> invitations = new ArrayList();
        ResultSet rs = DBManager.executeSelectQuery(getInvitation, id);
        try {
            while (rs.next()) {
                com.primoprogetto.database.Invitation invitation = new com.primoprogetto.database.Invitation();
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
    public static void changeState(int user_id, int group_id, int state) throws SQLException {
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
