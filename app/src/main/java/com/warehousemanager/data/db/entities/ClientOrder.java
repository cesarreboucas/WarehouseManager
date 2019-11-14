package com.warehousemanager.data.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;

@Entity(tableName = "clientOrder_table")
public class ClientOrder implements Serializable
{
    @PrimaryKey(autoGenerate = true)
    private int KEY;

    private User client;
    private ArrayList <Product> productList;
    private Warehouse pickupLocation;
    private enum Status
    {
        AVAILABLE, DELIVERED, PAID, CANCELED, ERROR
        //INTRANSIT,ARRIVED,PAID,CANCLED,ERROR
    }

    public int getKEY() {
        return KEY;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
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
