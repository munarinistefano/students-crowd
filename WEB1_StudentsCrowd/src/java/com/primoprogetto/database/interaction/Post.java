/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.primoprogetto.database.interaction;

import com.primoprogetto.database.DBManager;
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
    private static ResultSet generatedKey = null;
    
    private static final String PostTable = "POSTS";
    
    private static int numberOfPosts;
    private static Date dateLastPost;
    
    private final static  String getAllPosts = "SELECT * FROM POSTS JOIN USERGROUP ON POSTS.GROUP_ID = ? AND USERGROUP.GROUP_ID = POSTS.GROUP_ID AND USERGROUP.USER_ID = ? LEFT JOIN POSTFILE ON POSTFILE.POST_ID = POSTS.ID";
    
    private final static  String addPost = "INSERT INTO " + PostTable + " (TEXT,GROUP_ID,USER_ID,DATE) VALUES (?,?,?,?)";
    private final static  String getDateOfLastPostOfAUser = "SELECT DATE FROM POSTS WHERE DATE IN (SELECT MAX(DATE) FROM POSTS WHERE USER_ID = ? AND GROUP_ID = ?) AND USER_ID = ? AND GROUP_ID = ?";
    private final static  String getNumberOfPostsOfAUser = "SELECT COUNT(*) FROM POSTS WHERE USER_ID = ? AND GROUP_ID = ?";
    
    public static ArrayList<com.primoprogetto.database.Post> getAllPosts(int group_id, int user_id) throws SQLException{
        ArrayList<com.primoprogetto.database.Post> posts = new ArrayList();
        ResultSet rs = DBManager.executeSelectQuery(getAllPosts,group_id,user_id);
        try {
            while (rs.next()) {
                com.primoprogetto.database.Post post = new com.primoprogetto.database.Post();
                post.setID(rs.getInt(1));
                post.setText(rs.getString(2));
                post.setGroupID(rs.getInt(3));
                post.setUserID(rs.getInt(4));
                post.setDate(rs.getDate(5));
                post.setFile(rs.getString(10));
                posts.add(post);
            }
        } finally {
            rs.close();
        }
        return posts;
    }
    
    public static int addPost(String text, int user_id, int group_id, Date date) throws SQLException{
        int id = 0;
        PreparedStatement stm = DBManager.executeInsertQuery(addPost);
        try {
            stm.setString(1, text);
            stm.setInt(2, group_id);
            stm.setInt(3, user_id);
            stm.setDate(4, date);
            stm.executeUpdate();
            id = Utils.getId(stm);
        } finally { // ricordarsi SEMPRE di chiudere i PreparedStatement in un blocco finally
            stm.close();
        }
        return id;
    }
    
    public static void setNumberOfPostsOfAUser(int userID, int groupID) throws SQLException{
        ResultSet rs = DBManager.executeSelectQuery(getNumberOfPostsOfAUser, userID, groupID);
        try {
            while (rs.next()) {
              numberOfPosts = rs.getInt(1);
            }
        } finally {
            rs.close();
        }
    }
    
    public static int getNumberOfPostsOfAUser(){
        return numberOfPosts;
    }
  
    public static void setDateOfLastPostOfAUser(int userID, int groupID, int user, int group) throws SQLException{
        ResultSet rs = DBManager.executeSelectQuery(getDateOfLastPostOfAUser, userID, groupID, user, group);
        try {
            while (rs.next()) {
                dateLastPost = rs.getDate(1);
            }
        } finally {
            rs.close();
        }
    }
    
    public static Date getDateOfLastPostOfAUser(){
        return dateLastPost;
    }
    
}
