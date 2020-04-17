package com.example.barakah.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StoreItemModel implements Serializable {


    @SerializedName("herb_id")
    @Expose
    String herb_id;
    @SerializedName("id")
    @Expose
    String id;
    @SerializedName("capsule_price")
    @Expose
    int capsule_price;
    @SerializedName("capsule_quantity")
    @Expose
    int capsule_quantity;

   /* @SerializedName("stock_quantity")
    @Expose
    String stock_quantity;*/

    @SerializedName("raw_price")
    @Expose
    int raw_price;

    @SerializedName("raw_quantity")
    @Expose
    int raw_quantity;

    /*    @SerializedName("price")
    @Expose
    String price;
    @SerializedName("stock_quantity")
    @Expose
    String stock_quantity;*/
    @SerializedName("vendor_id")
    @Expose
    String vendor_id;
    @SerializedName("vendor_name")
    @Expose
    String vendor_name;





    public String getHerb_id() {
        return herb_id;
    }

    public void setHerb_id(String herb_id) {
        this.herb_id = herb_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

 /*   public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }*/
/*
    public String getStock_quantity() {
        return stock_quantity;
    }

    public void setStock_quantity(String stock_quantity) {
        this.stock_quantity = stock_quantity;
    }*/

    public String getVendor_id() {
        return vendor_id;
    }

    public void setVendor_id(String vendor_id) {
        this.vendor_id = vendor_id;
    }

    public String getVendor_name() {
        return vendor_name;
    }

    public void setVendor_name(String vendor_name) {
        this.vendor_name = vendor_name;
    }

      public int getCapsule_price() {
        return capsule_price;
    }

    public void setCapsule_price(int capsule_price) {
        this.capsule_price = capsule_price;
    }

    public int getCapsule_quantity() {
        return capsule_quantity;
    }

    public void setCapsule_quantity(int capsule_quantity) {
        this.capsule_quantity = capsule_quantity;
    }

    public int getRaw_price() {
        return raw_price;
    }

    public void setRaw_price(int raw_price) {
        this.raw_price = raw_price;
    }

    public int getRaw_quantity() {
        return raw_quantity;
    }

    public void setRaw_quantity(int raw_quantity) {
        this.raw_quantity = raw_quantity;
    }

   }