package com.warehousemanager.data.services;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.warehousemanager.data.db.entities.User;

public class FirebaseService {

  private FirebaseFirestore firebaseFirestore;

  private FirebaseUserCallback firebaseUserCallback = null;

  public FirebaseService() {
    this.firebaseFirestore = FirebaseFirestore.getInstance();
  }

  public void setUserCallback(FirebaseUserCallback firebaseUserCallback) {
    this.firebaseUserCallback = firebaseUserCallback;
  }

  public void addUser(User user) {
    firebaseFirestore.collection("users")
      .document(user.getUsername())
      .set(user)
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
          User user = null;
          for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
            String name = document.get("name") == null ? "" : document.get("name").toString();
            String username = document.get("username") == null ? "" : document.get("username").toString();
            String password = document.get("password") == null ? "" : document.get("password").toString();
            String role = document.get("role") == null ? "" : document.get("role").toString();
            String question = document.get("question") == null ? "" : document.get("question").toString();
            String answer = document.get("answer") == null ? "" : document.get("answer").toString();
            user = new User(name, username, password, role, question, answer);
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
