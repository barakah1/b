package com.example.barakah.ui.activity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barakah.R;
import com.example.barakah.databinding.ActivityMainBinding;
import com.example.barakah.models.RegisterModel;
import com.example.barakah.ui.fragment.CartFragment;
import com.example.barakah.ui.fragment.FavouriteFragment;
import com.example.barakah.ui.fragment.FragmentHome;
import com.example.barakah.ui.fragment.LoginFragment;
import com.example.barakah.ui.fragment.OrdersFragment;
import com.example.barakah.ui.fragment.PersonalProfileFragment;
import com.example.barakah.utils.BarakahConstants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {
    private static final String TAG = "MainActivity";
    Button signUpButton;
    EditText signUpnNameTextInput;
    EditText signUpEmailTextInput;
    EditText signUpPasswordTextInput;
    EditText signUpaddresTextInput;
    EditText signUpPhoneTextInput;

    // CheckBox agreementCheckBox;
    TextView errorView;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private RegisterModel registerModel;
    private Fragment fragment;
    private ActivityMainBinding binding;

    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        toolbar =  findViewById(R.id.toolbarHome);
        toolbar.setTitleTextAppearance(this, R.style.MyTitleTextApperance);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        binding.navigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
    /*  binding.navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
          @Override
          public boolean onNavigationItemSelected(@NonNull MenuItem item) {
              return false;
          }
      });*/
      /*  mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        signUpnNameTextInput = findViewById(R.id.nameF);
        signUpPasswordTextInput = findViewById(R.id.passF);
        signUpEmailTextInput = findViewById(R.id.EmailF);
        signUpPhoneTextInput = findViewById(R.id.PhoneF);
        signUpaddresTextInput = findViewById(R.id.addreddF);
        signUpButton = findViewById(R.id.signinB);
        errorView = findViewById(R.id.signUpErrorView);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                checkValidation();


            }


        });
*/

    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.home:
                    toolbar.setTitle("الصفحة الرئيسية");
                    fragment = FragmentHome.newInstance();
                    loadFragment(fragment);
                    return true;
                case R.id.cart:
                    toolbar.setTitle("السلة");
                    fragment = CartFragment.newInstance();
                    loadFragment(fragment);
                    return true;
                case R.id.favourite:
                    toolbar.setTitle("المفضلة");
                    ;
                    fragment = FavouriteFragment.newInstance();
                    loadFragment(fragment);
                    return true;
                case R.id.orders:
                    toolbar.setTitle("الطلبات");
                    fragment = OrdersFragment.newInstance();
                    loadFragment(fragment);
                    return true;
                case R.id.navProfile:
                    toolbar.setTitle("الصفحة الرئيسية");
                    fragment = PersonalProfileFragment.newInstance();
                    loadFragment(fragment);
                    return true;

            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
/*

    private void checkValidation() {

        String name = signUpnNameTextInput.getText().toString().trim();
        String password = signUpPasswordTextInput.getText().toString().trim();
        String email = signUpEmailTextInput.getText().toString().trim();
        String address = signUpaddresTextInput.getText().toString().trim();
        String mobile = signUpPhoneTextInput.getText().toString().trim();


        if (name.isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.name_req), Toast.LENGTH_SHORT).show();
        } else if (password.isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.pass_req), Toast.LENGTH_SHORT).show();

        } else if (email.isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.email_req), Toast.LENGTH_SHORT).show();

        } else if (address.isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.address_req), Toast.LENGTH_SHORT).show();

        } else if (mobile.isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.mobile_req), Toast.LENGTH_SHORT).show();

        } else {
            registerModel = new RegisterModel();
            registerModel.setAddress(address);
            registerModel.setName(name);
            registerModel.setEmail(email);
            registerModel.setMobile(mobile);
            login(email, password);
        }
    }

    private void login(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (registerModel != null) {
                                mDatabase.child(BarakahConstants.DbTABLE.CUSTOMER).child(user.getUid()).setValue(registerModel);
                            }
                        } else {
                            //show error
                        }
                    }
                });
    }
*/


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        fragment = getSupportFragmentManager().findFragmentById(R.id.loginContainer);
        if (fragment instanceof LoginFragment


        ) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onBackStackChanged() {
        fragment = getSupportFragmentManager().findFragmentById(R.id.loginContainer);
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            //  getSupportActionBar()!!.setDis(true)
            // supportActionBar!!.setHomeButtonEnabled(true)
            /*binding.toolbarMain!!.setNavigationOnClickListener {
                if (mFragment is ProfileFragment
                    || mFragment is MyUnitFragment
                        || mFragment is MyBulletinsFragment
                    || mFragment is MessagingFragment
                        || mFragment is SelectLanguageFragment
                    || mFragment is HomeFragment
                        || mFragment is HomeFragment
                    || mFragment is SignInFragment
                        || mFragment is ForgotPasswordFragment
                    || mFragment is AddEmergencyContactFragment
                        || mFragment is Scan_ORCode_Fragment
                    || mFragment is VisitorDetailsFragment
                        || mFragment is CreatePostFragment
                    || mFragment is UpdateBulletinsFragment
                ) {
                    this@MainActivity.finish()
                } else {
                    supportFragmentManager.popBackStack()
                }
            }*/
        } else {
            // supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        }


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

}
