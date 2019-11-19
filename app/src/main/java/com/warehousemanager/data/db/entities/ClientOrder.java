package com.warehousemanager.data.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ClientOrder implements Serializable
{
    private int id;
    @SerializedName("user_id")
    private Long clientID;
    @SerializedName("warehouse_key")
    private String warehouseKey;
    private List<Product> products;
    private String ordertime;
    private int done; // completed
    private int ready; // available

    public ClientOrder(Long clientID, String warehouseKey, List<Product> products, String ordertime) {
        this.clientID = clientID;
        this.warehouseKey = warehouseKey;
        this.products = products;
        this.ordertime = ordertime;
        this.done = 0;
        this.ready = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getClientID() {
        return clientID;
    }

    public void setClientID(Long clientID) {
        this.clientID = clientID;
    }

    public String getWarehouseKey() {
        return warehouseKey;
    }

    public void setWarehouseKey(String warehouseKey) {
        this.warehouseKey = warehouseKey;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;
    }

    public int isDone() {
        return done;
    }

    public void setDone(int done) {
        this.done = done;
    }

    public int isReady() {
        return ready;
    }

    public void setReady(int ready) {
        this.ready = ready;
    }
}
