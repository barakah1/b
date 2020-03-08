package com.example.barakah.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HerbsModel implements Serializable {
    @SerializedName("benefit")
    @Expose
    private String benefit;
    @SerializedName("description")
    @Expose
    private String description;

     @SerializedName("image")
    @Expose
    private String image;
     @SerializedName("interaction")
    @Expose
    private String interaction;

    @SerializedName("name")
    @Expose
    private String name;
 @SerializedName("side_effect")
    @Expose
    private String side_effect;

    public String getBenefit() {
        return benefit;
    }

    public void setBenefit(String benefit) {
        this.benefit = benefit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getInteraction() {
        return interaction;
    }

    public void setInteraction(String interaction) {
        this.interaction = interaction;
    }

    public String getSide_effect() {
        return side_effect;
    }

    public void setSide_effect(String side_effect) {
        this.side_effect = side_effect;
    }

    public HerbsModel(){

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
