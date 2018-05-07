package com.dreamsofpines.mcunost.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.ui.customView.SimpleCounter;

import nl.dionsegijn.steppertouch.StepperTouch;

/**
 * Created by ThePupsick on 21.02.2018.
 */

public class QuantityGroupFragment extends Fragment {

    private View view;
    private Button accept, cancel;
    private SimpleCounter teacher, children;

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

        teacher.setMinValue(0);
        teacher.setMaxValue(5);
        teacher.setValue(bundle.getInt("countT"));

        children.setMinValue(0);
        children.setMaxValue(100);
        children.setValue(bundle.getInt("countC"));
        TextView title = (TextView) getActivity().findViewById(R.id.title_tour);
        title.setText("Группа");
        Button help = (Button) getActivity().findViewById(R.id.button_help);
        help.setVisibility(View.GONE);



        return view;
    }

    private void bindView(){
        accept = (Button) view.findViewById(R.id.quantity_accept);
        cancel = (Button) view.findViewById(R.id.quantity_cancel);
        teacher = (SimpleCounter) view.findViewById(R.id.stepperTouchTeacher);
        children = (SimpleCounter) view.findViewById(R.id.stepperTouchChildren);
    }

    private void setListeners(){

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnClick(true,teacher.getValue(),children.getValue());
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
