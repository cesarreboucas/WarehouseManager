package com.warehousemanager.data.internal;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.warehousemanager.data.db.entities.Product;
import com.warehousemanager.data.db.entities.User;
import com.warehousemanager.data.db.entities.Warehouse;
import com.warehousemanager.data.internal.model.UserRow;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class JsonReader {

  Gson gson;
  Context context;

  public JsonReader(Context context) {
    this.gson = new Gson();
    this.context = context;
  }

  public List<User> getUser() {
    List<User> userRows = new ArrayList<>();
    try {
      InputStream is = context.getAssets().open("user_list_template.json");
      int size = is.available();
      byte[] buffer = new byte[size];
      is.read(buffer);
      is.close();
      String json = new String(buffer, StandardCharsets.UTF_8);

      userRows = gson.fromJson(json, new TypeToken<List<UserRow>>() {
      }.getType());
    } catch (IOException ex) {
      ex.printStackTrace();
      return userRows;
    }
    return userRows;
  }

  public List<Product> getProducts() {
    List<Product> products = new ArrayList<>();
    try {
      InputStream is = context.getAssets().open("products_list_template.json");
      int size = is.available();
      byte[] buffer = new byte[size];
      is.read(buffer);
      is.close();
      String json = new String(buffer, StandardCharsets.UTF_8);
      products = gson.fromJson(json, new TypeToken<List<Product>>() {
      }.getType());

    } catch (IOException e) {
      e.printStackTrace();
    }
    return products;
  }

  public List<Warehouse> getWarehouse() {
    List<Warehouse> Warehouse = new ArrayList<>();
    try {
      InputStream is = context.getAssets().open("products_list_template.json");
      int size = is.available();
      byte[] buffer = new byte[size];
      is.read(buffer);
      is.close();
      String json = new String(buffer, StandardCharsets.UTF_8);
      Warehouse = gson.fromJson(json, new TypeToken<List<Product>>() {
      }.getType());

    } catch (IOException e) {
      e.printStackTrace();
    }
    return Warehouse;
  }
}
