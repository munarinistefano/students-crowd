/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.primoprogetto.database.interaction;

import com.primoprogetto.database.DBManager;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Stefano
 */
public class Group {
    
    private static final String GroupTable = "GROUPS";
    
    private final static String getAllGroups = "SELECT * FROM "+ GroupTable;
    private final static  String insertQuery = "INSERT INTO "+ GroupTable + " (NAME,OWNER_ID,CREATION_DATE) VALUES (?,?,?)";
    private final static  String getIDcreatedGroup = "SELECT id FROM "+ GroupTable + " WHERE name = ? AND owner_id = ? AND creation_date = ?";
    
    
    public static ArrayList<com.primoprogetto.database.Group> getAllGroups() throws SQLException {
        ArrayList<com.primoprogetto.database.Group> groups = new ArrayList();
        ResultSet rs = DBManager.executeSelectQuery(getAllGroups);
        try {
            while (rs.next()) {
                com.primoprogetto.database.Group group = new com.primoprogetto.database.Group();
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
    public static com.primoprogetto.database.Group getGroup() throws SQLException {
        com.primoprogetto.database.Group group = new com.primoprogetto.database.Group();
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
    public static int addGroup(String name, int ownerID, Date creationDate) throws SQLException {
        int id = 0;
        PreparedStatement stm = DBManager.executeInsertQuery(insertQuery);
        try {
            //stm.setString(1, DBName);
            stm.setString(1, name);
            stm.setInt(2, ownerID);
            stm.setDate(3, creationDate);
            stm.executeUpdate();
            id = Utils.getId(stm);
        } finally { // ricordarsi SEMPRE di chiudere i PreparedStatement in un blocco finally
            stm.close();
        }
        return id;
    }

    
    
}
