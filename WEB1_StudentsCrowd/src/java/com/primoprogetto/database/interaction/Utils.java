/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.primoprogetto.database.interaction;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Stefano
 */
public class Utils {
    private static ResultSet generatedKey = null;
    
    /*
     * Get id of just created group. Used to get the id of the just created Group
     */
    public static int getId(PreparedStatement stm) throws SQLException {
        int id = 0;
        try {
            generatedKey = stm.getGeneratedKeys();
        
            if (generatedKey.next()) {
                id = generatedKey.getInt(1);
            } else {
                throw new SQLException("add failed, no generated key obtained.");
            }
        } finally {
              if (generatedKey != null) try {generatedKey.close();} catch (SQLException logOrIgnore) {}  
        }
        return id;
    }
    
}
