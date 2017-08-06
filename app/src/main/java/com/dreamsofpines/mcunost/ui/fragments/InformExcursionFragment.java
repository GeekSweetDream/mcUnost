package com.dreamsofpines.mcunost.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.ui.adapters.ExcurPagerAdapter;
import com.dreamsofpines.mcunost.ui.adapters.ExcurPagerAdapter;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by ThePupsick on 06.08.17.
 */

public class InformExcursionFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inform_excursion,container,false);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.excure_view_pager);
        CircleIndicator indicator = (CircleIndicator) view.findViewById(R.id.indicator);
        indicator.configureIndicator(-1,-1,-1,
                R.animator.scale_with_alpha,0,R.drawable.circle_shape,R.drawable.circle_shape);
        viewPager.setAdapter(new ExcurPagerAdapter(getActivity()));
        indicator.setViewPager(viewPager);
        return view;
    }
}
