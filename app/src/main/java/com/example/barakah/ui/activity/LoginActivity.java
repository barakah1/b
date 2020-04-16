package com.example.barakah.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.barakah.R;
import com.example.barakah.databinding.ActivityLoginBinding;
import com.example.barakah.ui.fragment.LoginFragment;
import com.example.barakah.ui.fragment.RegisterFragment;
import com.example.barakah.utils.BarakahConstants;
import com.example.barakah.utils.BarakahUtils;

public class LoginActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {

    private Fragment fragment;
    private ActivityLoginBinding binding;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.activity_login);
        binding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_login);
        toolbar = findViewById(R.id.toolbarHome);
        toolbar.setTitleTextAppearance(this, R.style.MyTitleTextApperance);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        getSupportFragmentManager().addOnBackStackChangedListener(this);
        boolean isLogedIn = BarakahUtils.getPrefBoolean(BarakahConstants.USER_PREF.IS_LOGEDIN, LoginActivity.this);
        if (isLogedIn) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            LoginActivity.this.finish();
        } else {
            toolbar.setVisibility(View.GONE);
            BarakahUtils.setCurrentFragment(
                    LoginActivity.this, R.id.loginContainer,
                    LoginFragment.newInstance(), LoginFragment.TAG
            );
        }


    }

    private void setFragment() {


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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == BarakahConstants.GET_HEALTH_STATUS) {
            if (data != null) {
                if (fragment instanceof RegisterFragment) {
                    fragment.onActivityResult(requestCode, resultCode, data);
                }
            }
        }
    }
}
