package com.warehousemanager.data.db.entities;

import com.google.gson.annotations.SerializedName;

public class prodTransactions {

    @SerializedName("sale_price")
    private String product_code;
    private int quantity;
    private boolean sent;
    private boolean received;
    private String warehouse_receiver;
    private String warehouse_sender;

    public prodTransactions() {
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
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

    public String getWarehouse_receiver() {
        return warehouse_receiver;
    }

    public void setWarehouse_receiver(String warehouse_receiver) {
        this.warehouse_receiver = warehouse_receiver;
    }

    public String getWarehouse_sender() {
        return warehouse_sender;
    }

    public void setWarehouse_sender(String warehouse_sender) {
        this.warehouse_sender = warehouse_sender;
    }
}
