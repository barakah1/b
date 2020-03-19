package com.example.barakah.ui.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.barakah.R;
import com.example.barakah.adapters.CurrentOrderAdapter;
import com.example.barakah.adapters.HomeAdapter;
import com.example.barakah.databinding.FragmentCurrentOrdersBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentOrdersFragment extends Fragment {

    private FragmentCurrentOrdersBinding binding;
    private CurrentOrderAdapter adapter;

    public CurrentOrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_current_orders, container, false);
        binding = FragmentCurrentOrdersBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.rcvCurrentOrders.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new CurrentOrderAdapter(getActivity());
        binding.rcvCurrentOrders.setAdapter(adapter);
        getCurrentOrdersList();
    }

    private void getCurrentOrdersList() {

    }
}
