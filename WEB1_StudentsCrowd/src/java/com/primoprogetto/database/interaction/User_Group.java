/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.primoprogetto.database.interaction;

import com.primoprogetto.database.DBManager;
import com.primoprogetto.database.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Stefano
 */
public class User_Group {
    private final static String insertQuery = "INSERT INTO USERGROUP (USER_ID,GROUP_ID,ROLE) VALUES (?,?,?)";
    private final static String getMyGroups = "SELECT * FROM USERGROUP JOIN USERS ON USERGROUP.USER_ID = USERS.ID JOIN GROUPS ON USERGROUP.GROUP_ID = GROUPS.ID WHERE USERGROUP.USER_ID = ?";
    private final static String isPartOfAGroup = "SELECT COUNT(1) FROM USERGROUP WHERE GROUP_ID = ? AND USER_ID = ?";
    private final static String getMembersNames = "SELECT USERNAME, ID FROM USERS JOIN USERGROUP ON USERS.ID = USERGROUP.USER_ID WHERE USERGROUP.GROUP_ID = ?";
    
    public static void add(int user_id, int group_id, int role) throws SQLException {
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
    
    public static ArrayList<com.primoprogetto.database.Group> getMyGroups(int id) throws SQLException {
        ArrayList<com.primoprogetto.database.Group> groups = new ArrayList();
        ResultSet rs = DBManager.executeSelectQuery(getMyGroups, id);
        try {
            while (rs.next()) {
                com.primoprogetto.database.Group group = new com.primoprogetto.database.Group();
                group.setID(rs.getInt(7));
                group.setName(rs.getString(8));
                group.setCreationDate(rs.getDate(10));
                group.setOwnerName(rs.getInt(7));
                groups.add(group);
            }
        } finally {
            rs.close();
        }
        return groups;
    }
    
    public static boolean isPartOfAGroup(int user_id, int group_id) throws SQLException{
        boolean isIt=false;
        int i=0;
        ResultSet rs = DBManager.executeSelectQuery(isPartOfAGroup, group_id, user_id);
        try {
            while (rs.next()) {
                i = rs.getInt(1);
            }
        } finally {
            rs.close();
        }
        if (i>=1){
            isIt=true;
        }
        return isIt;
    }
    
     public static ArrayList<User> getMembersNames(int id) throws SQLException {
        ArrayList<User> users = new ArrayList();
        ResultSet rs = DBManager.executeSelectQuery(getMembersNames, id);
        try {
            while (rs.next()) {
                User user = new User();
                user.setUsername(rs.getString(1));
                user.setId(rs.getInt(2));
                users.add(user);
            }
        } finally {
            rs.close();
        }
        return users;
    }
}
