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
 * Created by ThePupsick on 21.02.2018.
 */

public class QuantityGroupFragment extends Fragment {

    private View view;
    private Button accept, cancel;
    private StepperTouch teacher, children;

    public static OnClickListener listener;

    public interface OnClickListener{
        void OnClick(boolean accept,int countT, int countC);
    }



    public void setListener(OnClickListener listener){
        this.listener = listener;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.fragment_quantity_group,container,false);
        Bundle bundle = getArguments();
        bindView();
        setListeners();

        teacher.stepper.setMin(0);
        teacher.stepper.setMax(5);
        teacher.stepper.setValue(bundle.getInt("countT"));

        children.stepper.setMin(0);
        children.stepper.setMax(100);
        children.stepper.setValue(bundle.getInt("countC"));

        return view;
    }

    private void bindView(){
        accept = (Button) view.findViewById(R.id.quantity_accept);
        cancel = (Button) view.findViewById(R.id.quantity_cancel);
        teacher = (StepperTouch) view.findViewById(R.id.stepperTouchTeacher);
        children = (StepperTouch) view.findViewById(R.id.stepperTouchChildren);
    }

    private void setListeners(){

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnClick(true,teacher.stepper.getValue(),children.stepper.getValue());
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnClick(false,0,0);
            }
        });
    }




}
