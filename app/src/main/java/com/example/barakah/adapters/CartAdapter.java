package com.example.barakah.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.barakah.R;
import com.example.barakah.databinding.AdapterCartBinding;
import com.example.barakah.databinding.AdapterFavouriteBinding;
import com.example.barakah.models.CartHerbModel;
import com.example.barakah.models.CartModel;
import com.example.barakah.models.HerbsModel;
import com.example.barakah.utils.BarakahConstants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    private final Context mContext;
    private String[] mDataset;
    private ArrayList<CartHerbModel> herbsList;
    private ArrayList<HerbsModel> contactListFiltered;

    public void setData(ArrayList<CartHerbModel> herbsModels) {
        this.herbsList = herbsModels;
        // this.contactListFiltered = herbsModels;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        AdapterCartBinding binding;

        public MyViewHolder(AdapterCartBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public CartAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        AdapterCartBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.adapter_cart, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (herbsList != null && herbsList.size() > 0) {
            final HerbsModel herb = herbsList.get(position).getHerbModel();
            final CartModel cart = herbsList.get(position).getCartModel();
            holder.binding.herbName.setText(herb.getName());
            holder.binding.herbType.setText(cart.getHerb_type());
            Glide.with(mContext).load(herb.getImage()).into(holder.binding.imageIv);
            holder.binding.btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    final FirebaseUser user = mAuth.getCurrentUser();
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                    herbsList.remove(herbsList.get(position));
                    notifyItemChanged(position);
                }
            });
            holder.binding.tvMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (cart.getQuantity() <= 1) {
                        cart.setQuantity(1);
                    } else {
                        int count = Integer.valueOf((String) holder.binding.tvCount.getText());
                        cart.setQuantity(count - 1);
                        holder.binding.tvCount.setText(cart.getQuantity());
                    }

                }
            });
            holder.binding.tvPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (cart.getQuantity() <= 1) {
                        cart.setQuantity(1);
                    } else {
                       // int count = holder.binding.tvCount.getText();
                       // cart.setQuantity(count + 1);
                        holder.binding.tvCount.setText(cart.getQuantity());
                    }
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