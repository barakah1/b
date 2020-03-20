package com.example.barakah.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.barakah.R;
import com.example.barakah.databinding.AdapterHerbOfVendorBinding;
import com.example.barakah.databinding.AdapterSelectVendorBinding;
import com.example.barakah.models.CartHerbModel;
import com.example.barakah.models.CartModel;
import com.example.barakah.models.HerbsModel;
import com.example.barakah.models.VendorStoreItemModel;

import java.util.ArrayList;

public class HerbOfVendorAdapter extends RecyclerView.Adapter<HerbOfVendorAdapter.MyViewHolder> {
    private final Context mContext;
    private String[] mDataset;
    private ArrayList<CartHerbModel> herbsList;
    private ArrayList<HerbsModel> contactListFiltered;

    public void setData(ArrayList<CartHerbModel> herbsModels) {
        this.herbsList = herbsModels;
        // this.contactListFiltered = herbsModels;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        AdapterHerbOfVendorBinding binding;

        public MyViewHolder(AdapterHerbOfVendorBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public HerbOfVendorAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        AdapterHerbOfVendorBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.adapter_herb_of_vendor, parent, false);
        return new MyViewHolder(binding);
    }

    public ArrayList<CartHerbModel> getHerbCartList() {
        return herbsList;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (herbsList != null && herbsList.size() > 0) {
            CartHerbModel cartHerbModel = herbsList.get(position);
            final HerbsModel herb = herbsList.get(position).getHerbModel();
         //   final VendorStoreItemModel herb = herbsList.get(position).getVendor();
            final CartModel cart = herbsList.get(position).getCartModel();
            holder.binding.tvHerbName.setText(herb.getName());
           // holder.binding.tvPrice.setText(cart.get());
            Glide.with(mContext).load(herb.getImage()).into(holder.binding.imageIv);
            holder.binding.tvQuantity.setText(String.valueOf(cart.getQuantity()));

            }
    }

    @Override
    public int getItemCount() {
        if (herbsList != null) {
            return herbsList.size();
        } else return 0;
    }


}