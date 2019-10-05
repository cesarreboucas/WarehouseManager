package com.warehousemanager.data.services;

import com.google.firebase.firestore.DocumentReference;
import com.warehousemanager.data.db.entities.UserEntity;

public interface FirebaseUserCallback {
  void onUserAddSuccess();
  void onUserAddFail(Exception ex);
  void onUserFetchComplete(UserEntity userEntity);
  void onUserFetchFail(Exception ex);
}
