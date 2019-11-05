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

    private User username;

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
}
