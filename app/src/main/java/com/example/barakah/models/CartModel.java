package com.example.barakah.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartModel {

    @SerializedName("herb_id")
    @Expose
    String herb_id;
    @SerializedName("herb_type")
    @Expose
    String herb_type;
    @SerializedName("quantity")
    @Expose
    int quantity = 1;

    public String getHerb_id() {
        return herb_id;
    }

    public void setHerb_id(String herb_id) {
        this.herb_id = herb_id;
    }

    public String getHerb_type() {
        return herb_type;
    }

    public void setHerb_type(String herb_type) {
        this.herb_type = herb_type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
