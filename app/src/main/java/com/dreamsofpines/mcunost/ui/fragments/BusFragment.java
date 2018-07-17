package com.dreamsofpines.mcunost.ui.fragments;

import android.animation.LayoutTransition;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.storage.preference.GlobalPreferences;
import com.dreamsofpines.mcunost.ui.customView.SimpleCounter;
import com.dreamsofpines.mcunost.ui.customView.ViewFragmentPattern;
import com.dreamsofpines.mcunost.ui.customView.ViewTextWithCheckbox;
import com.dreamsofpines.mcunost.ui.customView.helpers.BusHelper;

import net.igenius.customcheckbox.CustomCheckBox;

import java.util.ArrayList;
import java.util.List;

import nl.dionsegijn.steppertouch.StepperTouch;

/**
 * Created by ThePupsick on 23.02.2018.
 */

public class BusFragment extends Fragment {


    private View view,field;
    private ViewFragmentPattern fragment;
    private ViewTextWithCheckbox mainBus;
    private List<ViewTextWithCheckbox> list;
    private RelativeLayout relDay;
    private int day;
    private float x,y;
    private boolean addMainBuss = true;
    private int addBusDays;
    private static OnClickListener listener;

    public interface OnClickListener{
        void onClick(boolean mainBus, int addBusDays);
    }

    public void setListener(OnClickListener listener){
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.fragment_quantity_dinner,container,false);
        field = (View) inflater.inflate(R.layout.fragment_bus,container,false);
        list = new ArrayList<>();
        bindView();
        getInformationFromBundle();
        onPressBackListener(view);
        settingFragment();
        mainBus.setCheckBox(addMainBuss);
        checkBusDays();
        if(day>2){
            relDay.setVisibility(View.VISIBLE);
            for(int i = 0; i<day-2;++i){
                list.get(i).setVisibility(View.VISIBLE);
            }
        }

        return view;
    }

    private void getInformationFromBundle(){
        Bundle bundle = getArguments();
        day = bundle.getInt("day");
        x = bundle.getFloat("x");
        y = bundle.getFloat("y");
        addMainBuss = bundle.getBoolean("addMainBus");
        addBusDays = bundle.getInt("addBusDays");
    }

    private void onPressBackListener(View view){
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener((view1, i,keyEvent)->{
            if( i == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                fragment.hide();
                listener.onClick(mainBus.isChecked(),saveBusDays());
                return true;
            }
            return false;
        });
    }


    private void settingFragment(){
        fragment.setTitleInToolbar("Автобус: ");
        fragment.setViewInLayout(field);
        fragment.setPositionX(x);
        fragment.setPositionY(y);
        fragment.setTextInToolbar("Выбран");
        fragment.setIconInToolbar("icon_bus");
        fragment.setOnBackgroundClickListener(()->{
            fragment.hide();
            listener.onClick(mainBus.isChecked(),saveBusDays());
        });
        new Handler().postDelayed(()->fragment.show(),200);
    }


    private void bindView(){
        fragment = (ViewFragmentPattern) view.findViewById(R.id.fragment);
        list.add((ViewTextWithCheckbox) field.findViewById(R.id.day2));
        list.add((ViewTextWithCheckbox) field.findViewById(R.id.day3));
        list.add((ViewTextWithCheckbox) field.findViewById(R.id.day4));
        list.add((ViewTextWithCheckbox) field.findViewById(R.id.day5));
        list.add((ViewTextWithCheckbox) field.findViewById(R.id.day6));
        mainBus = (ViewTextWithCheckbox) field.findViewById(R.id.main_bus);
        relDay = (RelativeLayout) field.findViewById(R.id.rel_bus);
    }

    private void checkBusDays(){
        if(addBusDays != 0) {
            for (int i = 0; i < 5; i++) {
                list.get(i).setCheckBox(((addBusDays >> i) & 1) == 1);
            }
        }
    }
    private int saveBusDays(){
        addBusDays = 0;
        for (int i = 4; i >= 0; i--) {
            addBusDays = addBusDays << 1;
            if(list.get(i).getVisibility() == View.VISIBLE &&
                    list.get(i).isChecked()){
                ++addBusDays;
            }
        }
        return addBusDays;
    }

    /*

        Проверочный метод добавленных автобусов

     */
    private void printLog(int p){
        for(int i = 0; i < 5; ++i){
            Log.i("LLL", ""+((p >> i) & 1));
        }

    }

}
