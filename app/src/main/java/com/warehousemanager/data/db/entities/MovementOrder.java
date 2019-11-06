package com.warehousemanager.data.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "movementOrder_table")
public class MovementOrder
{
    @PrimaryKey(autoGenerate = true)
    private int KEY;

    private Warehouse originLocation;
    private Warehouse destinationLocation;
    private ArrayList<Product> itemsList;
    private Product item;
    private User username;
    private String transferType;

    public int getKEY() {
        return KEY;
    }

    public Warehouse getOriginLocation() {
        return originLocation;
    }

    public void setOriginLocation(Warehouse originLocation) {
        this.originLocation = originLocation;
    }

    public Warehouse getDestinationLocation() {
        return destinationLocation;
    }

    public void setDestinationLocation(Warehouse destinationLocation) {
        this.destinationLocation = destinationLocation;
    }

    public ArrayList<Product> getItemsList() {
        return itemsList;
    }

    public void setItemsList(ArrayList<Product> itemsList) {
        this.itemsList = itemsList;
    }

    public User getUsername() {
        return username;
    }

    public void setUsername(User username) {
        this.username = username;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public Product getItem() {
        return item;
    }

    public void setItem(Product item) {
        this.item = item;
    }
}
