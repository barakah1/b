package com.example.barakah.models;

import java.io.Serializable;
import java.util.ArrayList;

public class CartHerbModel implements Serializable {
    CartModel cartModel;
    HerbsModel herbModel;
    ArrayList<VendorStoreItemModel> vendors;
    VendorStoreItemModel vendor;
    //0=home, 1= self service
    String deliveryType = "0";

    public CartModel getCartModel() {
        return cartModel;
    }

    public void setCartModel(CartModel cartModel) {
        this.cartModel = cartModel;
    }

    public HerbsModel getHerbModel() {
        return herbModel;
    }

    public void setHerbModel(HerbsModel herbModel) {
        this.herbModel = herbModel;
    }

    public ArrayList<VendorStoreItemModel> getVendors() {
        return vendors;
    }

    public void setVendors(ArrayList<VendorStoreItemModel> vendor) {
        this.vendors = vendor;
    }

    public void setVendor(VendorStoreItemModel vendor) {
        this.vendor = vendor;
    }

    public VendorStoreItemModel getVendor() {
        return vendor;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }
}
