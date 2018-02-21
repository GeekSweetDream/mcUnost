package com.dreamsofpines.mcunost.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dreamsofpines.mcunost.R;

/**
 * Created by ThePupsick on 22.08.17.
 */

public class ChooseCityDialogFragment extends DialogFragment{

    private String findCity="Другой регион";

    private TextView city,city1,city2,city3;;
    private Button butOk;
    private RelativeLayout  rl1, back;
    private LinearLayout rl;
    private RelativeLayout mess;
    private boolean isFinedSuccses;
    private ImageView imgCity, imgCity2, imgCity3;

    public static OnCityChangedListener mListener;

    public interface OnCityChangedListener{
        void onChange(String city);
    }

    public static ChooseCityDialogFragment newInstance(OnCityChangedListener callback,String findCity,boolean findSuccses){
        ChooseCityDialogFragment dF = new ChooseCityDialogFragment();
        dF.initialize(callback,findCity,findSuccses);
        return dF;
    }

    private void initialize(OnCityChangedListener callBack,String findCity,boolean findSuccses){
        this.mListener = callBack;
        this.findCity = findCity;
        this.isFinedSuccses = findSuccses;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_choose_city,container,false);
        bindDialogFragment(view);
        settingListener();
        city.setText(findCity);
        if(!isFinedSuccses){
            Handler handler = new Handler();
            mess.setVisibility(View.VISIBLE);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mess.setVisibility(View.INVISIBLE);
                }
            },8000);
        }
        return view;
    }

    private void bindDialogFragment(View view){
        city = (TextView) view.findViewById(R.id.mycity);
        city1 = (TextView) view.findViewById(R.id.city1);
        city2 = (TextView) view.findViewById(R.id.city2);
        city3 = (TextView) view.findViewById(R.id.city3);
        imgCity = (ImageView) view.findViewById(R.id.city1_img);
        imgCity2 = (ImageView) view.findViewById(R.id.city2_img);
        imgCity3 = (ImageView) view.findViewById(R.id.city3_img);
        butOk = (Button) view.findViewById(R.id.butt_ok_city);
        rl = (LinearLayout) view.findViewById(R.id.another_city);
        rl1 = (RelativeLayout) view.findViewById(R.id.rl_city);
        mess = (RelativeLayout) view.findViewById(R.id.mess_city);
        back = (RelativeLayout) view.findViewById(R.id.choose_city_background);
    }
    private void settingListener(){
        imgCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city.setText(city1.getText().toString());
                rl.setVisibility(View.INVISIBLE);
                butOk.setVisibility(View.VISIBLE);
            }
        });

        imgCity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city.setText(city2.getText().toString());
                rl.setVisibility(View.INVISIBLE);
                butOk.setVisibility(View.VISIBLE);
            }
        });

        imgCity3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city.setText(city3.getText().toString());
                rl.setVisibility(View.INVISIBLE);
                butOk.setVisibility(View.VISIBLE);
            }
        });

        rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl.setVisibility(rl.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
                butOk.setVisibility(butOk.getVisibility() == View.INVISIBLE ? View.VISIBLE : View.INVISIBLE);
            }
        });

        butOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onChange(city.getText().toString());
                dismiss();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl.setVisibility(View.INVISIBLE);
                butOk.setVisibility(View.VISIBLE);
            }
        });

    }

}
