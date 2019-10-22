package com.warehousemanager.data.services;

import com.warehousemanager.data.db.entities.User;

public interface FirebaseUserCallback {
  void onUserAddSuccess();
  void onUserAddFail(Exception ex);
  void onUserFetchComplete(User user);
  void onUserFetchFail(Exception ex);
}
