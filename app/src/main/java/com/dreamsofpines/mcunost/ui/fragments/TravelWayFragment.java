package com.dreamsofpines.mcunost.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.dreamsofpines.mcunost.R;

/**
 * Created by ThePupsick on 28.02.2018.
 */

public class TravelWayFragment extends Fragment {

    private View view;
    private SwitchCompat fly,train;
    private Button accept,cancel;
    private OnClickListener listener;
    public interface OnClickListener{
        void onClick(boolean accept, int choose);
    }

    public void setOnClickListener(OnClickListener listener){
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.fragment_travel_way,container,false);
        Bundle bundle = getArguments();
        bindView();
        setListener();
        int choose = bundle != null ? bundle.getInt("travel"):0;
        if(choose == 1) fly.setChecked(true); else train.setChecked(true);
        TextView title = (TextView) getActivity().findViewById(R.id.title_tour);
        title.setText("Проезд");
        Button help = (Button) getActivity().findViewById(R.id.button_help);
        help.setVisibility(View.GONE);


        return view;
    }


    private void bindView(){
        fly = (SwitchCompat) view.findViewById(R.id.switch_fly);
        train = (SwitchCompat) view.findViewById(R.id.switch_train);
        accept = (Button) view.findViewById(R.id.travel_way_accept);
        cancel = (Button) view.findViewById(R.id.travel_way_cancel);
    }

    private void setListener(){
        fly.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b){
                    fly.setChecked(false);
                    train.setChecked(true);
                }else{
                    fly.setChecked(true);
                    train.setChecked(false);
                }
            }
        });

        train.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b){
                    train.setChecked(false);
                    fly.setChecked(true);
                }else{
                    train.setChecked(true);
                    fly.setChecked(false);
                }
            }
        });
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(true,fly.isChecked()?1:0);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(false,0);
            }
        });
    }

}
