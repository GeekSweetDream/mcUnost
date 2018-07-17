package com.dreamsofpines.mcunost.ui.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.ui.customView.SimpleCounter;
import com.dreamsofpines.mcunost.ui.customView.ViewFragmentPattern;

/**
 * Created by ThePupsick on 23.02.2018.
 */

public class DinnerFragment extends Fragment {

    private View view;
    private View field;
    private SimpleCounter br,lu,din;
    private ViewFragmentPattern fragment;
    private float x,y;
    private int brCount, luCount, dinCount, maxDay;

    public static OnClickListener listener;
    public interface OnClickListener{
        void onClick(int countBr, int coutnLun, int countDin);
    }

    public void setOnClickListener(OnClickListener listener){
        this.listener = listener;
    }

    private SimpleCounter.onClickListener mOnClickListener =  (boolean plus)->{
        fragment.setTextInToolbar(getCountEatString());
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.fragment_quantity_dinner,container,false);
        field = (View) inflater.inflate(R.layout.view_dinner_field,container,false);
        bindView();
        getInformationFromBundle();
        settingFragment();
        onPressBackListener();

        setValueInCounters(br,brCount);
        setValueInCounters(lu,luCount);
        setValueInCounters(din,dinCount);

        return view;
    }

    private void setValueInCounters(SimpleCounter counter, int value){
        counter.setValue(value);
        counter.setMinValue(0);
        counter.setMaxValue(maxDay);
        counter.setOnClickListener(mOnClickListener);
    }

    private void getInformationFromBundle(){
        Bundle bundle = getArguments();
        x = bundle.getFloat("x");
        y = bundle.getFloat("y");
        brCount = bundle.getInt("br");
        luCount = bundle.getInt("lu");
        dinCount = bundle.getInt("din");
        maxDay = bundle.getInt("day");
    }

    private void onPressBackListener(){
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener((view, i,keyEvent)->{
            if( i == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                fragment.hide();
                listener.onClick(br.getValue(),lu.getValue(),din.getValue());
                return true;
            }
            return false;
        });
    }

    private void settingFragment(){
        fragment.setViewInLayout(field);
        fragment.setPositionX(x);
        fragment.setPositionY(y);
        fragment.setTitleInToolbar("Питание:");
        fragment.setTextInToolbar(getCountEatString());
        fragment.setIconInToolbar("icon_din");
        fragment.setOnBackgroundClickListener(()->{
            fragment.hide();
            listener.onClick(br.getValue(),lu.getValue(),din.getValue());
        });
        new Handler().postDelayed(()-> fragment.show(),200);
    }

    private String getCountEatString(){
        return brCount+"/"+luCount +"/"+dinCount;
    }

    private void bindView(){
        br      = (SimpleCounter) field.findViewById(R.id.stepperTouchBreakfast);
        lu      = (SimpleCounter) field.findViewById(R.id.stepperTouchLunch);
        din     = (SimpleCounter) field.findViewById(R.id.stepperTouchDinner);
        fragment = (ViewFragmentPattern) view.findViewById(R.id.fragment);
    }

}
