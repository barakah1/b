package com.example.barakah.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.barakah.R;
import com.example.barakah.adapters.OrdersPagerAdapter;
import com.example.barakah.databinding.FragmentOrdersBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import static androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrdersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrdersFragment extends Fragment {

    private FragmentOrdersBinding binding;

    public OrdersFragment() {
        // Required empty public constructor
    }

    public static OrdersFragment newInstance() {
        OrdersFragment fragment = new OrdersFragment();
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
        //return inflater.inflate(R.layout.fragment_orders, container, false);
        binding = FragmentOrdersBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.pager.setAdapter(new OrdersPagerAdapter(getActivity()));
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(binding.tabs, binding.pager, true, new TabLayoutMediator.TabConfigurationStrategy() {

            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if (position == 0) {
                    tab.setText(getResources().getString(R.string.current_orders));

                } else if (position == 1) {
                    tab.setText(getResources().getString(R.string.previous_orders));

                }


            }
        });
        tabLayoutMediator.attach();

                /*  TabLayoutMediator(binding.tabs, binding.pager) { tab, position ->
            tab.text = "OBJECT ${(position + 1)}"
            when (position) {
                0 -> {
                    tab.text = resources.getString(R.string.generated_qr)
                }
                1 -> {
                    tab.text = resources.getString(R.string.checked_in)
                }
                2 -> {
                    tab.text = resources.getString(R.string.checked_out)
                }
            }
        }.attach()*/
    }
}
