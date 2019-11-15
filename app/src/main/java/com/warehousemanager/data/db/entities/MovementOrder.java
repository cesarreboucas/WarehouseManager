package com.warehousemanager.data.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;


public class MovementOrder {
    private int ID;

    private String warehouse_receiver;
    private String Warehouse_sender;
    private String user;
    private String productKey;
    private String productName;

    private int quantity;

    private boolean sent;
    private boolean received;
    private boolean error;

    public int getID() {
        return ID;
    }

    public String getWarehouse_receiver() {
        return warehouse_receiver;
    }

    public void setWarehouse_receiver(String warehouse_receiver) {
        this.warehouse_receiver = warehouse_receiver;
    }

    public String getWarehouse_sender() {
        return Warehouse_sender;
    }

    public void setWarehouse_sender(String warehouse_sender) {
        Warehouse_sender = warehouse_sender;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public boolean isReceived() {
        return received;
    }

    public void setReceived(boolean received) {
        this.received = received;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}