package com.warehousemanager.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.warehousemanager.data.db.entities.UserEntity;

@Dao
public interface UserDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  long insertUser(UserEntity user);

  @Query("SELECT * FROM `user_table` WHERE id = 0")
  UserEntity getUser();

}
