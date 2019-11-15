package com.warehousemanager.data.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    private transient double total = 0;
    private transient double profit = 0;
    private transient boolean outOfStock = false;


    public ClientOrder(Long clientID, String warehouseKey, List<Product> products, String ordertime) {
        this.clientID = clientID;
        this.warehouseKey = warehouseKey;
        this.products = products;
        this.ordertime = ordertime;
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

    public int getDone() {
        return done;
    }

    public void setDone(int done) {
        this.done = done;
    }

    public int getReady() {
        return ready;
    }

    public void setReady(int ready) {
        this.ready = ready;
    }

    public double getTotal() {
        return total;
    }

    public double getProfit() {
        return profit;
    }

    public void updateTotals() {
        this.total = 0;
        this.profit = 0;

        for(Product p : products) {
            this.total += p.getTotal();
            this.profit += p.getTotal()-p.getTotalCost();
        }
    }

    public boolean isOutOfStock() {
        return outOfStock;
    }

    public void setOutOfStock(boolean outOfStock) {
        this.outOfStock = outOfStock;
    }

}
