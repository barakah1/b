package com.example.barakah.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashMap;

public class HealthStatusModel implements Serializable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("conflict")
    @Expose
    private HashMap<String,String> conflict;


    private Boolean isChecked = false;


    public HealthStatusModel() {

    }

    public HealthStatusModel(String name, String id, Boolean status) {
        this.name = name;
        this.id = id;
        this.isChecked = status;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String mobile) {
        this.id = mobile;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    public HashMap<String, String> getConflict() {
        return conflict;
    }

    public void setConflict(HashMap<String, String> conflict) {
        this.conflict = conflict;
    }
}
