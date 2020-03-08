package com.example.barakah.ui.fragment;

import android.animation.ObjectAnimator;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.barakah.R;
import com.example.barakah.databinding.FragmentHerbsDetailBinding;
import com.example.barakah.models.HerbsModel;
import com.example.barakah.utils.BarakahConstants;

/**
 * A simple {@link Fragment} subclass.
 */
public class HerbsDetailFragment extends Fragment {

    private FragmentHerbsDetailBinding binding;
    private HerbsModel herbsModel;

    public HerbsDetailFragment() {
        // Required empty public constructor
    }

    public static String TAG = "HerbsDetailFragment";

    public static HerbsDetailFragment newInstance(HerbsModel herbModel) {
        HerbsDetailFragment fragment = new HerbsDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(BarakahConstants.HERBS_MODEL, herbModel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            herbsModel = (HerbsModel) getArguments().getSerializable(BarakahConstants.HERBS_MODEL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_herbs_detail, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (herbsModel != null) {
            setHerbsData(herbsModel);
            binding.btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            binding.btnAddToFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

    }

    public void setHerbsData(HerbsModel herbsData) {
        binding.cardBenefit.setCardDescription(herbsData.getBenefit());
        binding.cardConflict.setCardDescription(herbsData.getInteraction());
        binding.cardEffects.setCardDescription(herbsData.getSide_effect());
        binding.tvTitle.setText(herbsModel.getName());
        binding.herbInfo.setText(herbsModel.getDescription());
        Glide.with(getActivity()).load(herbsData.getImage()).into(binding.herbPic);


    }

}
