package com.example.barakah.ui.fragment;

import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.barakah.R;
import com.example.barakah.databinding.FragmentHerbsDetailBinding;
import com.example.barakah.models.CartModel;
import com.example.barakah.models.FavouriteModel;
import com.example.barakah.models.HerbsModel;
import com.example.barakah.utils.BarakahConstants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * A simple {@link Fragment} subclass.
 */
public class HerbsDetailFragment extends Fragment {
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FragmentHerbsDetailBinding binding;
    private HerbsModel herbsModel;

    public HerbsDetailFragment() {
        // Required empty public constructor
    }

    public static String TAG = "HerbsDetailFragment";

    public static HerbsDetailFragment newInstance(HerbsModel herbModel) {
        HerbsDetailFragment fragment = new HerbsDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(BarakahConstants.HERBS_MODEL, herbModel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            herbsModel = (HerbsModel) getArguments().getSerializable(BarakahConstants.HERBS_MODEL);
        }
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_herbs_detail, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.herb_details));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (herbsModel != null) {
            setHerbsData(herbsModel);
            binding.btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showAlertDialog();

                }
            });
            binding.btnAddToFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addToFavourite();
                }
            });
        }

    }

    private void addToFavourite() {
        final FirebaseUser user = mAuth.getCurrentUser();
        FavouriteModel fav = new FavouriteModel(herbsModel.getId(), true);
        mDatabase.child(BarakahConstants.DbTABLE.FAVOURITE).child(user.getUid()).child(herbsModel.getId()).setValue(fav);
        Toast.makeText(getActivity(), getResources().getText(R.string.herb_fav_success), Toast.LENGTH_SHORT).show();

    }

    public void setHerbsData(HerbsModel herbsData) {
        binding.cardBenefit.setCardDescription(herbsData.getBenefit());
        binding.cardConflict.setCardDescription(herbsData.getInteraction());
        binding.cardEffects.setCardDescription(herbsData.getSide_effect());
        binding.tvTitle.setText(herbsModel.getName());
        binding.herbInfo.setText(herbsModel.getDescription());
        Glide.with(getActivity()).load(herbsData.getImage()).into(binding.herbPic);

    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle(getResources().getString(R.string.select_herb_title));
        String[] items = {getResources().getString(R.string.capsule), getResources().getString(R.string.raw)};
        final int checkedItem = 0;
        alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        checkUserMedicalHistory(which);
                        dialog.dismiss();

                        break;
                    case 1:
                        checkUserMedicalHistory(which);
                        dialog.dismiss();
                        break;
                }
            }
        });

        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }

    private void checkUserMedicalHistory(final int which) {
        final FirebaseUser firebaseUser = mAuth.getCurrentUser();
        mDatabase.child(BarakahConstants.DbTABLE.CUSTOMER_HEALTH_STATUS).child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    Iterator<DataSnapshot> dataSnapshotIterator = dataSnapshot.getChildren().iterator();
                    //  System.out.println(dataSnapshot.getChildren().iterator());
                    //addHerbToCart(firebaseUser.getUid(), which);

                } else {
                    //add herb to cart
                    addHerbToCart(firebaseUser.getUid(), which);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void addHerbToCart(final String uid, final int which) {
        String herbType = "";
        if (which == 0) {
            herbType = getResources().getString(R.string.capsule);
        } else if (which == 1) {
            herbType = getResources().getString(R.string.raw);

        }
        final String finalHerbType = herbType;
        mDatabase.child(BarakahConstants.DbTABLE.CART).child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    System.out.println(dataSnapshot.getChildrenCount());
                    ArrayList<CartModel> herbsModels = new ArrayList<CartModel>();
                    Iterator<DataSnapshot> data = dataSnapshot.getChildren().iterator();
                    System.out.println(data);
                    boolean isAddNewItem = true;

                    while (data.hasNext()) {
                        DataSnapshot da = data.next();
                        System.out.println(da.getValue());
                        CartModel model = da.getValue(CartModel.class);
                        if (model.getHerb_id().equalsIgnoreCase(herbsModel.getId())) {
                            if (model.getHerb_type().trim().equalsIgnoreCase(finalHerbType.trim())) {
                                String key = da.getKey();
                                isAddNewItem = false;
                                int quantity = model.getQuantity() + 1;
                                mDatabase.child(BarakahConstants.DbTABLE.CART).child(uid).child(key).child(BarakahConstants.quantity).setValue(quantity);
                                break;
                            } else {
                                isAddNewItem = true;
                            }

                        }
                    }
                    if (isAddNewItem) {
                        CartModel cartModel = new CartModel();
                        cartModel.setHerb_id(herbsModel.getId());
                        cartModel.setHerb_type(finalHerbType);
                        DatabaseReference dr = mDatabase.child(BarakahConstants.DbTABLE.CART).child(uid).push();
                        cartModel.setId(dr.getKey());
                        mDatabase.child(BarakahConstants.DbTABLE.CART).child(uid).child(dr.getKey()).setValue(cartModel);
                    }

                } else {
                    //add herb to cart
                    CartModel cartModel = new CartModel();
                    cartModel.setHerb_id(herbsModel.getId());
                    cartModel.setHerb_type(finalHerbType);
                    mDatabase.child(BarakahConstants.DbTABLE.CART).child(uid).push().setValue(cartModel);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void showConflict() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle(getResources().getString(R.string.select_herb_title));
        String[] items = {getResources().getString(R.string.capsule), getResources().getString(R.string.raw)};
        final int checkedItem = 0;
    /*    alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        checkUserMedicalHistory(which);
                        dialog.dismiss();

                        break;
                    case 1:
                        checkUserMedicalHistory(which);
                        dialog.dismiss();
                        break;
                }
            }
        });*/

        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }

}
