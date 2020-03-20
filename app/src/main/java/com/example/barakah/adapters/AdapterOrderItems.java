package com.example.barakah.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barakah.R;
import com.example.barakah.models.HealthStatusModel;

import java.util.ArrayList;

public class AdapterOrderItems extends RecyclerView.Adapter<AdapterOrderItems.MyViewHolder> {
    private final Context context;
    private ArrayList<HealthStatusModel> data;

    public AdapterOrderItems(FragmentActivity activity) {

        this.context = activity;

    }

    public void setData(ArrayList<HealthStatusModel> data) {
        this.data = data;
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
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_vendor, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        if (data != null && data.size() > 0) {
            HealthStatusModel model = data.get(position);
          /*  if (model.getChecked()) {
                holder.checkobx.setChecked(true);
            } else holder.checkobx.setChecked(false);
            holder.checkobx.setText(model.getName());
            holder.checkobx.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!data.get(position).getChecked()) {
                        data.get(position).setChecked(true);
                    } else data.get(position).setChecked(false);
                }
            });*/
        }
    }

    @Override
    public int getItemCount() {
        if (data != null && data.size() > 0) {
            return data.size();
        } else return 0;
    }

    public ArrayList<HealthStatusModel> getData() {
        ArrayList<HealthStatusModel> arrayList = new ArrayList<>();

        if (data != null) {
            for (HealthStatusModel hl : data
            ) {
                if (hl.getChecked()) {
                    arrayList.add(hl);
                }
            }
        }
        return arrayList;
    }
}