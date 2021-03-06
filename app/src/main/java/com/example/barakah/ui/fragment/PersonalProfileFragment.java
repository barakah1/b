package com.example.barakah.ui.fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.barakah.R;
import com.example.barakah.databinding.PersonalProfileFragmentBinding;
import com.example.barakah.models.HealthStatusModel;
import com.example.barakah.models.RegisterModel;
import com.example.barakah.ui.activity.HomeActivity;
import com.example.barakah.ui.activity.LoginActivity;
import com.example.barakah.ui.activity.MainActivity;
import com.example.barakah.utils.BarakahConstants;
import com.example.barakah.utils.BarakahUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PersonalProfileFragment extends Fragment {
    private PersonalProfileFragmentBinding binding;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private ArrayList<HealthStatusModel> arrayList;
    private RegisterModel registerModel;
    private Dialog progressDialog;

    public static PersonalProfileFragment newInstance() {
        PersonalProfileFragment fragment = new PersonalProfileFragment();
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.personal_profile_fragment, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.personal_profile));

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUserData();
        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                BarakahUtils.deletesharedData(getActivity());
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().finish();

            }

        });
        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValidation();
            }

        });
        binding.btnHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                intent.putExtra(BarakahConstants.HOME_ACTIVITY, BarakahConstants.HEALTH_STATUS_FM);
                startActivityForResult(intent, BarakahConstants.GET_HEALTH_STATUS);

            }


        });

          binding.tvChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                intent.putExtra(BarakahConstants.HOME_ACTIVITY, BarakahConstants.CHANGE_PASSWORD);
                getActivity().startActivity(intent);
            }


        });
       String em= BarakahUtils.getPref(BarakahConstants.USER_PREF.EMAIL, getActivity());
     String pass=   BarakahUtils.getPref(BarakahConstants.USER_PREF.PASSWORD, getActivity());

     if(em!=null&&pass!=null) {
         recentLogin(em,pass);
     }


    }

    private void setUserData() {
        progressDialog = BarakahUtils.customProgressDialog(getActivity());
        FirebaseUser user = mAuth.getCurrentUser();
        mDatabase.child(BarakahConstants.DbTABLE.CUSTOMER).child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    RegisterModel register = dataSnapshot.getValue(RegisterModel.class);
                    System.out.println();
                    binding.etMobile.setText(register.getMobile());
                    binding.etAddress.setText(register.getAddress());
                    binding.etName.setText(register.getName());
                    binding.etEmail.setText(register.getEmail());
                }
                closeProgress();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                closeProgress();
            }
        });

    }


    private void checkValidation() {
        String name = binding.etName.getText().toString().trim();
        String email = binding.etEmail.getText().toString().trim();
        String address = binding.etAddress.getText().toString().trim();
        String mobile = binding.etMobile.getText().toString().trim();
        if (name.isEmpty()) {
            Toast.makeText(getActivity(), getResources().getString(R.string.name_req), Toast.LENGTH_SHORT).show();
        } else if (email.isEmpty()) {
            Toast.makeText(getActivity(), getResources().getString(R.string.email_req), Toast.LENGTH_SHORT).show();
        }else if (!BarakahUtils.isValidEmailId(email)) {
            Toast.makeText(getActivity(), getResources().getString(R.string.invalid_email), Toast.LENGTH_SHORT).show();
        } else if (address.isEmpty()) {
            Toast.makeText(getActivity(), getResources().getString(R.string.address_req), Toast.LENGTH_SHORT).show();
        } else if (mobile.isEmpty()) {
            Toast.makeText(getActivity(), getResources().getString(R.string.mobile_req), Toast.LENGTH_SHORT).show();
        }else if (mobile.length()<10) {
            Toast.makeText(getActivity(), getResources().getString(R.string.plz_enter_valid_mob), Toast.LENGTH_SHORT).show();
        }
        else {
            registerModel = new RegisterModel();
            registerModel.setAddress(address);
            registerModel.setName(name);
            registerModel.setEmail(email);
            registerModel.setMobile(mobile);
            progressDialog = BarakahUtils.customProgressDialog(getActivity());
            login(email);
        }
    }

    private void login(final String email) {
        final FirebaseUser user = mAuth.getCurrentUser();
        user.updateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isComplete()) {
                  if(task.isSuccessful()){
                      BarakahUtils.putPref(BarakahConstants.USER_PREF.EMAIL, email, getActivity());
                  }
                                updateUserData(user);
                }
                closeProgress();

            }
        });

    }

    private void updateUserData(FirebaseUser user) {
        if (registerModel != null) {
            mDatabase.child(BarakahConstants.DbTABLE.CUSTOMER).child(user.getUid()).setValue(registerModel);
            if (arrayList != null && arrayList.size() > 0) {
                DatabaseReference dr = mDatabase.child(BarakahConstants.DbTABLE.CUSTOMER_HEALTH_STATUS).child(user.getUid());
                dr.removeValue();
                for (HealthStatusModel hl : arrayList
                ) {
                    dr.push().setValue(hl.getId());

                }
            }
            closeProgress();
            Toast.makeText(getActivity(), getResources().getString(R.string.profile_update_success), Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == BarakahConstants.GET_HEALTH_STATUS) {
            if (data != null) {
                arrayList = (ArrayList<HealthStatusModel>) data.getExtras().getSerializable(BarakahConstants.HEALTH_STATUS_DATA);
            }
        }
    }

    public void closeProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

      private void recentLogin(final String email, final String password) {
          final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
          AuthCredential credential = EmailAuthProvider
                  .getCredential(email, password);
          user.reauthenticate(credential);
    }

}
