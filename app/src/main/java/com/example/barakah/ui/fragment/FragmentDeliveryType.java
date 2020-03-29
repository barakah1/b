package com.example.barakah.ui.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.barakah.R;
import com.example.barakah.adapters.HerbOfVendorAdapter;
import com.example.barakah.adapters.SelectVendorAdapter;
import com.example.barakah.databinding.FragmentDeliveryTypeBinding;
import com.example.barakah.models.CartHerbModel;
import com.example.barakah.models.OrderModel;
import com.example.barakah.utils.BarakahConstants;
import com.example.barakah.utils.BarakahUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
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

                for (CartHerbModel model :
                        al) {
                    OrderModel orderModel = new OrderModel();
                    orderModel.setVendor_name(model.getVendor().getVendor_name());
                    orderModel.setVendor_id(model.getVendor().getVendor_id());
                    orderModel.setQuantity(model.getCartModel().getQuantity());
                    orderModel.setVendor_name(model.getVendor().getVendor_name());
                    orderModel.setHerb_type(model.getCartModel().getHerb_type());
                    orderModel.setHerb_name(model.getHerbModel().getName());
                    orderModel.setOrder_status("0");
                    try {
                        int quantity = orderModel.getQuantity();
                        double price = quantity * Double.parseDouble(model.getVendor().getPrice());
                        orderModel.setOrder_price(String.valueOf(price));
                    } catch (Exception e
                    ) {
                    }
                    DatabaseReference db = mDatabase.child(BarakahConstants.DbTABLE.ORDERS).child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).push();
                    String key = db.getKey();
                    orderModel.setId(key);
                    mDatabase.child(BarakahConstants.DbTABLE.ORDERS).child(mAuth.getCurrentUser().getUid()).child(key).setValue(orderModel);
                    mDatabase.child(BarakahConstants.DbTABLE.CART).child(mAuth.getCurrentUser().getUid()).child(model.getCartModel().getId()).removeValue();
                }
                /* @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("herb_id")
    @Expose
    private String herb_id;
    @SerializedName("vendor_id")
    @Expose
    private String vendor_id;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("herb_name")
    @Expose
    private String herb_name;
    @SerializedName("vendor_name")
    @Expose
    private String vendor_name;*/

               /* ArrayList<CartHerbModel> cartherb= adapter.getHerbCartList();
                BarakahUtils.setCurrentFragment(
                        getActivity(), R.id.homeContainer,
                        HerbsDetailFragment.newInstance(cartherb), HerbsDetailFragment.TAG
                );*/
            }
        });
        adapter.setData(cartHerbList);
        adapter.notifyDataSetChanged();
    }
}
