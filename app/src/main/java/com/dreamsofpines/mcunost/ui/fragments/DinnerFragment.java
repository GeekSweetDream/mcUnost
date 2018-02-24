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

import nl.dionsegijn.steppertouch.StepperTouch;

/**
 * Created by ThePupsick on 23.02.2018.
 */

public class DinnerFragment extends Fragment {

    private View view;
    private StepperTouch br,lu,din;
    private Button cancel, accept;

    public static OnClickListener listener;
    public interface OnClickListener{
        void onClick(boolean accept,int countBr, int coutnLun, int countDin);
    }

    public void setOnClickListener(OnClickListener listener){
        this.listener = listener;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.fragment_quantity_dinner,container,false);
        bindView();
        setListeners();
        Bundle bundle = getArguments();
        br.stepper.setValue(bundle.getInt("br")-1);
        br.stepper.setMin(0);
        br.stepper.setMax(bundle.getInt("day"));
        lu.stepper.setValue(bundle.getInt("lu"));
        lu.stepper.setMin(0);
        lu.stepper.setMax(bundle.getInt("day"));
        din.stepper.setValue(bundle.getInt("din"));
        lu.stepper.setMin(0);
        lu.stepper.setMax(bundle.getInt("day"));
        return view;
    }

    private void bindView(){
        br = (StepperTouch) view.findViewById(R.id.stepperTouchBreakfast);
        lu = (StepperTouch) view.findViewById(R.id.stepperTouchLunch);
        din = (StepperTouch) view.findViewById(R.id.stepperTouchDinner);
        accept = (Button) view.findViewById(R.id.quantity_dinner_accept);
        cancel = (Button) view.findViewById(R.id.quantity_dinner_cancel);
    }

    private void setListeners(){
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(true, br.stepper.getValue(),lu.stepper.getValue(),din.stepper.getValue());
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(false,0,0,0);
            }
        });


    }

}
