package com.example.barakah.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barakah.R;
import com.example.barakah.models.OrderSubItemModel;

import java.util.ArrayList;

public class AdapterOrderHerb extends RecyclerView.Adapter<AdapterOrderHerb.MyViewHolder> {
    //  private final String herb_type;
    private final Context context;
    private final ArrayList<OrderSubItemModel> data;


    public AdapterOrderHerb(FragmentActivity mContext, ArrayList<OrderSubItemModel> cartHerbModel) {
        this.context = mContext;
        this.data = cartHerbModel;
        // this.herb_type = herb_type;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tvPrice;
        public TextView tvQuantity;
        public TextView tvHerb;

        public MyViewHolder(View v) {
            super(v);
            tvHerb = v.findViewById(R.id.tvHerb);
            tvQuantity = v.findViewById(R.id.tvQuantity);
            tvPrice = v.findViewById(R.id.tvPrice);
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_herbs, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        if (data != null && data.size() > 0) {
            OrderSubItemModel model = data.get(position);
            holder.tvPrice.setText(model.getOrder_price());
            holder.tvQuantity.setText(String.valueOf(model.getQuantity()));
            holder.tvHerb.setText(model.getHerb_name());
        /*    if (model.getHerb_type() != null || !model.getHerb_type().isEmpty()) {
                if (model.getHerb_type().equals(context.getResources().getString(R.string.raw))) {
                    holder.tvPrice.setText(String.valueOf(model.getOrder_price()));

                } else if (model.getHerb_type().equals(context.getResources().getString(R.string.capsule))) {
                    holder.tvPrice.setText(String.valueOf(model.getOrder_price()));

                }

            }*/

        }
    }

    @Override
    public int getItemCount() {
        if (data != null && data.size() > 0) {
            return data.size();
        } else return 0;
    }
}