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
        final FirebaseUser firebaseUser = mAuth.getCurrentUser();

        mDatabase.child(BarakahConstants.DbTABLE.ORDERS).child(firebaseUser.getUid()).orderByChild("order_status").startAt("0").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    ArrayList<OrderModel> herbsModels = new ArrayList<OrderModel>();
                    Iterator<DataSnapshot> data = dataSnapshot.getChildren().iterator();
                    while (data.hasNext()) {
                        DataSnapshot da = data.next();
                        System.out.println(da.getValue());
                        OrderModel model = da.getValue(OrderModel.class);
                        herbsModels.add(model);
                    }
                       adapter.setData(herbsModels);
                    adapter.notifyDataSetChanged();

                    /*if (herbsModels.size() > 0) {
                        getAllHerbs(herbsModels);
                    }*/
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
