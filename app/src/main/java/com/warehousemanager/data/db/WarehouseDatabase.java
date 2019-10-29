package com.warehousemanager.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.warehousemanager.data.db.dao.UserDao;
import com.warehousemanager.data.db.entities.User;

import javax.inject.Singleton;

@Singleton
@Database(entities = {
  User.class
}, version = 2, exportSchema = false)
public abstract class WarehouseDatabase extends RoomDatabase {

  private static WarehouseDatabase INSTANCE;

  public abstract UserDao userDao();

  public static WarehouseDatabase getAppDatabase(Context context) {
    if (INSTANCE == null) {
      INSTANCE =
              Room.databaseBuilder(context.getApplicationContext(),
                      WarehouseDatabase.class,
                      "user-database")
                      // allow queries on the main thread.
                      // Don't do this on a real app! See PersistenceBasicSample for an example.
                      .allowMainThreadQueries()
                      .fallbackToDestructiveMigration()
                      .build();
    }
    return INSTANCE;
  }

  public static void destroyInstance() {
    INSTANCE = null;
  }
}
