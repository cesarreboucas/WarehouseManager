package com.warehousemanager.data.db.entities;

public class WarehouseHang {

    private String warehouse_key;
    private int quantity_sold;
    private int quantity_in_stock;
    private int quantity_compromised;
    private int quantity_future_movs;

    public int getFreeQuantity() {
        return quantity_in_stock + quantity_compromised;
    }

    public String getWarehouse_key() {
        return warehouse_key;
    }

    public void setWarehouse_key(String warehouse_key) {
        this.warehouse_key = warehouse_key;
    }

    public int getQuantity_sold() {
        return quantity_sold;
    }

    public void setQuantity_sold(int quantity_sold) {
        this.quantity_sold = quantity_sold;
    }

    public int getQuantity_in_stock() {
        return quantity_in_stock;
    }

    public void setQuantity_in_stock(int quantity_in_stock) {
        this.quantity_in_stock = quantity_in_stock;
    }

    public int getQuantity_compromised() {
        return quantity_compromised;
    }

    public void setQuantity_compromised(int quantity_compromised) {
        this.quantity_compromised = quantity_compromised;
    }

    public int getQuantity_future_movs() {
        return quantity_future_movs;
    }

    public void setQuantity_future_movs(int quantity_future_movs) {
        this.quantity_future_movs = quantity_future_movs;
    }
}
