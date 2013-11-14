/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primoprogetto.database;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 *
 * @author Stefano
 */
public class DBManager implements Serializable{

    private static Connection con;
    
    //Database tables name
    private static final String UserTable = "USERS";
    private static final String GroupTable = "GROUPS";
    private static final String UserGroupTable = "USERGROUP";
    private static final String PostTable = "POSTS";
    private static final String PostFileTable = "POSTFILE";
    private static final String InvitationTable = "INVITATIONS";
    
    
    public DBManager(String dburl) throws SQLException{
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver", true, getClass().getClassLoader());
        } catch(Exception e) {
            throw new RuntimeException(e.toString(), e);
        }
        //per stabilire la connessione al DB
        Connection con = DriverManager.getConnection(dburl);
        this.con = con;   
    }
    
    public static void shutdown() { //close the connection to the database; called on "contextDestroyed" method of ServletContextListener
        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).info(ex.getMessage());
        }
    }
    
    /**
     * Authenticate a user based on username & password
     *
     * @param username
     * @param password
     * @return null if user isn't in the database, user Object if user exists in DB and it has been authenticated
     */
    public User authenticate(String username, String password) throws SQLException {
        // usare SEMPRE i PreparedStatement, anche per query banali.
        // *** MAI E POI MAI COSTRUIRE LE QUERY CONCATENANDO STRINGHE !!!!
        PreparedStatement stm = con.prepareStatement("SELECT * FROM " + UserTable + " WHERE username = ? AND password = ?");
        try {
            stm.setString(1, username);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();
            try {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt(1));
                    user.setUsername(username);
                    user.setPassword(password);
                    return user;
                } else {
                    return null;
                }
            } finally {
                // ricordarsi SEMPRE di chiudere i ResultSet in un blocco
                rs.close();
            }
        } finally { // ricordarsi SEMPRE di chiudere i PreparedStatement in un blocco finally
                 stm.close();
        }
    }

    public ArrayList<User> getAllUser() throws SQLException {
        ArrayList<User> users = new ArrayList();
        PreparedStatement stm = con.prepareStatement("SELECT * FROM " + UserTable);
        try {
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt(1));
                    user.setUsername(rs.getString(2));
                    user.setPassword(rs.getString(3));
                    users.add(user);
                }
            } finally {
                // ricordarsi SEMPRE di chiudere i ResultSet in un blocco
                rs.close();
            }
        } finally { // ricordarsi SEMPRE di chiudere i PreparedStatement in un blocco finally
                 stm.close();
        }
        return users;
    }

    public void getInvitation(int id) {
        
    }
    
    public void addGroup(String name, int ownerID, Date creationDate) throws SQLException {
        System.out.println("addUser");
        PreparedStatement stm = con.prepareStatement("INSERT INTO "+ GroupTable + " (NAME,OWNER_ID,CREATION_DATE) VALUES (?,?,?)");
        try {
            //stm.setString(1, DBName);
            stm.setString(1, name);
            stm.setInt(2, ownerID);
            stm.setDate(3, creationDate);
            stm.executeUpdate();
        } finally { // ricordarsi SEMPRE di chiudere i PreparedStatement in un blocco finally
            stm.close();
        }
    }
    
    public static ResultSet executeSelectQuery(String query) throws SQLException {
      PreparedStatement stm = con.prepareStatement(query);
      ResultSet rs = stm.executeQuery();
      return rs;
    }
    
    public static ResultSet executeSelectQuery(String query, int id) throws SQLException {
      PreparedStatement stm = con.prepareStatement(query);
      stm.setInt(1, id);
      ResultSet rs = stm.executeQuery();
      return rs;
    }
    
    public static PreparedStatement executeInsertQuery(String query) throws SQLException {
      return con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
    }
    
}