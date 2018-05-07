package com.dreamsofpines.mcunost.ui.fragments;

import android.animation.LayoutTransition;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.storage.preference.GlobalPreferences;
import com.dreamsofpines.mcunost.ui.customView.SimpleCounter;
import com.dreamsofpines.mcunost.ui.customView.helpers.BusHelper;

import net.igenius.customcheckbox.CustomCheckBox;

import nl.dionsegijn.steppertouch.StepperTouch;

/**
 * Created by ThePupsick on 23.02.2018.
 */

public class BusFragment extends Fragment {


    private View view;
    private SwitchCompat busBox, addbus1,addbus2;
    private SimpleCounter countBus,countBus2;
    private LinearLayout lin,lin2;
    private Button accept,cancel;
    private int day;
    private static OnClickListener listener;
    private boolean ch1,ch2;
    private int leftBus;
    private BusHelper busHelper;

    public interface OnClickListener{
        void onClick(boolean accept, boolean mainBus, int moreBus, int more4bus);
    }

    public void setListener(OnClickListener listener){
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.fragment_bus,container,false);

        bindView();
        setListeners();
        Bundle bundle = getArguments();
        leftBus = bundle.getInt("day");
        day = bundle.getInt("day");

        ch1 = ch2 = false;
        countBus2.setValue(0);
        countBus.setValue(0);

        if(bundle.getBoolean("bus")) busBox.setChecked(true);

        if(bundle.getInt("count4Bus") != 0) {
            setAlphaLinear(lin, true);
            ch2 = true;
            addbus2.setChecked(true);
            addbus2.setClickable(true);
            leftBus -= bundle.getInt("count4Bus");
            countBus2.setVisibility(View.VISIBLE);
            countBus2.setValue(bundle.getInt("count4Bus"));
            countBus2.setMinValue(1);

        }
        if(bundle.getInt("countbus") != 0) {
            setAlphaLinear(lin2,true);
            ch1 = true;
            addbus1.setChecked(true);
            addbus1.setClickable(true);
            countBus.setVisibility(View.VISIBLE);
            leftBus -= bundle.getInt("countbus");
            countBus.setValue(bundle.getInt("countbus"));
            countBus.setMinValue(1);
        }
        if( leftBus <= 0 && bundle.getInt("countbus") == 0 && bundle.getInt("count4Bus")== 0 ){
            setAlphaLinear(lin,false);
            addbus1.setClickable(false);
            setAlphaLinear(lin2,false);
            addbus2.setClickable(false);
        }

