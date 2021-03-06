package com.example.barakah.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RadioGroup;

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
            final CartHerbModel cartHerbModel = herbsList.get(position);
            VendorStoreItemModel vendor = herbsList.get(position).getVendor();
            final HerbsModel herb = herbsList.get(position).getHerbModel();
            //   final VendorStoreItemModel herb = herbsList.get(position).getVendor();
            final CartModel cart = herbsList.get(position).getCartModel();
            holder.binding.tvHerb.setText(herb.getName());
            if (cart.getHerb_type() != null || !cart.getHerb_type().isEmpty()) {
                if (cart.getHerb_type().equals(mContext.getResources().getString(R.string.raw))) {
                    holder.binding.tvPrice.setText(String.valueOf(vendor.getRaw_price()));

                } else if (cart.getHerb_type().equals(mContext.getResources().getString(R.string.capsule))) {
                    holder.binding.tvPrice.setText(String.valueOf(vendor.getCapsule_price()));

                }

            }
            // holder.binding.tvPrice.setText(vendor.getPrice());
            // Glide.with(mContext).load(herb.getImage()).into();
            holder.binding.imageIv.setText(vendor.getVendor_name());
            holder.binding.tvQuantity.setText(String.valueOf(cart.getQuantity()));
            if (cartHerbModel.getDeliveryType().equals("0")) {
                holder.binding.radioHomeDelivery.setChecked(true);

            } else {
                holder.binding.receiveFromHome.setChecked(true);

            }
            holder.binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.receiveFromHome) {
                        cartHerbModel.setDeliveryType("1");
                    } else if (checkedId == R.id.radioHomeDelivery) {
                        cartHerbModel.setDeliveryType("0");

                    }
                    // This will get the radiobutton that has changed in its check state
                    //  RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
                    // This puts the value (true/false) into the variable
                    // boolean isChecked = checkedRadioButton.isChecked();
                    // If the radiobutton that has changed in check state is now checked...
                  /*  if (isChecked)
                    {
                        // Changes the textview's text to "Checked: example radiobutton text"
                        tv.setText("Checked:" + checkedRadioButton.getText());
                    }*/

                }
            });
          /*  if (holder.binding.radioHomeDelivery.isChecked()) {
                cartHerbModel.setDeliveryType("0");
            } else {
                cartHerbModel.setDeliveryType("1");
            }*/

        }
    }

    @Override
    public int getItemCount() {
        if (herbsList != null) {
            return herbsList.size();
        } else return 0;
    }


}