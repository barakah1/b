package com.example.barakah.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.barakah.R;
import com.example.barakah.adapters.HerbOfVendorAdapter;
import com.example.barakah.adapters.SelectVendorAdapter;
import com.example.barakah.databinding.FragmentDeliveryTypeBinding;
import com.example.barakah.models.CartHerbModel;
import com.example.barakah.models.OrderModel;
import com.example.barakah.models.StoreItemModel;
import com.example.barakah.models.VendorStoreItemModel;
import com.example.barakah.ui.activity.MainActivity;
import com.example.barakah.utils.BarakahConstants;
import com.example.barakah.utils.BarakahUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

import static com.example.barakah.utils.BarakahConstants.CART_DATA;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDeliveryType extends Fragment {

    public static String TAG = "FragmentDeliveryType";

    private FragmentDeliveryTypeBinding binding;
    private HerbOfVendorAdapter adapter;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private ArrayList<CartHerbModel> cartHerbList;

    public FragmentDeliveryType() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        cartHerbList = (ArrayList<CartHerbModel>) getArguments().getSerializable(CART_DATA);

    }

    public static FragmentDeliveryType newInstance(ArrayList<CartHerbModel> cartHerbList) {
        FragmentDeliveryType fragment = new FragmentDeliveryType();
        Bundle args = new Bundle();
        args.putSerializable(CART_DATA, cartHerbList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_delivery_type, container, false);
        binding = FragmentDeliveryTypeBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.rcvCurrentOrders.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new HerbOfVendorAdapter(getActivity());
        binding.rcvCurrentOrders.setAdapter(adapter);
        binding.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<CartHerbModel> al = adapter.getHerbCartList();

                for (final CartHerbModel model :
                        al) {
                    long number = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
                    final OrderModel orderModel = new OrderModel();
                    orderModel.setId(String.valueOf(number));
                    orderModel.setVendor_name(model.getVendor().getVendor_name());
                    orderModel.setVendor_id(model.getVendor().getVendor_id());
                    orderModel.setQuantity(model.getCartModel().getQuantity());
                    orderModel.setVendor_name(model.getVendor().getVendor_name());
                    orderModel.setHerb_type(model.getCartModel().getHerb_type());
                    orderModel.setHerb_name(model.getHerbModel().getName());
                    orderModel.setHerb_id(model.getHerbModel().getId());
                    orderModel.setOrder_status("0");
                    try {
                        int quantity = orderModel.getQuantity();
                        if (orderModel.getHerb_type() != null || !orderModel.getHerb_type().isEmpty()) {
                            if (orderModel.getHerb_type().equals(getActivity().getResources().getString(R.string.raw))) {
                                double price = quantity * Double.valueOf(model.getVendor().getRaw_price());

                                orderModel.setOrder_price(String.valueOf(price));
                            } else if (orderModel.getHerb_type().equals(getActivity().getResources().getString(R.string.capsule))) {
                                double price = quantity * Double.valueOf(model.getVendor().getCapsule_price());

                                orderModel.setOrder_price(String.valueOf(price));


                            }

                        }
                    } catch (Exception e
                    ) {
                    }
                    DatabaseReference db = mDatabase.child(BarakahConstants.DbTABLE.ORDERS).child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).push();
                    String key = db.getKey();
                    //orderModel.setId(key);
                    mDatabase.child(BarakahConstants.DbTABLE.ORDERS).child(mAuth.getCurrentUser().getUid()).child(key).setValue(orderModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isComplete()) {
                                mDatabase.child(BarakahConstants.DbTABLE.CART).child(mAuth.getCurrentUser().getUid()).child(model.getCartModel().getId()).removeValue();
                                BarakahUtils.toastMessgae(getActivity(), getResources().getString(R.string.order_placed_successfully), Toast.LENGTH_SHORT);
                                updateStoreData(orderModel);

                            }
                        }
                    });
                }
            }
        });
        adapter.setData(cartHerbList);
        adapter.notifyDataSetChanged();
    }

    private void updateStoreData(final OrderModel orderModel) {
        mDatabase.child(BarakahConstants.DbTABLE.STOREITEM).child(orderModel.getHerb_id()).child(orderModel.getVendor_id()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    VendorStoreItemModel modell = dataSnapshot.getValue(VendorStoreItemModel.class);
                    StoreItemModel model=new StoreItemModel();
model.setCapsule_price(modell.getCapsule_price());
model.setCapsule_quantity(modell.getCapsule_quantity());
model.setRaw_price(modell.getRaw_price());
model.setRaw_quantity(modell.getRaw_quantity());
model.setHerb_id(modell.getHerb_id());
model.setVendor_id(modell.getVendor_id());
model.setVendor_name(modell.getVendor_name());
                    if (orderModel.getHerb_type().equalsIgnoreCase(getActivity().getResources().getString(R.string.raw))) {
                        model.setRaw_quantity(model.getRaw_quantity() - orderModel.getQuantity());
                        mDatabase.child(BarakahConstants.DbTABLE.STOREITEM).child(orderModel.getHerb_id()).child(orderModel.getVendor_id()).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isComplete()){
                                    Intent intentt = new Intent(getActivity(), MainActivity.class);
                                    intentt.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intentt.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intentt.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intentt);
                                    getActivity().finish();
                                }
                            }
                        });


                    } else if (orderModel.getHerb_type().equalsIgnoreCase(getActivity().getResources().getString(R.string.capsule))) {
                        model.setCapsule_quantity(model.getCapsule_quantity() - orderModel.getQuantity());
                        mDatabase.child(BarakahConstants.DbTABLE.STOREITEM).child(orderModel.getHerb_id()).child(orderModel.getVendor_id()).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isComplete()){
                                    Intent intentt = new Intent(getActivity(), MainActivity.class);
                                    intentt.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intentt.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intentt.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intentt);
                                    getActivity().finish();
                                }
                            }
                        });

                    }


                }
            }
            //  System.out.println(da.getValue());


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }
}
