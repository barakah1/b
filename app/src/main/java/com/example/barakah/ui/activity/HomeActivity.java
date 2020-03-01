package com.example.barakah.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.barakah.R;
import com.example.barakah.ui.fragment.HealthStatusFragment;
import com.example.barakah.ui.fragment.LoginFragment;
import com.example.barakah.ui.fragment.RegisterFragment;
import com.example.barakah.utils.BarakahConstants;
import com.example.barakah.utils.BarakahUtils;

public class HomeActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {

    private String fragmentType;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            fragmentType = bundle.getString(BarakahConstants.HOME_ACTIVITY);
        }

        setFragment();

    }

    private void setFragment() {
        if (fragmentType != null) {
            if (fragmentType.equals(BarakahConstants.HEALTH_STATUS_FM)) {
                BarakahUtils.setCurrentFragment(
                        HomeActivity.this, R.id.homeContainer,
                        HealthStatusFragment.newInstance(), HealthStatusFragment.TAG
                );
            }
        }

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


}
