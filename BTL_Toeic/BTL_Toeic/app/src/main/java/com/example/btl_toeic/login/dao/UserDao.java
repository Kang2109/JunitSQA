package com.example.btl_toeic.login.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


import com.example.btl_toeic.login.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("select * from User where email = :email and password = :password")
    List<User> getUser(String email, String password);

    @Insert
    void insert(User user);
}
