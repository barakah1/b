package com.example.barakah.ui.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.barakah.R;
import com.example.barakah.adapters.CartAdapter;
import com.example.barakah.adapters.CurrentOrderAdapter;
import com.example.barakah.databinding.FragmentCartBinding;
import com.example.barakah.models.CartHerbModel;
import com.example.barakah.models.CartModel;
import com.example.barakah.models.HerbsModel;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {

    private FragmentCartBinding binding;
    private CartAdapter adapter;
    private Dialog progressDialog;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    public CartFragment() {
        // Required empty public constructor
    }

    public static CartFragment newInstance() {
        CartFragment fragment = new CartFragment();
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
        binding = FragmentCartBinding.inflate(inflater);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.cart));

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.rcvCurrentOrders.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new CartAdapter(getActivity());
        binding.rcvCurrentOrders.setAdapter(adapter);
        progressDialog = BarakahUtils.customProgressDialog(getActivity());

        getCardData();

        binding.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupAlertDialog();

            }
        });
    }

    public void popupAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_message).setTitle(R.string.dialog_title);
        builder.setMessage(getResources().getString(R.string.add_to_cart_message))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ArrayList<CartHerbModel> cartHerbModels = adapter.getHerbCartList();
                        if (cartHerbModels != null && cartHerbModels.size() > 0) {
                            Intent intent = new Intent(getActivity(), HomeActivity.class);
                            intent.putExtra(BarakahConstants.HOME_ACTIVITY, BarakahConstants.SELECT_HERBS_VENDOR);
                            intent.putExtra(BarakahConstants.CART_DATA, cartHerbModels);
                            startActivity(intent);
                        }
                    }
                })
                .setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.setTitle(getResources().getString(R.string.add_to_cart_title));
        alert.show();
    }

    private void getCardData() {
        final FirebaseUser firebaseUser = mAuth.getCurrentUser();

        mDatabase.child(BarakahConstants.DbTABLE.CART).child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    ArrayList<CartModel> herbsModels = new ArrayList<CartModel>();
                    Iterator<DataSnapshot> data = dataSnapshot.getChildren().iterator();
                    while (data.hasNext()) {
                        DataSnapshot da = data.next();
                        System.out.println(da.getValue());
                        CartModel model = da.getValue(CartModel.class);
                        herbsModels.add(model);
                    }
                    //   adapter.setData(herbsModels);

                    if (herbsModels.size() > 0) {

                        getAllHerbs(herbsModels);
                    } else {

                            binding.rcvCurrentOrders.setVisibility(View.GONE);
                            binding.llNoData.setVisibility(View.VISIBLE);



                        binding.btnAddToCart.setVisibility(View.GONE);

                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                } else {
                    binding.btnAddToCart.setVisibility(View.GONE);
                    binding.rcvCurrentOrders.setVisibility(View.GONE);
                    binding.llNoData.setVisibility(View.VISIBLE);
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getAllHerbs(final ArrayList<CartModel> cart) {
        mDatabase.child(BarakahConstants.DbTABLE.HERB).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    ArrayList<HerbsModel> herbsModels = new ArrayList<HerbsModel>();
                    Iterator<DataSnapshot> data = dataSnapshot.getChildren().iterator();
                    while (data.hasNext()) {

                        HerbsModel model = data.next().getValue(HerbsModel.class);
                      //  model.setId(dataSnapshot.getKey());
                        System.out.println(model);
                        herbsModels.add(model);
                    }
                    ArrayList<CartHerbModel> herbsCartModels = new ArrayList<CartHerbModel>();
                    for (CartModel cartModel :
                            cart) {

                        for (HerbsModel herbModel :
                                herbsModels) {
                            if (cartModel.getHerb_id().equalsIgnoreCase(herbModel.getId())) {
                                CartHerbModel cartHerbModel = new CartHerbModel();
                                cartHerbModel.setCartModel(cartModel);
                                cartHerbModel.setHerbModel(herbModel);
                                herbsCartModels.add(cartHerbModel);
                            }
                        }

                    }
                    if (herbsCartModels.size() > 0) {
                        binding.btnAddToCart.setVisibility(View.VISIBLE);
                        binding.llCardNote.setVisibility(View.VISIBLE);


                    } else {
                        binding.btnAddToCart.setVisibility(View.GONE);
                    }
                    adapter.setData(herbsCartModels);
                    adapter.notifyDataSetChanged();
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                } else {
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
            }
        });
    }
}
