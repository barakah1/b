package com.example.barakah.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.barakah.R;
import com.example.barakah.adapters.FavouriteAdapter;
import com.example.barakah.adapters.HomeAdapter;
import com.example.barakah.databinding.FragmentFavouriteBinding;
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
import java.util.Iterator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavouriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavouriteFragment extends Fragment {


    private FragmentFavouriteBinding binding;
    private FavouriteAdapter adapter;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private ArrayList<HerbsModel> herbsModels;
    private ArrayList<FavouriteModel> favouriteList;

    public FavouriteFragment() {
        // Required empty public constructor
    }


    public static FavouriteFragment newInstance() {
        FavouriteFragment fragment = new FavouriteFragment();
        Bundle args = new Bundle();
        ;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favourite, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.favourites));

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.rcvHome.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new FavouriteAdapter(getActivity());
        binding.rcvHome.setAdapter(adapter);
        getHomeData();
    }

    private void getFavData() {
        final FirebaseUser user = mAuth.getCurrentUser();

        mDatabase.child(BarakahConstants.DbTABLE.FAVOURITE).child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    favouriteList = new ArrayList<FavouriteModel>();
                    Iterator<DataSnapshot> data = dataSnapshot.getChildren().iterator();
                    while (data.hasNext()) {
                        FavouriteModel model = data.next().getValue(FavouriteModel.class);
                        System.out.println(model);
                        favouriteList.add(model);
                    }
                    setAdapterdata();


                }else{
                    binding.rcvHome.setVisibility(View.GONE);
                    binding.llNoData.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setAdapterdata() {
        ArrayList<HerbsModel> herbsModelss = new ArrayList<>();
        if (herbsModels != null && herbsModels.size() > 0) {
            if (favouriteList != null && favouriteList.size() > 0) {
                for (FavouriteModel favModel : favouriteList
                ) {
                    for (HerbsModel herb : herbsModels
                    ) {
                        if (favModel.getHerbId().equalsIgnoreCase(herb.getId())) {
                            if (favModel.getIsFav()) {
                                herbsModelss.add(herb);
                            }
                        }

                    }

                }
                if(herbsModelss.size()>0){
                    binding.llNoData.setVisibility(View.GONE);
                    binding.rcvHome.setVisibility(View.VISIBLE);
                    adapter.setData(herbsModelss);
                    adapter.notifyDataSetChanged();
                }else{

                    binding.rcvHome.setVisibility(View.GONE);
                    binding.llNoData.setVisibility(View.VISIBLE);
                }

            }
        }
    }


    public void getHomeData() {
        mDatabase.child(BarakahConstants.DbTABLE.HERB).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    herbsModels = new ArrayList<HerbsModel>();
                    Iterator<DataSnapshot> data = dataSnapshot.getChildren().iterator();
                    while (data.hasNext()) {
                        HerbsModel model = data.next().getValue(HerbsModel.class);
                        System.out.println(model);
                        herbsModels.add(model);
                    }
                    getFavData();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
