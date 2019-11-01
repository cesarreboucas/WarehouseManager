package com.warehousemanager.data.db.entities;

import android.arch.persistence.room.Entity;

import java.io.Serializable;

@Entity(tableName = "product_table")
public class Product implements Serializable {

    private String name;
    private String description;
    private double cost;
    private double price;
    private String barcode;
    private String picture;
    //private int quantity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    /*public int getQuantity() {
        return quantity;
    }*/

    /*public void setQuantity(int quantity) {
        this.quantity = quantity;
    }*/

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
