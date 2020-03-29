package com.example.barakah.models;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VendorStoreItemModel implements Serializable {


    @SerializedName("herb_id")
    @Expose
    String herb_id;
    @SerializedName("id")
    @Expose
    String id;
    @SerializedName("price")
    @Expose
    String price;
    @SerializedName("stock_quantity")
    @Expose
    String stock_quantity;
    @SerializedName("vendor_id")
    @Expose
    String vendor_id;
 @SerializedName("vendor_name")
    @Expose
    String vendor_name;


  transient   boolean isChecked;


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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStock_quantity() {
        return stock_quantity;
    }

    public void setStock_quantity(String stock_quantity) {
        this.stock_quantity = stock_quantity;
    }

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

     public boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
}
