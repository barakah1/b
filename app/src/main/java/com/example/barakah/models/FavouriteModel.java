package com.example.barakah.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FavouriteModel {


    @SerializedName("herb_id")
    @Expose
    String herb_id;
    @SerializedName("is_fav")
    @Expose
    boolean is_fav = false;


    public FavouriteModel() {

    }

    public FavouriteModel(String herb_id, boolean is_fav) {
        this.herb_id = herb_id;
        this.is_fav = is_fav;
    }

    public String getHerbId() {
        return herb_id;
    }

    public void setHerbId(String id) {
        this.herb_id = id;
    }

    public boolean getIsFav() {
        return is_fav;
    }

    public void setIsFav(boolean is_fav) {
        this.is_fav = is_fav;
    }
}
