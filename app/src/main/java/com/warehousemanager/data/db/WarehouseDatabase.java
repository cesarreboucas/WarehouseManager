package com.warehousemanager.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.warehousemanager.data.db.dao.UserDao;
import com.warehousemanager.data.db.entities.User;

import javax.inject.Singleton;

@Singleton
@Database(entities = {
  User.class
}, version = 1, exportSchema = false)
public abstract class WarehouseDatabase extends RoomDatabase {
  public abstract UserDao userDao();
}
