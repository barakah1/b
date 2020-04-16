package com.example.barakah.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barakah.R;
import com.example.barakah.models.HealthStatusModel;
import com.example.barakah.models.VendorStoreItemModel;

import java.util.ArrayList;

public class AdapterVendors extends RecyclerView.Adapter<AdapterVendors.MyViewHolder> {
    private final String herb_type;
    private final Context context;
    private final ArrayList<VendorStoreItemModel> data;


    public AdapterVendors(FragmentActivity mContext, ArrayList<VendorStoreItemModel> cartHerbModel, String herb_type) {
        this.context = mContext;
        this.data = cartHerbModel;
        this.herb_type = herb_type;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tvPrice;
        public RadioButton radioButton;

        public MyViewHolder(View v) {
            super(v);
            radioButton = v.findViewById(R.id.radioVendor);
            tvPrice = v.findViewById(R.id.tvPrice);
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_vendor, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    private RadioButton lastCheckedRB = null;

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        if (data != null && data.size() > 0) {
            VendorStoreItemModel model = data.get(position);
            if (herb_type != null || !herb_type.isEmpty()) {
                if (herb_type.equals(context.getResources().getString(R.string.raw))) {
                    holder.tvPrice.setText(model.getRaw_price());

                } else if (herb_type.equals(context.getResources().getString(R.string.capsule))) {
                    holder.tvPrice.setText(model.getCapsule_price());

                }

            }
            holder.radioButton.setText(model.getVendor_name());
            holder.radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    RadioButton checked_rb = (RadioButton) buttonView;
                    if (lastCheckedRB != null) {
                        lastCheckedRB.setChecked(false);
                    }

                    lastCheckedRB = checked_rb;
                    for (int i = 0; i < data.size(); i++) {
                        if (i == position) {
                            data.get(i).setIsChecked(true);

                        } else {
                            data.get(i).setIsChecked(false);

                        }
                    }
                }

            });
        }
    }

    @Override
    public int getItemCount() {
        if (data != null && data.size() > 0) {
            return data.size();
        } else return 0;
    }
}