        TextView title = (TextView) getActivity().findViewById(R.id.title_tour);
        title.setText("Транспорт");
        Button help = (Button) getActivity().findViewById(R.id.button_help);
        help.setVisibility(View.VISIBLE);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                busHelper.show();
            }
        });

        if(GlobalPreferences.getPrefHelpBus(getContext())!=1){
            busHelper.show();
            GlobalPreferences.setPrefHelpBus(getContext(),1);
        }

        return view;
    }

    private void setAlphaLinear(LinearLayout lin,boolean mayTouch){
        if(mayTouch){
            lin.setAlpha((float) 1);
        }else{
            lin.setAlpha((float) 0.5);
        }
    }
    

    private void bindView(){
        busBox    = (SwitchCompat) view.findViewById(R.id.main_bus_box);
        countBus  = (SimpleCounter) view.findViewById(R.id.stepperTouchBus);
        countBus2 = (SimpleCounter) view.findViewById(R.id.stepperTouchBus2);
        addbus1   = (SwitchCompat) view.findViewById(R.id.add_bus_box);
        addbus2   = (SwitchCompat) view.findViewById(R.id.add_bus_box2);
        accept    = (Button) view.findViewById(R.id.bus_accept);
        cancel    = (Button) view.findViewById(R.id.bus_cancel);
        lin       = (LinearLayout) view.findViewById(R.id.lin_add_bus);
        lin2      = (LinearLayout) view.findViewById(R.id.lin_add_bus2);
        busHelper = (BusHelper) view.findViewById(R.id.bus_helper);
    }

    private void setListeners(){
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(false,false,0,0);
            }
        });
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(true,busBox.isChecked(),countBus.getValue(),countBus2.getValue());
            }
        });
        busBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    if(leftBus < 2 && day != 1){
                        setLeftBus(leftBus-2);
                        busBox.setChecked(false);
                    }else{
                        setLeftBus(leftBus-2);
                    }
                }else{
                    setLeftBus(leftBus+2);
                }
                notificateCounter();
            }
        });
        addbus1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!ch1) {
                    if (b && ((leftBus - 1) >= 0)) {
                        countBus.setVisibility(View.VISIBLE);
                        countBus.setMinValue(1);
                        countBus.setValue(1);
                        countBus.setMaxValue(leftBus);
                        setLeftBus(--leftBus);
                        if (leftBus == 0 && countBus2.getVisibility() == View.GONE) {
                            lin2.setAlpha((float) 0.5);
                            addbus2.setClickable(false);
                        }
                        notificateCounter();
                    } else {
                        if (!b) {
                            setLeftBus(leftBus + countBus.getValue());
                            countBus.setValue(0);
                            notificateCounter();
                        }
                        if (leftBus > 0 && countBus2.getVisibility() == View.GONE) {
                            lin2.setAlpha((float) 1);
                            addbus2.setClickable(true);
                        }

                        if (!b) countBus.setVisibility(View.GONE);

                    }
                }else{
                    ch1 = false;
                }
            }
        });
        addbus2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!ch2) {
                    if (b && ((leftBus - 1) >= 0)) {
                        countBus2.setMinValue(1);
                        countBus2.setValue(1);
                        countBus2.setMaxValue(leftBus);
                        countBus2.setVisibility(View.VISIBLE);
                        setLeftBus(--leftBus);

                        if (leftBus == 0 && countBus.getVisibility() == View.GONE) {
                            lin.setAlpha((float) 0.5);
                            addbus1.setClickable(false);
                        }
                        notificateCounter();
                    } else {
                        if (!b) {
                            countBus2.setValue(0);
                            setLeftBus(leftBus + countBus2.getValue());
                            notificateCounter();
                        }
                        if (leftBus > 0 && countBus.getVisibility() == View.GONE) {
                            lin.setAlpha((float) 1);
                            addbus1.setClickable(true);
                        }
                        if (!b) countBus2.setVisibility(View.GONE);

                    }
                }else{
                    ch2 = false;
                }
            }
        });
        countBus.setOnClickListener(new SimpleCounter.onClickListener() {
            @Override
            public void onButtonClick(boolean plus) {
                if(plus){
                    setLeftBus(--leftBus);
                    if(leftBus == 0 && countBus2.getVisibility()==View.GONE) {
                        lin2.setAlpha((float) 0.5);
                        addbus2.setClickable(false);
                    }
                }else{
                    setLeftBus(++leftBus);
                    if(leftBus > 0 && countBus2.getVisibility()==View.GONE) {
                        lin2.setAlpha((float) 1);
                        addbus2.setClickable(true);
                    }
                }
                countBus2.setMaxValue(leftBus);
            }
        });
        countBus2.setOnClickListener(new SimpleCounter.onClickListener() {
            @Override
            public void onButtonClick(boolean plus) {
                if(plus){
                    setLeftBus(--leftBus);
                    if(leftBus == 0 && countBus.getVisibility()==View.GONE) {
                        lin2.setAlpha((float) 0.5);
                        addbus1.setClickable(false);
                    }
                }else{
                    setLeftBus(++leftBus);
                    if(leftBus > 0 && countBus.getVisibility()==View.GONE) {
                        lin2.setAlpha((float) 1);
                        addbus1.setClickable(true);
                    }
                }
                countBus.setMaxValue(leftBus);
            }
        });

    }
    private void notificateCounter(){
        if(leftBus>0){
            if(!countBus.isClickable()){
                countBus.setClickable(true);
            }
            if(!countBus2.isClickable()){
                countBus2.setClickable(true);
            }
        }
        if(countBus.getVisibility() == View.VISIBLE){
            if(countBus.getValue()>leftBus) countBus.setValue(leftBus);
            countBus.setMaxValue(countBus.getValue()+leftBus);
        }
        if(countBus2.getVisibility() == View.VISIBLE){
            if(countBus2.getValue()>leftBus) countBus2.setValue(leftBus);
            countBus2.setMaxValue(countBus2.getValue()+leftBus);
        }
    }

    public void setLeftBus(int leftBus) {
        this.leftBus = leftBus;
    }
}
