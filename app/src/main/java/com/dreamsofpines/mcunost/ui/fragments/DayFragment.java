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
import android.widget.TextView;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.ui.dialog.ShortInfoOrderDialog;

import java.util.ArrayList;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static com.yandex.metrica.impl.q.a.b;
import static com.yandex.metrica.impl.q.a.s;

/**
 * Created by ThePupsick on 21.02.2018.
 */

public class DayFragment extends Fragment {

    private View view;
    private List<RelativeLayout> btn;
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
        Bundle bundle = getArguments();
        int d = bundle.getInt("day");
        btn = new ArrayList<>();
        bindView();
        setListener();
        for(int i = 0; i < btn.size();++i){
            btn.get(i).setBackgroundResource(R.mipmap.but_bl);
        }
        btn.get(d-1).setBackgroundResource(R.mipmap.but_or);
        TextView title = (TextView) getActivity().findViewById(R.id.title_tour);
        title.setText("Дней");
        Button help = (Button) getActivity().findViewById(R.id.button_help);
        help.setVisibility(View.GONE);


        return view;
    }


    void bindView(){
        btn.add((RelativeLayout) view.findViewById(R.id.rlbtn1));
        btn.add((RelativeLayout) view.findViewById(R.id.rlbtn2));
        btn.add((RelativeLayout) view.findViewById(R.id.rlbtn3));
        btn.add((RelativeLayout) view.findViewById(R.id.rlbtn4));
        btn.add((RelativeLayout) view.findViewById(R.id.rlbtn5));
        btn.add((RelativeLayout) view.findViewById(R.id.rlbtn6));
        btn.add((RelativeLayout) view.findViewById(R.id.rlbtn7));
        cancel = (Button) view.findViewById(R.id.rlbtn8);
    }

    void setListener(){
        btn.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(true,getString(R.string.day1));
            }
        });
        btn.get(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(true,getString(R.string.day2));
            }
        });
        btn.get(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(true,getString(R.string.day3));
            }
        });
        btn.get(3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(true,getString(R.string.day4));
            }
        });
        btn.get(4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(true,getString(R.string.day5));
            }
        });
        btn.get(5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(true,getString(R.string.day6));
            }
        });
        btn.get(6).setOnClickListener(new View.OnClickListener() {
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
