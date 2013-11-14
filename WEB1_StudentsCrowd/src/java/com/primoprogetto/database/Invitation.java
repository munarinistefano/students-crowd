/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primoprogetto.database;

/**
 *
 * @author Stefano
 */
public class Invitation {
    private int GroupID,UserID;
    
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
}
