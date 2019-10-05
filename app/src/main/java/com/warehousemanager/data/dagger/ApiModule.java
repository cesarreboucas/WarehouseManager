package com.warehousemanager.data.dagger;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.warehousemanager.data.services.FirebaseService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApiModule {

  @Provides
  @Singleton
  Gson provideGson() {
    GsonBuilder gsonBuilder = new GsonBuilder();
    return gsonBuilder.create();
  }

  @Provides
  @Singleton
  FirebaseFirestore provideFirestore() {
    return FirebaseFirestore.getInstance();
  }

  @Provides
  FirebaseService provideFirebaseService(FirebaseFirestore firebaseFirestore) {
    return new FirebaseService(firebaseFirestore);
  }

}
