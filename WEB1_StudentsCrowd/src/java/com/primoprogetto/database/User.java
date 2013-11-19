/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primoprogetto.database;

/**
 *
 * @author Stefano
 */
public class User {
    private String username,password;
    private int ID;
    
    public void setUsername(String username){
        this.username=username;
    }
    
    public void setPassword(String password){
        this.password=password;
    }
    
    public void setId(int ID){
        this.ID=ID;
    }
    
    public String getUsername(){
        return this.username;
    }
    
    public String getPassword(){
        return this.password;
    }
    
    public int getID(){
        return this.ID;
    }
}