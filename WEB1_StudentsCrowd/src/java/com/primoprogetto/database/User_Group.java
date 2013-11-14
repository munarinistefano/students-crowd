/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primoprogetto.database;

/**
 *
 * @author Stefano
 */
public class User_Group {
    private int UserID,GroupID;
    private int role;                       //0 normal user - 1 administrator
    
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
    
}
