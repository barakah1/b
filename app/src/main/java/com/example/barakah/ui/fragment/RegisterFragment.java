package com.example.barakah.ui.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barakah.R;
import com.example.barakah.models.HealthStatusModel;
import com.example.barakah.models.RegisterModel;
import com.example.barakah.ui.activity.HomeActivity;
import com.example.barakah.ui.activity.MainActivity;
import com.example.barakah.utils.BarakahConstants;
import com.example.barakah.utils.BarakahUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;


public class RegisterFragment extends Fragment {
    public static String TAG = "RegisterFragment";
    Button signUpButton;
    Button signoutB;
    EditText signUpnNameTextInput;
    EditText signUpEmailTextInput;
    EditText signUpPasswordTextInput;
    EditText signUpaddresTextInput;
    EditText signUpPhoneTextInput;
    private DatabaseReference mDatabase;

    // CheckBox agreementCheckBox;
    TextView errorView;
    private FirebaseAuth mAuth;
    private RegisterModel registerModel;
    private TextView medical;
    private ArrayList<HealthStatusModel> arrayList;
    private Dialog progressDialog;

    public RegisterFragment() {
        // Required empty public constructor
    }

    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
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
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        initializeViews(view);
        return view;
    }

    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                checkValidation();


            }


        });
        signoutB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                intent.putExtra(BarakahConstants.HOME_ACTIVITY, BarakahConstants.HEALTH_STATUS_FM);
                startActivityForResult(intent, BarakahConstants.GET_HEALTH_STATUS);
            }


        });

    }


    private void checkValidation() {
        String name = signUpnNameTextInput.getText().toString().trim();
        String password = signUpPasswordTextInput.getText().toString().trim();
        String email = signUpEmailTextInput.getText().toString().trim();
        String address = signUpaddresTextInput.getText().toString().trim();
        String mobile = signUpPhoneTextInput.getText().toString().trim();
        if (name.isEmpty()) {
            Toast.makeText(getActivity(), getResources().getString(R.string.name_req), Toast.LENGTH_SHORT).show();
        } else if (password.isEmpty()) {
            Toast.makeText(getActivity(), getResources().getString(R.string.pass_req), Toast.LENGTH_SHORT).show();

        } else if (email.isEmpty()) {
            Toast.makeText(getActivity(), getResources().getString(R.string.email_req), Toast.LENGTH_SHORT).show();

        } else if (address.isEmpty()) {
            Toast.makeText(getActivity(), getResources().getString(R.string.address_req), Toast.LENGTH_SHORT).show();

        } else if (mobile.isEmpty()) {
            Toast.makeText(getActivity(), getResources().getString(R.string.mobile_req), Toast.LENGTH_SHORT).show();

        } else {
            registerModel = new RegisterModel();
            registerModel.setAddress(address);
            registerModel.setName(name);
            registerModel.setEmail(email);
            registerModel.setMobile(mobile);
            progressDialog = BarakahUtils.customProgressDialog(getActivity());
            login(email, password);
        }
    }

    private void login(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (registerModel != null) {
                                mDatabase.child(BarakahConstants.DbTABLE.CUSTOMER).child(user.getUid()).setValue(registerModel);

                                if (arrayList != null && arrayList.size() > 0) {
                                    DatabaseReference dr = mDatabase.child(BarakahConstants.DbTABLE.CUSTOMER_HEALTH_STATUS).child(user.getUid());
                                    for (HealthStatusModel hl : arrayList
                                    ) {
                                        dr.push().setValue(hl.getId());

                                    }
                                }
                                closeProgress();

                                Toast.makeText(getActivity(), getResources().getString(R.string.reg_success), Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(getActivity(), MainActivity.class));
                                getActivity().finish();

                            } else {
                                closeProgress();

                                Toast.makeText(getActivity(), "something went wrong", Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            closeProgress();
                            System.out.println("something" + task.getException().getMessage());
                            System.out.println("something" + task);
                        }
                    }
                });
    }

    private void initializeViews(View view) {
        signUpnNameTextInput = view.findViewById(R.id.nameF);
        signUpPasswordTextInput = view.findViewById(R.id.passF);
        signUpEmailTextInput = view.findViewById(R.id.EmailF);
        signUpPhoneTextInput = view.findViewById(R.id.PhoneF);
        signUpaddresTextInput = view.findViewById(R.id.addreddF);
        signUpButton = view.findViewById(R.id.signinB);
        errorView = view.findViewById(R.id.signUpErrorView);
        medical = view.findViewById(R.id.medical);
        signoutB = view.findViewById(R.id.signoutB);

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
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
