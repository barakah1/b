package com.example.barakah.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.barakah.R;
import com.example.barakah.databinding.AdapterCartBinding;
import com.example.barakah.databinding.AdapterSelectVendorBinding;
import com.example.barakah.models.CartHerbModel;
import com.example.barakah.models.CartModel;
import com.example.barakah.models.HerbsModel;
import com.example.barakah.utils.BarakahConstants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SelectVendorAdapter extends RecyclerView.Adapter<SelectVendorAdapter.MyViewHolder> {
    private final Context mContext;
    private String[] mDataset;
    private ArrayList<CartHerbModel> herbsList;
    private ArrayList<HerbsModel> contactListFiltered;

    public void setData(ArrayList<CartHerbModel> herbsModels) {
        this.herbsList = herbsModels;
        // this.contactListFiltered = herbsModels;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        AdapterSelectVendorBinding binding;

        public MyViewHolder(AdapterSelectVendorBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public SelectVendorAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        AdapterSelectVendorBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.adapter_select_vendor, parent, false);
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
            final CartModel cart = herbsList.get(position).getCartModel();
            holder.binding.tvHerbName.setText(herb.getName());
            holder.binding.tvHerbType.setText(cart.getHerb_type());
            holder.binding.tvHerbWeight.setText(String.valueOf(cart.getQuantity()));
            LinearLayoutManager lln = new LinearLayoutManager(mContext);
            holder.binding.rcvVendors.setLayoutManager(lln);
            AdapterVendors adapter = new AdapterVendors((FragmentActivity) mContext, cartHerbModel.getVendors());
            holder.binding.rcvVendors.setAdapter(adapter);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (herbsList != null) {
            return herbsList.size();
        } else return 0;
    }


}