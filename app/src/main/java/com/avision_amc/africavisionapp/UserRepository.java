package com.avision_amc.africavisionapp;

import androidx.lifecycle.LiveData;

import java.util.List;

public class UserRepository {

    private static UserDao userDao;

    public static void initialize(AppDatabase appDatabase) {
        userDao = appDatabase.userDao();
    }

    public static void insertComment(UserModel comment) {
        userDao.insert(comment);
    }

    public static LiveData<List<UserModel>> getAllComments() {
        return userDao.getAll();
    }

    public static void deleteAllComments() {
        userDao.deleteAll();
    }
}
