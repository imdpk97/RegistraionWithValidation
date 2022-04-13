package com.example.registraionwithvalidation;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insert(User user);

    @Update
    void updateData(User user);

    @Delete
    void deleteUser(User user);

    @Query("SELECT * FROM user")
    List<User> getAllUserDetail();

    @Query("SELECT * FROM user WHERE userId = :userId")
    User getUserDetail(String userId);

}
