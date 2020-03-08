package com.example.barakah.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.barakah.R;
import com.example.barakah.databinding.AdapterHomeBinding;
import com.example.barakah.models.HerbsModel;
import com.example.barakah.ui.activity.HomeActivity;
import com.example.barakah.utils.BarakahConstants;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> implements Filterable {
    private final Context mContext;
    private String[] mDataset;
    private ArrayList<HerbsModel> herbsList;
    private ArrayList<HerbsModel> contactListFiltered;

    public void setData(ArrayList<HerbsModel> herbsModels) {
        this.herbsList = herbsModels;
        this.contactListFiltered = herbsModels;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        AdapterHomeBinding binding;

        public MyViewHolder(AdapterHomeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public HomeAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        AdapterHomeBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.adapter_home, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        if (herbsList != null && herbsList.size() > 0) {
            holder.binding.rvTitle.setText(herbsList.get(position).getName());
            Glide.with(mContext).load(herbsList.get(position).getImage()).into(holder.binding.imgHome);
            holder.binding.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, HomeActivity.class);
                    intent.putExtra(BarakahConstants.HOME_ACTIVITY, BarakahConstants.HERBS_DETAILS);
                    intent.putExtra(BarakahConstants.HERBS_MODEL, herbsList.get(position));
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

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    contactListFiltered = herbsList;
                } else {
                    ArrayList<HerbsModel> filteredList = new ArrayList<>();
                    for (HerbsModel row : herbsList) {


                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    contactListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = contactListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                contactListFiltered = (ArrayList<HerbsModel>) filterResults.values;

                notifyDataSetChanged();
            }
        };
    }

}