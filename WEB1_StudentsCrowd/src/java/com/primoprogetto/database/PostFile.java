/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primoprogetto.database;

/**
 *
 * @author Stefano
 */
public class PostFile {
    private int ID,PostID;
    private String file;
    
    public void setID(int ID){
        this.ID=ID;
    }
    
    
    public void setPostID(int PostID){
        this.PostID=PostID;
    }
    
    public void setFile(String file){
        this.file=file;
    }
    
    public int getID(){
        return this.ID;
    }
    
    public String getFile(){
        return this.file;
    }
    
    public int getPostID(){
        return this.PostID;
    }
}
