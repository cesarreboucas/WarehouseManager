package com.warehousemanager.data.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;
import java.util.ArrayList;


public class MovementOrder {
    private int id;

    @SerializedName("warehouse_receiver")
    private String warehouseReceiver;
    @SerializedName("warehouse_sender")
    private String warehouseSender;
    private String barcode;
    private String productName;
    private int quantity;
    private boolean sent;
    private boolean received;
    private Timestamp date;
}