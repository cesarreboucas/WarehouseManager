package com.warehousemanager.data.db.entities;

import com.google.gson.annotations.SerializedName;

public class Report {

    @SerializedName("associate_id")
    private int associateId;
    @SerializedName("associate_name")
    private String associateName;
    private String order;
    private String message;
    private String product;
    @SerializedName("warehouse_name")
    private String warehouseName;
    @SerializedName("product_name")
    private String productName;
    private boolean solved;

    public Report() {
    }

    public int getAssociateId() {
        return associateId;
    }

    public void setAssociateId(int associateId) {
        this.associateId = associateId;
    }

    public String getAssociateName() {
        return associateName;
    }

    public void setAssociateName(String associateName) {
        this.associateName = associateName;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
