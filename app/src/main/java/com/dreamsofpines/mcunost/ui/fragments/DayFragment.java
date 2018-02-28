package com.dreamsofpines.mcunost.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.ui.dialog.ShortInfoOrderDialog;

import static com.yandex.metrica.impl.q.a.s;

/**
 * Created by ThePupsick on 21.02.2018.
 */

public class DayFragment extends Fragment {

    private View view;
    private RelativeLayout btn1,btn2,btn3,btn4,btn5,btn6,btn7;
    private Button cancel;

    public static OnClickListener listener;

    public interface OnClickListener{
        void onClick(boolean accept,String str);
    }

    public void setListener(OnClickListener listener){
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.fragment_day,container,false);
        bindView();
        setListener();
        return view;
    }


    void bindView(){
        btn1 = (RelativeLayout) view.findViewById(R.id.rlbtn1);
        btn2 = (RelativeLayout) view.findViewById(R.id.rlbtn2);
        btn3 = (RelativeLayout) view.findViewById(R.id.rlbtn3);
        btn4 = (RelativeLayout) view.findViewById(R.id.rlbtn4);
        btn5 = (RelativeLayout) view.findViewById(R.id.rlbtn5);
        btn6 = (RelativeLayout) view.findViewById(R.id.rlbtn6);
        btn7 = (RelativeLayout) view.findViewById(R.id.rlbtn7);
        cancel = (Button) view.findViewById(R.id.rlbtn8);
    }

    void setListener(){
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(true,getString(R.string.day1));
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(true,getString(R.string.day2));
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(true,getString(R.string.day3));
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(true,getString(R.string.day4));
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(true,getString(R.string.day5));
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(true,getString(R.string.day6));
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(true,getString(R.string.day7));
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(false,null);
            }
        });
    }
}
