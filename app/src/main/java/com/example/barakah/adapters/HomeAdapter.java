package com.example.barakah.adapters;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

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
        // each data item is just a string in this case
        AdapterHomeBinding binding;

        public MyViewHolder(AdapterHomeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public HomeAdapter(Context mContext) {
        this.mContext = mContext;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        // create a new view
     /*   TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_home, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;*/
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        AdapterHomeBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.adapter_home, parent, false);
        /*.inflate<AdapterHomeBinding>(
                layoutInflater,
        R.layout.adapter_home,
                parent,
                false
        );*/
        return new MyViewHolder(binding);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (herbsList != null && herbsList.size() > 0) {
            holder.binding.rvTitle.setText(herbsList.get(position).getName());
            Glide.with(mContext).load(herbsList.get(position).getImage()).into(holder.binding.imgHome);
            holder.binding.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //  holder.textView.setText(mDataset[position]);

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

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
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

                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }

}