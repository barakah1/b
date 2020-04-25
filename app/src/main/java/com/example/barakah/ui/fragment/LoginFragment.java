package com.example.barakah.ui.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barakah.R;
import com.example.barakah.models.RegisterModel;
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


public class LoginFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static String TAG = "LoginFragment";
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private Dialog progressDialog;

    private EditText etEmail;
    private EditText etPassowrd;
    private Button loginBtn;
    private TextView registerBtn;
    private TextView forgotPassword;


    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initializeViews(view);
        return view;
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
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                checkValidation();


            }


        });
        System.out.println(registerBtn.getFontFeatureSettings());
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BarakahUtils.setCurrentFragment(
                        getActivity(), R.id.loginContainer,
                        RegisterFragment.newInstance(), RegisterFragment.TAG
                );

            }


        });
          forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BarakahUtils.setCurrentFragment(
                        getActivity(), R.id.loginContainer,
                        ForgotPasswordFragment.newInstance(), ForgotPasswordFragment.TAG
                );

            }


        });


    }

    private void checkValidation() {
        String email = etEmail.getText().toString().trim();
        String password = etPassowrd.getText().toString().trim();
        if (email.isEmpty()) {
            Toast.makeText(getActivity(), getResources().getString(R.string.email_req), Toast.LENGTH_SHORT).show();
        } else if (password.isEmpty()) {
            Toast.makeText(getActivity(), getResources().getString(R.string.pass_req), Toast.LENGTH_SHORT).show();
        } else {
            progressDialog = BarakahUtils.customProgressDialog(getActivity());
            login(email, password);
        }
    }

    private void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            closeProgress();
                            Toast.makeText(getActivity(), getResources().getString(R.string.success_login), Toast.LENGTH_SHORT).show();
                            BarakahUtils.putPrefBoolean(BarakahConstants.USER_PREF.IS_LOGEDIN, true, getActivity());
                            startActivity(new Intent(getActivity(), MainActivity.class));
                            getActivity().finish();
                        } else {
                            closeProgress();
                            System.out.println("something" + task);
                            Toast.makeText(getActivity(),"كلمة المرور او البريد الالكتروني غير صحيح", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void initializeViews(View view) {
        etEmail = view.findViewById(R.id.etEmail);
        forgotPassword = view.findViewById(R.id.tvForgotPassword);
        etPassowrd = view.findViewById(R.id.etPass);
        registerBtn = view.findViewById(R.id.tvSignup);
        loginBtn = view.findViewById(R.id.btnSignin);
    }

    public void closeProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
