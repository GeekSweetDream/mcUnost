package com.dreamsofpines.mcunost.ui.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.ui.customView.ViewFragmentPattern;

/**
 * Created by ThePupsick on 28.02.2018.
 */

public class TravelWayFragment extends Fragment {

    private View view;
    private View field;
    private ViewFragmentPattern fragment;
    private SwitchCompat fly,train;
    private OnClickListener listener;
    public interface OnClickListener{
        void onClick(int choose);
    }

    public void setOnClickListener(OnClickListener listener){
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.fragment_quantity_dinner,container,false);
        field = (View) inflater.inflate(R.layout.view_travel_way_field,container,false);
        Bundle bundle = getArguments();
        bindView();
        setListener();

        int choose = bundle != null ? bundle.getInt("travel"):0;
        if(choose == 1) fly.setChecked(true); else train.setChecked(true);
        fragment.setViewInLayout(field);
        fragment.setTextInToolbar(getTravelTransport());
        fragment.setPositionX(bundle.getFloat("x"));
        fragment.setPositionY(bundle.getFloat("y"));
        fragment.setTitleInToolbar("Проезд: ");
        fragment.setIconInToolbar("icon_fly");
        new Handler().postDelayed(()->fragment.show(),200);
        fragment.setOnBackgroundClickListener(()->{
            fragment.hide();
            listener.onClick(fly.isChecked()?1:0);
        });

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener((view, i,keyEvent)->{
            if( i == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                fragment.hide();
                listener.onClick(fly.isChecked()?1:0);
                return true;
            }
            return false;
        });
        return view;
    }


    private void bindView(){
        fly = (SwitchCompat) field.findViewById(R.id.switch_fly);
        train = (SwitchCompat) field.findViewById(R.id.switch_train);
        fragment = (ViewFragmentPattern) view.findViewById(R.id.fragment);
    }

    private String getTravelTransport(){
        return fly.isChecked()?"Самолет":"Поезд";
    }

    private void setListener(){
        fly.setOnCheckedChangeListener((compoundButton, b)-> {
                fly.setChecked(b);
                train.setChecked(!b);
                fragment.setTextInToolbar(getTravelTransport());
        });

        train.setOnCheckedChangeListener((compoundButton, b)-> {
                train.setChecked(b);
                fly.setChecked(!b);
                fragment.setTextInToolbar(getTravelTransport());
        });
    }

}
