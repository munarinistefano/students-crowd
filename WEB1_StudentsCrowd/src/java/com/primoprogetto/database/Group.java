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
public class Group {
    private int ID,OwnerID;
    private String name;
    private Date creationDate;
    
    public void setName(String name){
        this.name=name;
    }
    
    public void setID(int ID){
        this.ID=ID;
    }
    
    public void setOwnerID(int OwnerID){
        this.OwnerID=OwnerID;
    }
    
    public void setCreationDate(Date date){
        this.creationDate=date;
    }
    
    public String getName(){
        return this.name;
    }
    
    public Date getCreationDate(){
        return this.creationDate;
    }
    
    public int getID(){
        return this.ID;
    }
    
    public int getIDOwner(){
        return this.OwnerID;
    }
    
}
