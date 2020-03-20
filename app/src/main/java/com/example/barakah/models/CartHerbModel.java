package com.example.barakah.models;

import java.io.Serializable;
import java.util.ArrayList;

public class CartHerbModel implements Serializable {
    CartModel cartModel;
    HerbsModel herbModel;
    ArrayList<VendorStoreItemModel> vendor;


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

    public ArrayList<VendorStoreItemModel> getVendor() {
        return vendor;
    }

    public void setVendor(ArrayList<VendorStoreItemModel> vendor) {
        this.vendor = vendor;
    }
}
