package com.warehousemanager.data.services;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.warehousemanager.data.db.entities.UserEntity;

import java.util.List;

public class FirebaseService {

  private FirebaseFirestore firebaseFirestore;

  private FirebaseUserCallback firebaseUserCallback = null;

  public FirebaseService(FirebaseFirestore firebaseFirestore) {
    this.firebaseFirestore = firebaseFirestore;
  }

  public void setUserCallback(FirebaseUserCallback firebaseUserCallback) {
    this.firebaseUserCallback = firebaseUserCallback;
  }

  public void addUser(UserEntity userEntity) {
    firebaseFirestore.collection("users")
      .document(userEntity.getUsername())
      .set(userEntity)
      .addOnSuccessListener(new OnSuccessListener<Void>() {
        @Override
        public void onSuccess(Void aVoid) {
          if(firebaseUserCallback != null) {
            firebaseUserCallback.onUserAddSuccess();
          }
        }
      })
      .addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
          if(firebaseFirestore != null) {
            firebaseUserCallback.onUserFetchFail(e);
          }
        }
      });
  }

  public void getUser(String username) {
    firebaseFirestore.collection("users")
      .whereEqualTo("username", username)
      .get()
      .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
        @Override
        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
          UserEntity user = null;
          for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
            String name = document.get("name").toString();
            String username = document.get("username").toString();
            String password = document.get("password").toString();
            String role = document.get("role").toString();
            user = new UserEntity(name, username, password, role);
          }
          if(firebaseUserCallback != null) {
            firebaseUserCallback.onUserFetchComplete(user);
          }
        }
      })
      .addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
          if(firebaseUserCallback != null) {
            firebaseUserCallback.onUserFetchFail(e);
          }
        }
      });
  }

}
