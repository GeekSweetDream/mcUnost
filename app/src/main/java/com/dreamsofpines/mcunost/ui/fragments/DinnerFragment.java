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
import com.dreamsofpines.mcunost.data.storage.preference.GlobalPreferences;
import com.dreamsofpines.mcunost.ui.customView.SimpleCounter;
import com.dreamsofpines.mcunost.ui.customView.helpers.DinHelper;

import nl.dionsegijn.steppertouch.StepperTouch;

/**
 * Created by ThePupsick on 23.02.2018.
 */

public class DinnerFragment extends Fragment {

    private View view;
    private SimpleCounter br,lu,din;
    private Button cancel, accept,help;
    private TextView title;
    private DinHelper dinHelp;

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
        br.setValue(bundle.getInt("br"));
        br.setMinValue(0);
        br.setMaxValue(bundle.getInt("day"));
        lu.setValue(bundle.getInt("lu"));
        lu.setMinValue(0);
        lu.setMaxValue(bundle.getInt("day"));
        din.setValue(bundle.getInt("din"));
        din.setMinValue(0);
        din.setMaxValue(bundle.getInt("day"));

        help = (Button) getActivity().findViewById(R.id.button_help);
        help.setVisibility(View.VISIBLE);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dinHelp.show();
            }
        });
        if(GlobalPreferences.getPrefHelpDin(getContext())!=1){
            dinHelp.show();
            GlobalPreferences.setPrefHelpDin(getContext(),1);
        }
        title.setText("Обед");
        return view;
    }

    private void bindView(){
        br      = (SimpleCounter) view.findViewById(R.id.stepperTouchBreakfast);
        lu      = (SimpleCounter) view.findViewById(R.id.stepperTouchLunch);
        din     = (SimpleCounter) view.findViewById(R.id.stepperTouchDinner);
        accept  = (Button) view.findViewById(R.id.quantity_dinner_accept);
        cancel  = (Button) view.findViewById(R.id.quantity_dinner_cancel);
        title   = (TextView) getActivity().findViewById(R.id.title_tour);
        dinHelp = (DinHelper) view.findViewById(R.id.din_help);
    }

    private void setListeners(){
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(true, br.getValue(),lu.getValue(),din.getValue());
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
