package com.avision_amc.africavisionapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table")
public class UserModel {

        @PrimaryKey(autoGenerate = true)
        private long id;

        @ColumnInfo(name = "nickname")
        private String nickname;

        @ColumnInfo(name = "comments")
        private String comments;

        //Contractor
    public UserModel(String nickname, String comments) {
        this.nickname = nickname;
        this.comments = comments;
    }


    //Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

}
