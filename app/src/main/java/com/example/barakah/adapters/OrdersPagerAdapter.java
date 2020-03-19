package com.example.barakah.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.barakah.ui.fragment.CurrentOrdersFragment;
import com.example.barakah.ui.fragment.PreviousOrdersFragment;

public class OrdersPagerAdapter extends FragmentStateAdapter {
    int NUM_PAGES = 2;

    public OrdersPagerAdapter(@NonNull FragmentActivity fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new CurrentOrdersFragment();
        }
        if (position == 1) {
            return new PreviousOrdersFragment();
        }
        return new Fragment();    }





    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }
}
