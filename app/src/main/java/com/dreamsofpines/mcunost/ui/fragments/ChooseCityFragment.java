package com.dreamsofpines.mcunost.ui.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.storage.models.Hotel;
import com.dreamsofpines.mcunost.ui.customView.ViewFragmentPattern;

public class ChooseCityFragment extends Fragment {


    private View view,field;
    private ViewFragmentPattern fragment;
    private TextView spb,msc,anoth;
    private OnClickCityListener listener;
    private String curCity;
    private float x,y;
    private View.OnClickListener textViewClickListener = (btnTxtCity)->{
        String str = ((TextView)btnTxtCity).getText().toString();
        fragment.setTextInToolbar(str);
        fragment.hideDown();
        listener.onClick(true,str);
    };

    public interface OnClickCityListener{
        void onClick(boolean change,String city);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_quantity_dinner,container,false);
        field = inflater.inflate(R.layout.fragment_choose_city,container,false);
        bindView();
        getInfromationFromBundle();
        setListener();
        settingFragment();
        onPressBackListener();
        return view;
    }


    private void getInfromationFromBundle(){
        Bundle bundle = getArguments();
        x = bundle.getFloat("x");
        y = bundle.getFloat("y");
        curCity = bundle.getString("city");
    }

    private void onPressBackListener(){
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener((view1, i,keyEvent)->{
            if( i == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                fragment.hideDown();
                listener.onClick(false,curCity);
                return true;
            }
            return false;
        });
    }


    private void settingFragment(){
        fragment.setTitleInToolbar("Откуда");
        fragment.setViewInLayout(field);
        fragment.setVisibleIcon(false);
        fragment.setPositionX(x);
        fragment.setPositionY(y);
        fragment.setTextInToolbar(curCity);
        fragment.setOnBackgroundClickListener(()->{
            fragment.hideDown();
                listener.onClick(false,curCity);
        });
        new Handler().postDelayed(()->fragment.showDown(),200);
    }


    private void bindView(){
        fragment = (ViewFragmentPattern) view.findViewById(R.id.fragment);
        spb = (TextView) field.findViewById(R.id.city_spb);
        msc = (TextView) field.findViewById(R.id.city_msc);
        anoth = (TextView) field.findViewById(R.id.city_anoth);
    }

    private void setListener(){
        spb.setOnClickListener(textViewClickListener);
        msc.setOnClickListener(textViewClickListener);
        anoth.setOnClickListener(textViewClickListener);
    }

    public void setOnClickCityListener(OnClickCityListener listener){
        this.listener = listener;
    }

}
