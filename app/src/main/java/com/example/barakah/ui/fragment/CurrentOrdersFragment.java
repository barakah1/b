package com.example.barakah.ui.fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.example.barakah.models.CartModel;
import com.example.barakah.models.OrderModel;
import com.example.barakah.models.OrderSubItemModel;
import com.example.barakah.utils.BarakahConstants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentOrdersFragment extends Fragment {

    private FragmentCurrentOrdersBinding binding;
    private CurrentOrderAdapter adapter;
    private Dialog progressDialog;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    ArrayList<OrderModel> herbsModels;

    public CurrentOrdersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCurrentOrdersBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.rcvCurrentOrders.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new CurrentOrderAdapter(getActivity());
        binding.rcvCurrentOrders.setAdapter(adapter);
        herbsModels = new ArrayList<OrderModel>();

        getCurrentOrdersList();
    }

    private void getCurrentOrdersList() {
        final FirebaseUser firebaseUser = mAuth.getCurrentUser();

        mDatabase.child(BarakahConstants.DbTABLE.ORDERS).child(firebaseUser.getUid()).orderByChild("order_status").equalTo("0").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    Iterator<DataSnapshot> data = dataSnapshot.getChildren().iterator();
                    while (data.hasNext()) {
                        DataSnapshot da = data.next();
                        OrderModel model = da.getValue(OrderModel.class);
                        DataSnapshot child = da.child(BarakahConstants.DbTABLE.HERB_KEY);
                        if (child.hasChildren()) {
                            Iterator<DataSnapshot> dataa = child.getChildren().iterator();
                            ArrayList<OrderSubItemModel> al = new ArrayList<>();
                            while (dataa.hasNext()) {
                                DataSnapshot daa = dataa.next();
                                OrderSubItemModel orderItem = daa.getValue(OrderSubItemModel.class);
                                al.add(orderItem);

                            }
                            model.setHerbs(al);
                        }
                        herbsModels.add(model);
                    }
                    adapter.setData(herbsModels);
                    adapter.notifyDataSetChanged();
                    getProgressOrders();
                } else {
                    getProgressOrders();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                getProgressOrders();
            }
        });
    }

    private void getProgressOrders() {
        final FirebaseUser firebaseUser = mAuth.getCurrentUser();
        mDatabase.child(BarakahConstants.DbTABLE.ORDERS).child(firebaseUser.getUid()).orderByChild("order_status").equalTo("1").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    Iterator<DataSnapshot> data = dataSnapshot.getChildren().iterator();
                    while (data.hasNext()) {
                        DataSnapshot da = data.next();
                        OrderModel model = da.getValue(OrderModel.class);
                        DataSnapshot child = da.child(BarakahConstants.DbTABLE.HERB_KEY);
                        if (child.hasChildren()) {
                            Iterator<DataSnapshot> dataa = child.getChildren().iterator();
                            ArrayList<OrderSubItemModel> al = new ArrayList<>();
                            while (dataa.hasNext()) {
                                DataSnapshot daa = dataa.next();
                                OrderSubItemModel orderItem = daa.getValue(OrderSubItemModel.class);
                                al.add(orderItem);

                            }
                            model.setHerbs(al);
                        }
                        herbsModels.add(model);
                    }
                    adapter.setData(herbsModels);
                    adapter.notifyDataSetChanged();
                }
                checkPreviousOrders();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void checkPreviousOrders() {
        if (herbsModels.size() > 0) {
            adapter.setData(herbsModels);
            adapter.notifyDataSetChanged();
            binding.rcvCurrentOrders.setVisibility(View.VISIBLE);
            binding.llNoData.setVisibility(View.GONE);
        } else {
            binding.rcvCurrentOrders.setVisibility(View.GONE);
            binding.llNoData.setVisibility(View.VISIBLE);
        }
    }
}
