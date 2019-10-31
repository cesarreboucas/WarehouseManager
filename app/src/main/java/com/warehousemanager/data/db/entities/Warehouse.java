package com.warehousemanager.data.db.entities;

import android.arch.persistence.room.Entity;

import com.google.type.LatLng;

import java.util.ArrayList;

@Entity(tableName = "warehouse_table")
public class Warehouse {

    private String name;
    private int capacity;
    private LatLng location;
    private ArrayList<User> workerList;
    private ArrayList<Product> productList;

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
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

    public int getWorkerCount()
    {
        ArrayList<User> users = getWorkerList();
        int i = 0;
        for (User u: users)
        {
            if (u.getAssignment().equals(getName()))
            i++;
        }
        return i;
    }
}
