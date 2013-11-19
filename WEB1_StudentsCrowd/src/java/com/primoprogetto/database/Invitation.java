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
    private String groupName,owner;
    private int state;                  //o pending - 1 accepted - 2 refused (DEFAULT 0)
    
    
    
    
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
}

