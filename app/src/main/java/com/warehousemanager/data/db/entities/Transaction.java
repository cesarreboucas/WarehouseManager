package com.warehousemanager.data.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "transaction_table")
public class Transaction
{
    @PrimaryKey(autoGenerate = true)
    private int KEY;

    private String client;
    private ArrayList <Product> productList;
    private Warehouse pickupLocation;
    private enum Status
    {
        INTRANSIT,ARRIVED,PAID,CANCLED,ERROR
    }

    public int getKEY() {
        return KEY;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<Product> productList) {
        this.productList = productList;
    }

    public Warehouse getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(Warehouse pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

}
