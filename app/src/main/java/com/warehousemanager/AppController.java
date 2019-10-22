package com.warehousemanager;

import android.app.Application;

import com.warehousemanager.data.db.WarehouseDatabase;

public class AppController extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    WarehouseDatabase.getAppDatabase(getApplicationContext());
  }
}
