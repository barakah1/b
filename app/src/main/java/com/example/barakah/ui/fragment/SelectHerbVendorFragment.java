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
import com.example.barakah.adapters.FavouriteAdapter;
import com.example.barakah.adapters.SelectVendorAdapter;
import com.example.barakah.databinding.FragmentSelectHerbVendorBinding;
import com.example.barakah.models.CartHerbModel;
import com.example.barakah.models.CartModel;
import com.example.barakah.models.VendorStoreItemModel;
import com.example.barakah.ui.activity.HomeActivity;
import com.example.barakah.utils.BarakahConstants;
import com.example.barakah.utils.BarakahUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

import static com.example.barakah.utils.BarakahConstants.CART_DATA;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectHerbVendorFragment extends Fragment {
    public static String TAG = "SelectHerbVendorFragment";
    private Dialog progressDialog;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private ArrayList<CartHerbModel> cartHerbList;
    private FragmentSelectHerbVendorBinding binding;
    private SelectVendorAdapter adapter;

    public static SelectHerbVendorFragment newInstance(ArrayList<CartHerbModel> cartHerbList) {
        SelectHerbVendorFragment fragment = new SelectHerbVendorFragment();
        Bundle args = new Bundle();
        args.putSerializable(CART_DATA, cartHerbList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        cartHerbList = (ArrayList<CartHerbModel>) getArguments().getSerializable(CART_DATA);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_select_herb_vendor, container, false);
        binding = FragmentSelectHerbVendorBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.rcvCurrentOrders.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new SelectVendorAdapter(getActivity());
        binding.rcvCurrentOrders.setAdapter(adapter);
        if (cartHerbList != null && cartHerbList.size() > 0) {
            getCardData();

        }
        binding.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<CartHerbModel> cartherb = adapter.getHerbCartList();
               /* for (CartHerbModel cart:
                     cartherb) {*/
                for (int i = 0; i < cartherb.size(); i++) {
                    for (VendorStoreItemModel vendor :
                            cartherb.get(i).getVendors()) {
                        if (vendor.getIsChecked()) {
                            cartherb.get(i).setVendor(vendor);
                        }
                    }
                }


                BarakahUtils.setCurrentFragment(
                        getActivity(), R.id.homeContainer,
                        FragmentDeliveryType.newInstance(cartherb), FragmentDeliveryType.TAG
                );
            }
        });
    }

    private void getCardData() {
        mDatabase.child(BarakahConstants.DbTABLE.STOREITEM).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    Iterator<DataSnapshot> data = dataSnapshot.getChildren().iterator();
                    while (data.hasNext()) {
                        DataSnapshot da = data.next();
                        String key = da.getKey();
                       /* for (CartHerbModel model : cartHerbList
                        ) {*/
                        for (int i = 0; i < cartHerbList.size(); i++) {
                            if (cartHerbList.get(i).getCartModel().getHerb_id().equalsIgnoreCase(key)) {
                                if (da.hasChildren()) {
                                    ArrayList<VendorStoreItemModel> herbsModels = new ArrayList<VendorStoreItemModel>();

                                    Iterator<DataSnapshot> dataa = da.getChildren().iterator();
                                    while (dataa.hasNext()) {
                                        DataSnapshot daa = dataa.next();
                                        VendorStoreItemModel modell = daa.getValue(VendorStoreItemModel.class);
                                        herbsModels.add(modell);

                                    }
                                    cartHerbList.get(i).setVendors(herbsModels);

                                }
                            }

                        }
                        System.out.println(da.getValue());


                    }
                    adapter.setData(cartHerbList);
                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
