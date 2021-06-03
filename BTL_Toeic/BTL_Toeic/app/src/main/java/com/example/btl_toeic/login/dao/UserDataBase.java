package com.example.btl_toeic.login.dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.btl_toeic.login.User;
// tạo bảng theo những thuojc tính đã khai báo bên user
@Database(entities = {User.class}, exportSchema = false, version = 1)
public abstract class UserDataBase extends RoomDatabase {
    public abstract UserDao userDao();
}
