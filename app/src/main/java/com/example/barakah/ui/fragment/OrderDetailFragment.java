package com.example.barakah.ui.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.barakah.R;
import com.example.barakah.databinding.FragmentOrderDetailBinding;
import com.example.barakah.models.OrderModel;
import com.example.barakah.utils.BarakahConstants;

import java.io.Serializable;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderDetailFragment extends Fragment {
    public static String TAG = "OrderDetailFragment";

    private FragmentOrderDetailBinding binding;
    private OrderModel orderModel;

    public OrderDetailFragment() {
        // Required empty public constructor
    }


    public static OrderDetailFragment newInstance(OrderModel orderModel) {
        OrderDetailFragment fragment = new OrderDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(BarakahConstants.ORDER_MODEL, orderModel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            orderModel = (OrderModel) getArguments().getSerializable(BarakahConstants.ORDER_MODEL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //  return inflater.inflate(R.layout.fragment_order_detail, container, false);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_detail, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (orderModel != null) {
            if (orderModel.getOrder_status() != null) {
                if (orderModel.getOrder_status().equals("0")) {
                    binding.stepper.setCurrentStep(1);

                } else if (orderModel.getOrder_status().equals("1")) {
                    binding.stepper.setCurrentStep(2);

                } else if (orderModel.getOrder_status().equals("2")) {
                    binding.stepper.setCurrentStep(3);

                }
            }
            if (orderModel.getDelivery_type() != null) {
                if (orderModel.getDelivery_type().equals("0")) {
                    binding.deliveryMethod.setText(getResources().getString(R.string.delivery_method,getResources().getString(R.string.home_delivery)));

                } else if (orderModel.getDelivery_type().equals("1")) {
                    binding.deliveryMethod.setText(getResources().getString(R.string.delivery_method,getResources().getString(R.string.receive_from_home)));

                }
            }
            binding.orderNumber.setText( orderModel.getId());
          //  binding.deliveryMethod.setText(getResources().getString(R.string.order_id, orderModel.getOrder_status()));
            binding.tvVendroName.setText( orderModel.getVendor_name());
            binding.tvPrice.setText(orderModel.getOrder_price());
            binding.tvQuantity.setText(String.valueOf(orderModel.getQuantity()));
            binding.tvHerb.setText(orderModel.getHerb_name());
            if (orderModel.getOrder_price() != null) {
                binding.tvTotalPrice.setText(getResources().getString(R.string.total_price, orderModel.getOrder_price()));
            }
        }

    }
}
