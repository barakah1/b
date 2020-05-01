package com.example.barakah.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderSubItemModel implements Serializable {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("herb_id")
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
    private String herb_type;

    public String getHerb_id() {
        return herb_id;
    }

    public void setHerb_id(String herb_id) {
        this.herb_id = herb_id;
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
}
