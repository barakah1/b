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
        toolbar.setTitle("الصفحة الرئيسية");
        fragment = FragmentHome.newInstance();
        loadFragment(fragment);
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
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


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
