package com.example.barakah.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderModel implements Serializable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("order_key")
    @Expose
    private String order_key;

    @SerializedName("vendor_id")
    @Expose
    private String vendor_id;
 @SerializedName("total_price")
    @Expose
    private String total_price;

   /* @SerializedName("herb_id")
    @Expose
    private String herb_id;
    @SerializedName("quantity")
    @Expose
    private int quantity;
    @SerializedName("herb_name")
    @Expose
    private String herb_name;
    @SerializedName("order_price")
    @Expose
    private String order_price;
    @SerializedName("herb_type")
    @Expose
    private String herb_type;*/


    @SerializedName("vendor_name")
    @Expose
    private String vendor_name;
    //order status 0=approval,1=in process,2=delivered
    @SerializedName("order_status")
    @Expose
    private String order_status;


    @SerializedName("order_id")
    @Expose
    private String order_id;

    @SerializedName("delivery_type")
    @Expose
    private String delivery_type;

    /*    @SerializedName("delivery_type")
      @Expose*/
    private ArrayList<OrderSubItemModel> herbs;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVendor_id() {
        return vendor_id;
    }

    public void setVendor_id(String vendor_id) {
        this.vendor_id = vendor_id;
    }
/*
    public String getHerb_id() {
        return herb_id;
    }

    public void setHerb_id(String herb_id) {
        this.herb_id = herb_id;
    }*/

    public String getOrder_key() {
        return order_key;
    }

    public void setOrder_key(String order_key) {
        this.order_key = order_key;
    }
/*
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
    }*/

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

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }
/*
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
    }*/

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getDelivery_type() {
        return delivery_type;
    }

    public void setDelivery_type(String delivery_type) {
        this.delivery_type = delivery_type;
    }

    public ArrayList<OrderSubItemModel> getHerbs() {
        return herbs;
    }

    public void setHerbs(ArrayList<OrderSubItemModel> herbs) {
        this.herbs = herbs;
    }
}
