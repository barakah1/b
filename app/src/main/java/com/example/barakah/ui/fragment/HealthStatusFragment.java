package com.example.barakah.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.barakah.R;
import com.example.barakah.models.HealthStatusModel;
import com.example.barakah.adapters.AdapterHealthStatus;
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

public class HealthStatusFragment extends Fragment {
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    public static String TAG = "HealthStatusFragment";
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private AdapterHealthStatus mAdapter;
    private Button btnSubmit;


    public HealthStatusFragment() {
        // Required empty public constructor
    }

    public static HealthStatusFragment newInstance() {
        HealthStatusFragment fragment = new HealthStatusFragment();
        Bundle args = new Bundle();

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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_health_status, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.health_status_title));

        initializeView(view);
        return view;
    }

    private void initializeView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.rcvHealth);
        btnSubmit = (Button) view.findViewById(R.id.btnSignin);
        // use a linear layout manager
        layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        // specify an adapter (see also next example)
        mAdapter = new AdapterHealthStatus(getActivity());
        recyclerView.setAdapter(mAdapter);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final ArrayList<HealthStatusModel> arrayList = new ArrayList<>();
        arrayList.add(new HealthStatusModel("الضغط", "1", false));
        arrayList.add(new HealthStatusModel("السكر", "2", false));
        arrayList.add(new HealthStatusModel("الكوليسترول", "3", false));
        arrayList.add(new HealthStatusModel("خمول الغدة الدرقية", "4", false));
        arrayList.add(new HealthStatusModel("الحموضة", "5", false));
        arrayList.add(new HealthStatusModel("نشاط الغدة الدرقية", "6", false));
        arrayList.add(new HealthStatusModel("الدورة الشهرية", "7", false));
        arrayList.add(new HealthStatusModel("الحمل", "8", false));
        arrayList.add(new HealthStatusModel("الجلطة", "9", false));
        arrayList.add(new HealthStatusModel("الكحة", "10", false));
        arrayList.add(new HealthStatusModel("متلازمة تكيس المبايض", "11", false));
        arrayList.add(new HealthStatusModel("القولون العصبي", "12", false));
        arrayList.add(new HealthStatusModel("امساك", "13", false));
        arrayList.add(new HealthStatusModel("عسر الهضم", "14", false));
        arrayList.add(new HealthStatusModel("غثيان", "15", false));

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            mDatabase.child(BarakahConstants.DbTABLE.CUSTOMER_HEALTH_STATUS).child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChildren()) {
                        HashMap<String, String> register = (HashMap<String, String>) dataSnapshot.getValue();
                        System.out.println(register);
                        Collection<String> strList = register.values();
                        for (String list : strList) {

                            for (int i = 0; i < arrayList.size(); i++) {
                                if (arrayList.get(i).getId().equals(list)) {
                                    arrayList.get(i).setChecked(true);
                                }
                            }
                        }
                        mAdapter.setData(arrayList);
                        mAdapter.notifyDataSetChanged();
                    } else {
                        mAdapter.setData(arrayList);
                        mAdapter.notifyDataSetChanged();
                    }


                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {
            mAdapter.setData(arrayList);
            mAdapter.notifyDataSetChanged();
        }


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<HealthStatusModel> arrayList = mAdapter.getData();
                if (arrayList.size() > 0) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(BarakahConstants.HEALTH_STATUS_DATA, arrayList);
                    getActivity().setResult(BarakahConstants.GET_HEALTH_STATUS, resultIntent);
                    getActivity().finish();
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
