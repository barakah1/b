package com.example.barakah.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.provider.ContactsContract;
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
import com.example.barakah.models.OrderSubItemModel;
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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

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
    private CartHerbModel orders;
    private OrderModel sameVendorOrder;

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
                //  ArrayList<CartHerbModel> ventors = new ArrayList<>(al);
                for (int i = 0; i < al.size(); i++) {
                    for (int j = 0; j < al.size(); j++) {
                        if (al.get(i).getVendor().getVendor_id().equals(al.get(j).getVendor().getVendor_id())) {
                            if (al.get(i).getDeliveryType().equals(al.get(j).getDeliveryType())) {
                                if(!al.get(i).getOid().isEmpty()){
                                   // long number = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
                                    al.get(j).setOid(String.valueOf(al.get(i).getOid()));
                                }else{
                                    long number = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
                                    al.get(i).setOid(String.valueOf(number));
                                }
                            }else{
                                if(al.get(i).getOid().isEmpty()){
                                    long number = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
                                    al.get(i).setOid(String.valueOf(number));
                                }
                            }
                        }

                    }
                }
            /*    List<CartHerbModel> ventors = new CopyOnWriteArrayList<CartHerbModel>(al);
                Iterator<CartHerbModel> iterator = ventors.iterator();
                while (iterator.hasNext()) {
                    CartHerbModel cart = iterator.next();
                    Iterator<CartHerbModel> iterator1 = ventors.iterator();
                    while (iterator1.hasNext()) {
                        CartHerbModel model = iterator1.next();

                        if (cart.getVendor().getVendor_id().equals(model.getVendor().getVendor_id())) {
                            if (cart.getDeliveryType().equals(model.getDeliveryType())) {
                                System.out.println("Same vendor same deliverytype");
                                System.out.println("VendorSize" + ventors.size());
                               // if (model.equals(cart)) {
                                  //  addHerbToOrderRecursive(cart, model);
                                  //  ventors.remove(model);
                                //}

                            } else {
                                System.out.println("Same vendor different deliverytype");
                              //  addHerbToOrder(cart, model);

                            }
                        }
                    }

                }*/
            saveOrderData(al);
                System.out.println("Same vendor different deliverytype" + al);

            }
        });
        adapter.setData(cartHerbList);
        adapter.notifyDataSetChanged();
    }

    private void saveOrderData(final ArrayList<CartHerbModel> al) {

        for (final CartHerbModel model :
                al) {
          //  long number = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
            final OrderModel orderModel = new OrderModel();
            orderModel.setId(String.valueOf(model.getOid()));
            orderModel.setVendor_name(model.getVendor().getVendor_name());
            orderModel.setVendor_id(model.getVendor().getVendor_id());
            orderModel.setVendor_name(model.getVendor().getVendor_name());
            orderModel.setDelivery_type(model.getDeliveryType());
            orderModel.setOrder_status("0");
            final OrderSubItemModel orderSubItemModel = new OrderSubItemModel();
            orderSubItemModel.setHerb_type(model.getCartModel().getHerb_type());
            orderSubItemModel.setHerb_name(model.getHerbModel().getName());
            orderSubItemModel.setQuantity(model.getCartModel().getQuantity());
            orderSubItemModel.setHerb_id(model.getHerbModel().getId());
            try {
                int quantity = orderSubItemModel.getQuantity();
                if (orderSubItemModel.getHerb_type() != null || !orderSubItemModel.getHerb_type().isEmpty()) {
                    if (orderSubItemModel.getHerb_type().equals(getActivity().getResources().getString(R.string.raw))) {
                        double price = quantity * Double.valueOf(model.getVendor().getRaw_price());
                        orderSubItemModel.setOrder_price(String.valueOf(price));
                    } else if (orderSubItemModel.getHerb_type().equals(getActivity().getResources().getString(R.string.capsule))) {
                        double price = quantity * Double.valueOf(model.getVendor().getCapsule_price());
                        orderSubItemModel.setOrder_price(String.valueOf(price));
                    }

                }
            } catch (Exception e
            ) {
            }
            mDatabase.child(BarakahConstants.DbTABLE.ORDERS).child(mAuth.getCurrentUser().getUid()).child(model.getOid()).setValue(orderModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isComplete()) {

                     /*   for (final CartHerbModel subCartModel :
                                al) {
                        }*/
                        DatabaseReference dbb = mDatabase.child(BarakahConstants.DbTABLE.ORDERS).child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).child(model.getOid()).push();
                        String keys = dbb.getKey();

                        mDatabase.child(BarakahConstants.DbTABLE.ORDERS).child(mAuth.getCurrentUser().getUid()).child(model.getOid()).child(BarakahConstants.DbTABLE.HERB_KEY).child(keys).setValue(orderSubItemModel);
                        mDatabase.child(BarakahConstants.DbTABLE.CART).child(mAuth.getCurrentUser().getUid()).child(model.getCartModel().getId()).removeValue();
                        BarakahUtils.toastMessgae(getActivity(), getResources().getString(R.string.order_placed_successfully), Toast.LENGTH_SHORT);
                         updateStoreData(orderModel,orderSubItemModel);

                    }
                }
            });
        }
    }

    private void addHerbToOrder(CartHerbModel order, final CartHerbModel herb) {
        long number = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
        final OrderModel orderModel = new OrderModel();
        orderModel.setId(String.valueOf(number));
        orderModel.setVendor_name(order.getVendor().getVendor_name());
        orderModel.setVendor_id(order.getVendor().getVendor_id());
        orderModel.setVendor_name(order.getVendor().getVendor_name());
        orderModel.setDelivery_type(order.getDeliveryType());
        orderModel.setOrder_status("0");
        final OrderSubItemModel orderSubItemModel = new OrderSubItemModel();
        orderSubItemModel.setHerb_type(herb.getCartModel().getHerb_type());
        orderSubItemModel.setHerb_name(herb.getHerbModel().getName());
        orderSubItemModel.setQuantity(herb.getCartModel().getQuantity());
        orderSubItemModel.setHerb_id(herb.getHerbModel().getId());
        try {
            int quantity = orderSubItemModel.getQuantity();
            if (orderSubItemModel.getHerb_type() != null || !orderSubItemModel.getHerb_type().isEmpty()) {
                if (orderSubItemModel.getHerb_type().equals(getActivity().getResources().getString(R.string.raw))) {
                    double price = quantity * Double.valueOf(herb.getVendor().getRaw_price());
                    orderSubItemModel.setOrder_price(String.valueOf(price));
                } else if (orderSubItemModel.getHerb_type().equals(getActivity().getResources().getString(R.string.capsule))) {
                    double price = quantity * Double.valueOf(herb.getVendor().getCapsule_price());
                    orderSubItemModel.setOrder_price(String.valueOf(price));
                }

            }
        } catch (Exception e
        ) {
        }
        DatabaseReference db = mDatabase.child(BarakahConstants.DbTABLE.ORDERS).child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).push();
        final String key = db.getKey();
        orderModel.setOrder_key(key);
        mDatabase.child(BarakahConstants.DbTABLE.ORDERS).child(mAuth.getCurrentUser().getUid()).child(key).setValue(orderModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isComplete()) {
                    DatabaseReference dbb = mDatabase.child(BarakahConstants.DbTABLE.ORDERS).child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).child(key).push();
                    String keys = dbb.getKey();

                    mDatabase.child(BarakahConstants.DbTABLE.ORDERS).child(mAuth.getCurrentUser().getUid()).child(key).child(keys).setValue(orderSubItemModel);

                    mDatabase.child(BarakahConstants.DbTABLE.CART).child(mAuth.getCurrentUser().getUid()).child(herb.getCartModel().getId()).removeValue();
                    BarakahUtils.toastMessgae(getActivity(), getResources().getString(R.string.order_placed_successfully), Toast.LENGTH_SHORT);
                    updateStoreData(orderModel, orderSubItemModel);

                }
            }
        });
    }

    private void addHerbToOrderRecursive(CartHerbModel order, final CartHerbModel herb) {
        if (orders != null) {
            if (sameVendorOrder != null) {
                if (order.equals(sameVendorOrder)) {
                    saveOrder(order, herb);
                } else {
                    recursiveOrder(order, herb);

                }
            }

        } else {
            this.orders = order;
            recursiveOrder(order, herb);
        }

    }

    private void saveOrder(CartHerbModel order, final CartHerbModel herb) {
        final OrderSubItemModel orderSubItemModel = new OrderSubItemModel();
        orderSubItemModel.setHerb_type(herb.getCartModel().getHerb_type());
        orderSubItemModel.setHerb_name(herb.getHerbModel().getName());
        orderSubItemModel.setQuantity(herb.getCartModel().getQuantity());
        orderSubItemModel.setHerb_id(herb.getHerbModel().getId());
        try {
            int quantity = orderSubItemModel.getQuantity();
            if (orderSubItemModel.getHerb_type() != null || !orderSubItemModel.getHerb_type().isEmpty()) {
                if (orderSubItemModel.getHerb_type().equals(getActivity().getResources().getString(R.string.raw))) {
                    double price = quantity * Double.valueOf(herb.getVendor().getRaw_price());
                    orderSubItemModel.setOrder_price(String.valueOf(price));
                } else if (orderSubItemModel.getHerb_type().equals(getActivity().getResources().getString(R.string.capsule))) {
                    double price = quantity * Double.valueOf(herb.getVendor().getCapsule_price());
                    orderSubItemModel.setOrder_price(String.valueOf(price));
                }

            }
        } catch (Exception e
        ) {
        }
        DatabaseReference db = mDatabase.child(BarakahConstants.DbTABLE.ORDERS).child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).child(sameVendorOrder.getOrder_key()).push();
        final String key = db.getKey();
        // sameVendorOrder.setOrder_key(key);
        mDatabase.child(BarakahConstants.DbTABLE.ORDERS).child(mAuth.getCurrentUser().getUid()).child(sameVendorOrder.getOrder_key()).child(key).setValue(orderSubItemModel).addOnCompleteListener(new OnCompleteListener<Void>() {

            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isComplete()) {
                    DatabaseReference dbb = mDatabase.child(BarakahConstants.DbTABLE.ORDERS).child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).child(key).push();
                    String keys = dbb.getKey();

                    mDatabase.child(BarakahConstants.DbTABLE.ORDERS).child(mAuth.getCurrentUser().getUid()).child(key).child(keys).setValue(orderSubItemModel);

                    mDatabase.child(BarakahConstants.DbTABLE.CART).child(mAuth.getCurrentUser().getUid()).child(herb.getCartModel().getId()).removeValue();
                    BarakahUtils.toastMessgae(getActivity(), getResources().getString(R.string.order_placed_successfully), Toast.LENGTH_SHORT);
                    // sameVendorOrder = orderModel;
                    updateStoreData(sameVendorOrder, orderSubItemModel);

                }
            }
        });
    }

    private void recursiveOrder(CartHerbModel order, final CartHerbModel herb) {
        long number = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
        final OrderModel orderModel = new OrderModel();
        orderModel.setId(String.valueOf(number));
        orderModel.setVendor_name(order.getVendor().getVendor_name());
        orderModel.setVendor_id(order.getVendor().getVendor_id());
        orderModel.setVendor_name(order.getVendor().getVendor_name());
        orderModel.setDelivery_type(order.getDeliveryType());
        orderModel.setOrder_status("0");
        final OrderSubItemModel orderSubItemModel = new OrderSubItemModel();
        orderSubItemModel.setHerb_type(herb.getCartModel().getHerb_type());
        orderSubItemModel.setHerb_name(herb.getHerbModel().getName());
        orderSubItemModel.setQuantity(herb.getCartModel().getQuantity());
        orderSubItemModel.setHerb_id(herb.getHerbModel().getId());
        try {
            int quantity = orderSubItemModel.getQuantity();
            if (orderSubItemModel.getHerb_type() != null || !orderSubItemModel.getHerb_type().isEmpty()) {
                if (orderSubItemModel.getHerb_type().equals(getActivity().getResources().getString(R.string.raw))) {
                    double price = quantity * Double.valueOf(herb.getVendor().getRaw_price());
                    orderSubItemModel.setOrder_price(String.valueOf(price));
                } else if (orderSubItemModel.getHerb_type().equals(getActivity().getResources().getString(R.string.capsule))) {
                    double price = quantity * Double.valueOf(herb.getVendor().getCapsule_price());
                    orderSubItemModel.setOrder_price(String.valueOf(price));
                }

            }
        } catch (Exception e
        ) {
        }
        DatabaseReference db = mDatabase.child(BarakahConstants.DbTABLE.ORDERS).child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).push();
        final String key = db.getKey();
        orderModel.setOrder_key(key);
        sameVendorOrder = orderModel;

        mDatabase.child(BarakahConstants.DbTABLE.ORDERS).child(mAuth.getCurrentUser().getUid()).child(key).setValue(orderModel).addOnCompleteListener(new OnCompleteListener<Void>() {

            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isComplete()) {
                    DatabaseReference dbb = mDatabase.child(BarakahConstants.DbTABLE.ORDERS).child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).child(key).push();
                    String keys = dbb.getKey();

                    mDatabase.child(BarakahConstants.DbTABLE.ORDERS).child(mAuth.getCurrentUser().getUid()).child(key).child(keys).setValue(orderSubItemModel);
                    mDatabase.child(BarakahConstants.DbTABLE.CART).child(mAuth.getCurrentUser().getUid()).child(herb.getCartModel().getId()).removeValue();
                    BarakahUtils.toastMessgae(getActivity(), getResources().getString(R.string.order_placed_successfully), Toast.LENGTH_SHORT);
                    updateStoreData(orderModel, orderSubItemModel);

                }
            }
        });
    }

    private void updateStoreData(final OrderModel orderModel, final OrderSubItemModel orderSubItemModel) {
        mDatabase.child(BarakahConstants.DbTABLE.STOREITEM).child(orderSubItemModel.getHerb_id()).child(orderModel.getVendor_id()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    VendorStoreItemModel modell = dataSnapshot.getValue(VendorStoreItemModel.class);
                    StoreItemModel model = new StoreItemModel();
                    model.setCapsule_price(modell.getCapsule_price());
                    model.setCapsule_quantity(modell.getCapsule_quantity());
                    model.setRaw_price(modell.getRaw_price());
                    model.setRaw_quantity(modell.getRaw_quantity());
                    model.setHerb_id(modell.getHerb_id());
                    model.setVendor_id(modell.getVendor_id());
                    model.setVendor_name(modell.getVendor_name());
                    if (orderSubItemModel.getHerb_type().equalsIgnoreCase(getActivity().getResources().getString(R.string.raw))) {
                        model.setRaw_quantity(model.getRaw_quantity() - orderSubItemModel.getQuantity());
                        mDatabase.child(BarakahConstants.DbTABLE.STOREITEM).child(orderSubItemModel.getHerb_id()).child(orderModel.getVendor_id()).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isComplete()) {
                                    Intent intentt = new Intent(getActivity(), MainActivity.class);
                                    intentt.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intentt.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intentt.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intentt);
                                    getActivity().finish();
                                }
                            }
                        });


                    } else if (orderSubItemModel.getHerb_type().equalsIgnoreCase(getActivity().getResources().getString(R.string.capsule))) {
                        model.setCapsule_quantity(model.getCapsule_quantity() - orderSubItemModel.getQuantity());
                        mDatabase.child(BarakahConstants.DbTABLE.STOREITEM).child(orderSubItemModel.getHerb_id()).child(orderModel.getVendor_id()).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isComplete()) {
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


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
