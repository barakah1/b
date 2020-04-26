package com.example.barakah.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.barakah.R;
import com.example.barakah.databinding.ActivityHomeBinding;
import com.example.barakah.models.CartHerbModel;
import com.example.barakah.models.HerbsModel;
import com.example.barakah.models.OrderModel;
import com.example.barakah.ui.fragment.ChangePasswordFragment;
import com.example.barakah.ui.fragment.HealthStatusFragment;
import com.example.barakah.ui.fragment.HerbsDetailFragment;
import com.example.barakah.ui.fragment.LoginFragment;
import com.example.barakah.ui.fragment.OrderDetailFragment;
import com.example.barakah.ui.fragment.RegisterFragment;
import com.example.barakah.ui.fragment.SelectHerbVendorFragment;
import com.example.barakah.utils.BarakahConstants;
import com.example.barakah.utils.BarakahUtils;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {

    private String fragmentType;
    private Fragment fragment;
    private HerbsModel herbModel;
    private ActivityHomeBinding binding;
    private Toolbar toolbar;
    private ArrayList<CartHerbModel> cartHerbList;
    private OrderModel orderModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(HomeActivity.this, R.layout.activity_home);
        toolbar = findViewById(R.id.toolbarHome);
        toolbar.setTitleTextAppearance(this, R.style.MyTitleTextApperance);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            fragmentType = bundle.getString(BarakahConstants.HOME_ACTIVITY);
            herbModel = (HerbsModel) bundle.getSerializable(BarakahConstants.HERBS_MODEL);
            cartHerbList = (ArrayList<CartHerbModel>) bundle.getSerializable(BarakahConstants.CART_DATA);
            orderModel = (OrderModel) bundle.getSerializable(BarakahConstants.ORDER_MODEL);
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
          else  if (fragmentType.equals(BarakahConstants.HERBS_DETAILS)) {
                getSupportActionBar().setHomeButtonEnabled(true);
                BarakahUtils.setCurrentFragment(
                        HomeActivity.this, R.id.homeContainer,
                        HerbsDetailFragment.newInstance(herbModel), HerbsDetailFragment.TAG
                );
            }
           else if (fragmentType.equals(BarakahConstants.SELECT_HERBS_VENDOR)) {
                BarakahUtils.setCurrentFragment(
                        HomeActivity.this, R.id.homeContainer,
                        SelectHerbVendorFragment.newInstance(cartHerbList), SelectHerbVendorFragment.TAG
                );
            }
          else  if (fragmentType.equals(BarakahConstants.ORDER_DETAILS)) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                BarakahUtils.setCurrentFragment(
                        HomeActivity.this, R.id.homeContainer,
                        OrderDetailFragment.newInstance(orderModel), OrderDetailFragment.TAG
                );
            } else  if (fragmentType.equals(BarakahConstants.CHANGE_PASSWORD)) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                BarakahUtils.setCurrentFragment(
                        HomeActivity.this, R.id.homeContainer,
                        ChangePasswordFragment.newInstance(), ChangePasswordFragment.TAG

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
