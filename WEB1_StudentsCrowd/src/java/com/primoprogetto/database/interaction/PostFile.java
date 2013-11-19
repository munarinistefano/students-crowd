/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.primoprogetto.database.interaction;

import com.primoprogetto.database.DBManager;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Stefano
 */
public class PostFile {
    private static final String PostFileTable = "POSTFILE";
    
    private final static  String insertPostFile = "INSERT INTO "+ PostFileTable + " (FILE,POST_ID) VALUES (?,?)";
    
    public static void addPostFile(String file, int post_id) throws SQLException{
        PreparedStatement stm = DBManager.executeInsertQuery(insertPostFile);
        try {
            stm.setString(1, file);
            stm.setInt(2, post_id);
            stm.executeUpdate();
        } finally { // ricordarsi SEMPRE di chiudere i PreparedStatement in un blocco finally
            stm.close();
        }
    }
}
