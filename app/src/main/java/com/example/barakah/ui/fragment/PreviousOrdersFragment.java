package com.example.barakah.ui.fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.barakah.R;
import com.example.barakah.adapters.CurrentOrderAdapter;
import com.example.barakah.databinding.FragmentPreviousOrdersBinding;
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
public class PreviousOrdersFragment extends Fragment {


    private Dialog progressDialog;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FragmentPreviousOrdersBinding binding;
    private CurrentOrderAdapter adapter;

    public PreviousOrdersFragment() {
        // Required empty public constructor
    }

    public static PreviousOrdersFragment newInstance() {
        PreviousOrdersFragment fragment = new PreviousOrdersFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
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
        // Inflate the layout for this fragment
        //  return inflater.inflate(R.layout.fragment_previous_orders, container, false);
        binding = FragmentPreviousOrdersBinding.inflate(inflater);
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
        final FirebaseUser firebaseUser = mAuth.getCurrentUser();
        mDatabase.child(BarakahConstants.DbTABLE.ORDERS).child(firebaseUser.getUid()).orderByChild("order_status").equalTo(BarakahConstants.DELIVERED).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    ArrayList<OrderModel> herbsModels = new ArrayList<OrderModel>();
                  /*  ArrayList<OrderModel> herbsModels = new ArrayList<OrderModel>();
                    Iterator<DataSnapshot> data = dataSnapshot.getChildren().iterator();
                    while (data.hasNext()) {
                        DataSnapshot da = data.next();
                        System.out.println(da.getValue());
                        OrderModel model = da.getValue(OrderModel.class);
                        herbsModels.add(model);
                    }*/
                    Iterator<DataSnapshot> data = dataSnapshot.getChildren().iterator();
                    while (data.hasNext()) {
                        DataSnapshot da = data.next();
                        //System.out.println();
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
                  //  getProgressOrders();
                    if (herbsModels.size() > 0) {
                        adapter.setData(herbsModels);
                        adapter.notifyDataSetChanged();
                        binding.rcvCurrentOrders.setVisibility(View.VISIBLE);
                        binding.llNoData.setVisibility(View.GONE);
                    } else {
                        binding.rcvCurrentOrders.setVisibility(View.GONE);
                        binding.llNoData.setVisibility(View.VISIBLE);
                    }

                }else{
                    binding.rcvCurrentOrders.setVisibility(View.GONE);
                    binding.llNoData.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
