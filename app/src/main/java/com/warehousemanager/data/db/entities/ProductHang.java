package com.warehousemanager.data.db.entities;

import java.util.List;

public class ProductHang {

    private String barcode;
    private String name;
    private List<WarehouseHang> warehouses;

    public ProductHang(String barcode, String name, List<WarehouseHang> warehouses) {
        this.barcode = barcode;
        this.name = name;
        this.warehouses = warehouses;
    }

    public ProductHang(){}

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<WarehouseHang> getWarehouses() {
        return warehouses;
    }

    public void setWarehouses(List<WarehouseHang> warehouses) {
        this.warehouses = warehouses;
    }
}
