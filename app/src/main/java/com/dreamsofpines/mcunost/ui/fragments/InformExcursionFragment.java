package com.dreamsofpines.mcunost.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.database.MyDataBase;
import com.dreamsofpines.mcunost.ui.adapters.ExcurPagerAdapter;
import com.dreamsofpines.mcunost.ui.adapters.ExcurPagerAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by ThePupsick on 06.08.17.
 */

public class InformExcursionFragment extends Fragment {


    private MyDataBase mMyDataBase;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inform_excursion,container,false);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.excure_view_pager);
        CircleIndicator indicator = (CircleIndicator) view.findViewById(R.id.indicator);
        ImageView mImageView = (ImageView) view.findViewById(R.id.img_exc_inf);
        TextView title = (TextView) view.findViewById(R.id.title_exc_inf);
        indicator.configureIndicator(-1,-1,-1,
                R.animator.scale_with_alpha,0,R.drawable.circle_shape,R.drawable.circle_shape);
        ExcurPagerAdapter adapter = new ExcurPagerAdapter(getActivity());
        mMyDataBase = new MyDataBase(getActivity());
        Bundle bundle = getArguments();
        List<String> list = mMyDataBase.getListExcursion(bundle.getString("pack_exc"));
        String listExcursion = createStringListExcursion(list);
        if(listExcursion.equalsIgnoreCase("")) {
            listExcursion = "Список экскурсий пуст";
        }
        Picasso.with(getActivity()).load("file:///android_asset/"+bundle.getString("img")+".png").into(mImageView);
        title.setText(bundle.getString("pack_exc"));
        adapter.setDay(bundle.getString("day"));
        adapter.setCost(bundle.getString("cost"));
        adapter.setFullText(bundle.getString("description"));
        adapter.setListExc(listExcursion);
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);
        return view;
    }
    private String createStringListExcursion(List<String> list){
        String result="";
        for(int i = 0;i < list.size();++i ){
            result += (i+1)+". "+list.get(i)+"\n";
        }
        return result;
    }
}
