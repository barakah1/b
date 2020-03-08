package com.example.barakah.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.barakah.R;
import com.example.barakah.databinding.AdapterFavouriteBinding;
import com.example.barakah.databinding.AdapterHomeBinding;
import com.example.barakah.models.HerbsModel;
import com.example.barakah.ui.activity.HomeActivity;
import com.example.barakah.utils.BarakahConstants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.MyViewHolder> {
    private final Context mContext;
    private String[] mDataset;
    private ArrayList<HerbsModel> herbsList;
    private ArrayList<HerbsModel> contactListFiltered;

    public void setData(ArrayList<HerbsModel> herbsModels) {
        this.herbsList = herbsModels;
        this.contactListFiltered = herbsModels;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        AdapterFavouriteBinding binding;

        public MyViewHolder(AdapterFavouriteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public FavouriteAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        AdapterFavouriteBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.adapter_favourite, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        if (herbsList != null && herbsList.size() > 0) {
            holder.binding.herbName.setText(herbsList.get(position).getName());
            Glide.with(mContext).load(herbsList.get(position).getImage()).into(holder.binding.imageIv);
            holder.binding.btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    final FirebaseUser user = mAuth.getCurrentUser();
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                    mDatabase.child(BarakahConstants.DbTABLE.FAVOURITE).child(user.getUid()).child(herbsList.get(position).getId()).removeValue();
                    herbsList.remove(herbsList.get(position));
                    notifyItemChanged(position);
                }
            });
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