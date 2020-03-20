package com.example.barakah.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CartModel implements Serializable {

    @SerializedName("id")
    @Expose
    String id;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
