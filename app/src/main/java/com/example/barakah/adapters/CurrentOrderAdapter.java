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
import com.example.barakah.databinding.AdapterCurrentOrderBinding;
import com.example.barakah.databinding.AdapterFavouriteBinding;
import com.example.barakah.models.CartHerbModel;
import com.example.barakah.models.HerbsModel;
import com.example.barakah.models.OrderModel;
import com.example.barakah.ui.activity.HomeActivity;
import com.example.barakah.utils.BarakahConstants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CurrentOrderAdapter extends RecyclerView.Adapter<CurrentOrderAdapter.MyViewHolder> {
    private final Context mContext;
    private ArrayList<OrderModel> herbsList;
    private ArrayList<HerbsModel> contactListFiltered;

    public void setData(ArrayList<OrderModel> herbsModels) {
        this.herbsList = herbsModels;
        // this.contactListFiltered = herbsModels;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        AdapterCurrentOrderBinding binding;

        public MyViewHolder(AdapterCurrentOrderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public CurrentOrderAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        AdapterCurrentOrderBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.adapter_current_order, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        if (herbsList != null && herbsList.size() > 0) {
            final OrderModel model = herbsList.get(position);
            holder.binding.orderPrice.setText(mContext.getResources().getString(R.string.total_price,model.getOrder_price()));
            holder.binding.orderNumber.setText(model.getId());
            // holder.binding.orderNumber.setText(herbsList.get(position).getName());
            // holder.binding.orderPrice.setText(herbsList.get(position).getName());
            holder.binding.cardOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, HomeActivity.class);
                    intent.putExtra(BarakahConstants.HOME_ACTIVITY, BarakahConstants.ORDER_DETAILS);
                    intent.putExtra(BarakahConstants.ORDER_MODEL, model);
                    mContext.startActivity(intent);

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