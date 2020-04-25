package com.example.barakah.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.barakah.R;
import com.example.barakah.utils.BarakahUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ForgotPasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForgotPasswordFragment extends Fragment {

    public static String TAG = "ForgotPasswordFragment";

    private EditText etEmail;
    private Button submit;

    public ForgotPasswordFragment() {
        // Required empty public constructor
    }
  public static ForgotPasswordFragment newInstance() {
        ForgotPasswordFragment fragment = new ForgotPasswordFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.forgot_pass));
        initialization(view);
        return view;
    }

    private void initialization(View view) {

        etEmail = view.findViewById(R.id.etEmail);
        submit = view.findViewById(R.id.btnSubmit);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();

                if (email.isEmpty()) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.email_req), Toast.LENGTH_SHORT).show();

                } else if (!BarakahUtils.isValidEmailId(email)) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.invalid_email), Toast.LENGTH_SHORT).show();

                } else {
                    sendForgotPasswordLink(email);

                }
            }
        });
    }

    private void sendForgotPasswordLink(String email) {

        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Log.d(TAG, "Email sent.");
                            Toast.makeText(getActivity(), getResources().getString(R.string.reset_pass_email_sent), Toast.LENGTH_SHORT).show();
                            getActivity().getSupportFragmentManager().popBackStack();
                        }
                    }
                });
    }
}
