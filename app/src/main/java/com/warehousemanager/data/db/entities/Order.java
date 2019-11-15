package com.warehousemanager.data.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Order
{

    private int id;
    private int user_id;
    private String warehouse_key;
    private Timestamp ordertime;
    private Warehouse pickupLocation;
    private List<Product> products;

    private double total = 0;
    private double profit = 0;

    /*private enum Status
    {
        INTRANSIT,ARRIVED,PAID,CANCLED,ERROR
    }*/

    public Order(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getWarehouse_key() {
        return warehouse_key;
    }

    public void setWarehouse_key(String warehouse_key) {
        this.warehouse_key = warehouse_key;
    }

    public Timestamp getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(Timestamp ordertime) {
        this.ordertime = ordertime;
    }

    public Warehouse getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(Warehouse pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
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

    public String getFormatedOrderTime() {
        Date d = new Date(ordertime.getTime());
        DateFormat f = new SimpleDateFormat("dd-MMM-yyyy");
        return f.format(d);
    }

}
