package com.example.barakah.ui.fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePasswordFragment extends Fragment {
    public static String TAG = "ChangePasswordFragment";

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
        String email = etPass.getText().toString().trim();
        String password = etConfirmPass.getText().toString().trim();
        if (email.isEmpty()) {
            Toast.makeText(getActivity(), getResources().getString(R.string.pass_req), Toast.LENGTH_SHORT).show();
        } else if (password.isEmpty()) {
            Toast.makeText(getActivity(), getResources().getString(R.string.confirm_pass_req), Toast.LENGTH_SHORT).show();
        } else if (!email.equals(password)) {
            Toast.makeText(getActivity(), getResources().getString(R.string.confirm_pass_not_matched), Toast.LENGTH_SHORT).show();
        } else {
            progressDialog = BarakahUtils.customProgressDialog(getActivity());
            login(password);
        }
    }

    private void login(String password) {
        final FirebaseUser user = mAuth.getCurrentUser();

        user.updatePassword(password).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    closeProgress();

                    Toast.makeText(getActivity(), getResources().getString(R.string.pass_change_success), Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                } else closeProgress();
            }
        });
    }


    public void closeProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
