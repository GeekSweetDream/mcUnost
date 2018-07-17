package com.dreamsofpines.mcunost.ui.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.storage.models.Hotel;
import com.dreamsofpines.mcunost.ui.customView.ViewFragmentPattern;
import com.dreamsofpines.mcunost.ui.customView.ViewTextWithCheckbox;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThePupsick on 21.02.2018.
 */

public class DayFragment extends Fragment {

    private View view;
    private View field;
    private ViewFragmentPattern fragment;
    private float x,y;
    private int curPos;
    private List<ViewTextWithCheckbox> list;

    public static OnClickListener listener;

    public interface OnClickListener{
        void onClick(int day);
    }

    public void setListener(OnClickListener listener){
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view  = (View) inflater.inflate(R.layout.fragment_quantity_dinner,container,false);
        field = (View) inflater.inflate(R.layout.fragment_day,container,false);

        list = new ArrayList<>();

        bindView();
        getInfrormationFromBundle();
        setListener();
        settingFragment();
        onPressBackListener(view);
        list.get(curPos).setCheckBox(true);
        return view;
    }

    private void getInfrormationFromBundle(){
        Bundle bundle = getArguments();
        curPos = bundle.getInt("day")-1;
        x = bundle.getFloat("x");
        y = bundle.getFloat("y");
    }

    private void settingFragment(){
        fragment.setTitleInToolbar("Кол-во дней: ");
        fragment.setViewInLayout(field);
        fragment.setPositionX(x);
        fragment.setPositionY(y);
        fragment.setTextInToolbar(curPos==0?""+(curPos+1):(curPos+1)+"/"+curPos);
        fragment.setIconInToolbar("icon_calendar");
        fragment.setOnBackgroundClickListener(()->{
            fragment.hide();
            listener.onClick(curPos+1);
        });
        new Handler().postDelayed(()->fragment.show(),200);
    }

    private void onPressBackListener(View view){
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener((view1, i,keyEvent)->{
            if( i == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                fragment.hide();
                listener.onClick(curPos+1);
                return true;
            }
            return false;
        });
    }

    void bindView(){
        list.add((ViewTextWithCheckbox) field.findViewById(R.id.day1));
        list.add((ViewTextWithCheckbox) field.findViewById(R.id.day2));
        list.add((ViewTextWithCheckbox) field.findViewById(R.id.day3));
        list.add((ViewTextWithCheckbox) field.findViewById(R.id.day4));
        list.add((ViewTextWithCheckbox) field.findViewById(R.id.day5));
        list.add((ViewTextWithCheckbox) field.findViewById(R.id.day6));
        list.add((ViewTextWithCheckbox) field.findViewById(R.id.day7));
        fragment = (ViewFragmentPattern) view.findViewById(R.id.fragment);
    }

    private ViewTextWithCheckbox.OnClickCheckBoxListener mCheckBoxListener = (View view,boolean on)->{
        list.get(curPos).setCheckBox(false);
        curPos = ((ViewTextWithCheckbox)view).getTitle().charAt(0)-'0'-1;
        list.get(curPos).setCheckBox(true);
        fragment.setTextInToolbar(curPos==0?""+(curPos+1):(curPos+1)+"/"+curPos);
    };

    void setListener(){
        for (ViewTextWithCheckbox item: list) {
            item.setOnClickCheckBoxListener(mCheckBoxListener);
        }
    }
}
