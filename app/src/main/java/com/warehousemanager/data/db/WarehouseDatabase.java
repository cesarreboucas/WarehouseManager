package com.warehousemanager.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.warehousemanager.data.db.entities.UserEntity;

import javax.inject.Singleton;

@Singleton
@Database(entities = {
  UserEntity.class
}, version = 1, exportSchema = false)
public abstract class WarehouseDatabase extends RoomDatabase {
  public abstract UserDao userDao();
}
