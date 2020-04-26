package com.example.barakah.ui.fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.barakah.R;
import com.example.barakah.utils.BarakahUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePasswordFragment extends Fragment {
    public static String TAG = "ChangePasswordFragment";

    private TextInputEditText etEmail;
    private TextInputEditText etPass;
    private TextInputEditText etConfirmPass;
    private Dialog progressDialog;
    private FirebaseAuth mAuth;
    private Button submit;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    public static ChangePasswordFragment newInstance() {
        ChangePasswordFragment fragment = new ChangePasswordFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.reset_pass));

        initialization(view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

    }

    private void initialization(View view) {
        etEmail = view.findViewById(R.id.etEmail);
        etPass = view.findViewById(R.id.etPass);
        etConfirmPass = view.findViewById(R.id.etConfirmPass);
        submit = view.findViewById(R.id.btnSubmit);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });
    }

    private void checkValidation() {
        String email = etEmail.getText().toString().trim();
        String password = etPass.getText().toString().trim();
        String newPassword = etConfirmPass.getText().toString().trim();
        if (email.isEmpty()) {
            Toast.makeText(getActivity(), getResources().getString(R.string.email_req), Toast.LENGTH_SHORT).show();
        } else if (!BarakahUtils.isValidEmailId(email)) {
            Toast.makeText(getActivity(), getResources().getString(R.string.invalid_email), Toast.LENGTH_SHORT).show();
        }else if (password.isEmpty()) {
            Toast.makeText(getActivity(), getResources().getString(R.string.plz_enter_old_pass), Toast.LENGTH_SHORT).show();
        } else if (newPassword.isEmpty()) {
            Toast.makeText(getActivity(), getResources().getString(R.string.plz_enter_new_pass), Toast.LENGTH_SHORT).show();
        } else {
            progressDialog = BarakahUtils.customProgressDialog(getActivity());
            login(email,password,newPassword);
        }
    }

    private void login(String email, String pass, final String newPass) {
      //  final FirebaseUser user = mAuth.getCurrentUser();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

// Get auth credentials from the user for re-authentication. The example below shows
// email and password credentials but there are multiple possible providers,
// such as GoogleAuthProvider or FacebookAuthProvider.
        AuthCredential credential = EmailAuthProvider
                .getCredential(email, pass);

// Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            user.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        closeProgress();

                                        Toast.makeText(getActivity(), getResources().getString(R.string.pass_change_success), Toast.LENGTH_SHORT).show();
                                        getActivity().finish();

                                    } else {
                                        closeProgress();

                                        Toast.makeText(getActivity(), getResources().getString(R.string.pass_update_fail), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            closeProgress();

                            Toast.makeText(getActivity(), getResources().getString(R.string.invalid_credentials), Toast.LENGTH_SHORT).show();

                            Log.d(TAG, "Error auth failed");
                        }
                    }
                });
     /*   user.updatePassword(password).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    closeProgress();

                    Toast.makeText(getActivity(), getResources().getString(R.string.pass_change_success), Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                } else closeProgress();
            }
        });*/
    }


    public void closeProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
