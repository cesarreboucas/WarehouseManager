package com.warehousemanager.data.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;
import java.util.ArrayList;


public class MovementOrder {
    private String id;

    @SerializedName("warehouse_receiver")
    private String warehouseReceiver;
    @SerializedName("warehouse_sender")
    private String warehouseSender;
    private String barcode;
    private String productName;
    private int quantity;
    private boolean sent;
    private boolean received;
    private long date;

    public MovementOrder(){}

    public MovementOrder(String warehouseReceiver, String warehouseSender, String barcode, String productName, int quantity, boolean sent, boolean received, long date) {
        this.warehouseReceiver = warehouseReceiver;
        this.warehouseSender = warehouseSender;
        this.barcode = barcode;
        this.productName = productName;
        this.quantity = quantity;
        this.sent = sent;
        this.received = received;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWarehouseReceiver() {
        return warehouseReceiver;
    }

    public void setWarehouseReceiver(String warehouseReceiver) {
        this.warehouseReceiver = warehouseReceiver;
    }

    public String getWarehouseSender() {
        return warehouseSender;
    }

    public void setWarehouseSender(String warehouseSender) {
        this.warehouseSender = warehouseSender;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
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

    public boolean getSentStatus() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public boolean getReceivedStatus() {
        return received;
    }

    public void setReceived(boolean received) {
        this.received = received;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}