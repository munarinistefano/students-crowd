/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primoprogetto.database;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Stefano
 */
public class Post {
    private int ID,GroupID,UserID;
    private Date date;
    private String text;
    
    private static final String PostTable = "POSTS";
    
    private String getAllPosts = "SELECT * FROM "+ PostTable + " WHERE GROUP_ID = ?";
    private final String addPost = "INSERT INTO " + PostTable + " (TEXT,GROUP_ID,USER_ID,DATE) VALUES (?,?,?,?)";
    
    public void setID(int ID){
        this.ID=ID;
    }
    
    public void setUserID(int UserID){
        this.UserID=UserID;
    }
    
    public void setGroupID(int GroupId){
        this.GroupID=GroupId;
    }
    
    public void setDate(Date date){
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
    
    public ArrayList<Post> getAllPosts(int group_id) throws SQLException{
        ArrayList<Post> posts = new ArrayList();
        System.err.println("Group_id: "+group_id);
        ResultSet rs = DBManager.executeSelectQuery(getAllPosts,group_id);
        try {
            while (rs.next()) {
                Post post = new Post();
                post.setID(rs.getInt(1));
                post.setText(rs.getString(2));
                post.setGroupID(rs.getInt(3));
                System.err.println("Group_id: "+rs.getInt(3));
                post.setUserID(rs.getInt(4));
                post.setDate(rs.getDate(5));
                posts.add(post);
            }
        } finally {
            rs.close();
        }
        return posts;
    }
    
    public void addPost(String text, int user_id, int group_id, Date date) throws SQLException{
        PreparedStatement stm = DBManager.executeInsertQuery(addPost);
        try {
            stm.setString(1, text);
            stm.setInt(2, group_id);
            stm.setInt(3, user_id);
            stm.setDate(4, date);
            stm.executeUpdate();
        } finally { // ricordarsi SEMPRE di chiudere i PreparedStatement in un blocco finally
            stm.close();
        }
    }
}
