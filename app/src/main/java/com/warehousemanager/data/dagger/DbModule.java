package com.warehousemanager.data.dagger;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.support.annotation.NonNull;

import com.warehousemanager.data.db.dao.UserDao;
import com.warehousemanager.data.db.WarehouseDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DbModule {

  @Provides
  @Singleton
  WarehouseDatabase provideDatabase(@NonNull Application application) {
    return Room.databaseBuilder(application, WarehouseDatabase.class, "WarehouseManager.db")
      .allowMainThreadQueries().fallbackToDestructiveMigration().build();
  }

  @Provides
  @Singleton
  UserDao provideUserDao(@NonNull WarehouseDatabase warehouseDatabase) {
    return warehouseDatabase.userDao();
  }

}
