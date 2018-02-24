package com.dreamsofpines.mcunost.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dreamsofpines.mcunost.R;

import net.igenius.customcheckbox.CustomCheckBox;

import nl.dionsegijn.steppertouch.StepperTouch;

/**
 * Created by ThePupsick on 23.02.2018.
 */

public class BusFragment extends Fragment {


    private View view;
    private CustomCheckBox busBox;
    private StepperTouch countBus;
    private Button accept,cancel;
    private static OnClickListener listener;

    public interface OnClickListener{
        void onClick(boolean accept, boolean mainBus, int moreBus);
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
        busBox.setChecked(bundle.getBoolean("bus"));
        countBus.stepper.setMin(0);
        countBus.stepper.setValue(bundle.getInt("countbus"));
        countBus.stepper.setMax((bundle.getInt("day")-2)<0?0:bundle.getInt("day")-2);

        return view;
    }


    private void bindView(){
        busBox = (CustomCheckBox) view.findViewById(R.id.main_bus_box);
        countBus = (StepperTouch) view.findViewById(R.id.stepperTouchBus);
        accept = (Button) view.findViewById(R.id.bus_accept);
        cancel = (Button) view.findViewById(R.id.bus_cancel);
    }

    private void setListeners(){
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(false,false,0);
            }
        });
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(true,busBox.isChecked(),countBus.stepper.getValue());
            }
        });

    }




}
