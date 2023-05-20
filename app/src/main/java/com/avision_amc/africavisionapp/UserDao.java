package com.avision_amc.africavisionapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user_table")
    LiveData<List<UserModel>> getAll();

    @Insert
    long insert(UserModel user);

    @Query("DELETE FROM user_table")
    void deleteAll();

}
