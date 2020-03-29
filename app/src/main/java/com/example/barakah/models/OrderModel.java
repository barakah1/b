package com.example.barakah.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderModel implements Serializable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("herb_id")
    @Expose
    private String herb_id;
    @SerializedName("vendor_id")
    @Expose
    private String vendor_id;
    @SerializedName("quantity")
    @Expose
    private int quantity;
    @SerializedName("herb_name")
    @Expose
    private String herb_name;
    @SerializedName("vendor_name")
    @Expose
    private String vendor_name;
    //order status 0=approval,1=in process,2=delivered
    @SerializedName("order_status")
    @Expose
    private String order_status;
    @SerializedName("order_price")
    @Expose
    private String order_price;
    @SerializedName("herb_type")
    @Expose
    private String herb_type;
    @SerializedName("order_id")
    @Expose
    private String order_id;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHerb_id() {
        return herb_id;
    }

    public void setHerb_id(String herb_id) {
        this.herb_id = herb_id;
    }

    public String getVendor_id() {
        return vendor_id;
    }

    public void setVendor_id(String vendor_id) {
        this.vendor_id = vendor_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getHerb_name() {
        return herb_name;
    }

    public void setHerb_name(String herb_name) {
        this.herb_name = herb_name;
    }

    public String getVendor_name() {
        return vendor_name;
    }

    public void setVendor_name(String vendor_name) {
        this.vendor_name = vendor_name;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getOrder_price() {
        return order_price;
    }

    public void setOrder_price(String order_price) {
        this.order_price = order_price;
    }

    public String getHerb_type() {
        return herb_type;
    }

    public void setHerb_type(String herb_type) {
        this.herb_type = herb_type;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }
}
