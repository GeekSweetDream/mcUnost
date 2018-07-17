package com.dreamsofpines.mcunost.ui.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.ui.customView.SimpleCounter;
import com.dreamsofpines.mcunost.ui.customView.ViewFragmentPattern;

/**
 * Created by ThePupsick on 21.02.2018.
 */

public class QuantityGroupFragment extends Fragment {

    private View view,field;
    private SimpleCounter teacher, children;
    private ViewFragmentPattern fragment;

    public static OnClickListener listener;

    public interface OnClickListener{
        void OnClick(int countT, int countC);
    }


    public void setListener(OnClickListener listener){
        this.listener = listener;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.fragment_quantity_dinner,container,false);
        field = (View) inflater.inflate(R.layout.view_group_field,container,false);
        Bundle bundle = getArguments();
        bindView();
        setListeners();

        teacher.setMinValue(0);
        teacher.setMaxValue(5);
        teacher.setValue(bundle.getInt("countT"));
        teacher.setOnClickListener((boolean plus) ->{
            fragment.setTextInToolbar(getStringTitle());
        });


        children.setMinValue(0);
        children.setMaxValue(100);
        children.setValue(bundle.getInt("countC"));
        children.setOnClickListener((boolean plus) ->{
            fragment.setTextInToolbar(getStringTitle());
        });

        fragment.setViewInLayout(field);
        fragment.setTextInToolbar(getStringTitle());
        fragment.setPositionX(bundle.getFloat("x"));
        fragment.setPositionY(bundle.getFloat("y"));
        fragment.setTitleInToolbar("Группа: ");
        fragment.setIconInToolbar("icon_group");
        fragment.setOnBackgroundClickListener(()->{
            fragment.hide();
            listener.OnClick(teacher.getValue(),children.getValue());
        });
        new Handler().postDelayed(()->fragment.show(),200);

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener((view, i,keyEvent)->{
            if( i == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                fragment.hide();
                listener.OnClick(teacher.getValue(),children.getValue());
                return true;
            }
            return false;
        });



        return view;
    }

    private String getStringTitle(){
        return teacher.getValue()+"/"+children.getValue();
    }


    private void bindView(){
        fragment = (ViewFragmentPattern) view.findViewById(R.id.fragment);
        teacher = (SimpleCounter) field.findViewById(R.id.stepperTouchTeacher);
        children = (SimpleCounter) field.findViewById(R.id.stepperTouchChildren);
    }

    private void setListeners(){

    }




}
