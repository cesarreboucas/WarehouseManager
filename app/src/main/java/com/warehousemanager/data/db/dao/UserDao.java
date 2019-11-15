package com.warehousemanager.data.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.warehousemanager.data.db.entities.User;

@Dao
public interface UserDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  long insertUser(User user);

  @Query("SELECT * FROM user_table LIMIT 1")
  User getUser();

}
