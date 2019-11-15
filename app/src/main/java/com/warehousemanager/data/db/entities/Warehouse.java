package com.warehousemanager.data.db.entities;

import android.arch.persistence.room.Entity;

import com.google.gson.annotations.SerializedName;
import com.google.type.LatLng;

import java.io.Serializable;
import java.util.ArrayList;

@Entity(tableName = "warehouse_table")
public class Warehouse implements Serializable {

    @SerializedName("name")
    private String name;

    @SerializedName("capacity")
    private int capacity;

    @SerializedName("location")
    private String location;

    @SerializedName("workerList")
    private ArrayList<User> workerList = new ArrayList<>();

    @SerializedName("productList")
    private ArrayList<Product> productList = new ArrayList<>();

    private int workerCount;


    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<User> getWorkerList() {
        return workerList;
    }

    public void setWorkerList(ArrayList<User> workerList) {
        this.workerList = workerList;
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<Product> productList) {
        this.productList = productList;
    }

    public int getWorkerCount() {
        return workerList.size();
    }

    public void setWorkerCount(int workerCount) {
        this.workerCount = workerCount;
    }
}
