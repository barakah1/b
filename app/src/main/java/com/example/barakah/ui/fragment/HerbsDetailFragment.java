package com.example.barakah.ui.fragment;

import android.animation.ObjectAnimator;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import java.util.Collection;
import java.util.HashMap;

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

}
