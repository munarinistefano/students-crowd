/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primoprogetto.database;

import java.sql.Date;

/**
 *
 * @author Stefano
 */
public class Post {
    private int ID,GroupID,UserID;
    private Date date;
    private String text;
    
    public void setID(int ID){
        this.ID=ID;
    }
    
    public void setUserID(int UserID){
        this.UserID=UserID;
    }
    
    public void setGroupID(int GroupId){
        this.GroupID=GroupID;
    }
    
    public void setRole(Date date){
        this.date=date;
    }
    
    public void setText(String text){
        this.text=text;
    }
    
    public int getID(){
        return this.ID;
    }
    
    public int getUserID(){
        return this.UserID;
    }
    
    public int getGroupId(){
        return this.GroupID;
    }
    
    public String getText(){
        return this.text;
    }
    
    public Date getDate(){
        return this.date;
    }
}
