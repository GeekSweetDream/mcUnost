package com.dreamsofpines.mcunost.ui.customView.helpers;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.dreamsofpines.mcunost.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ThePupsick on 10.03.2018.
 */

public class BusHelper extends LinearLayout {

    private View rootView;
    private List<RelativeLayout> list;
    private int cur;

    public BusHelper(@NonNull Context context) {
        super(context);
        init(context);
    }

    public BusHelper(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BusHelper(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        rootView = inflate(context, R.layout.help_bus,this);
        list = new ArrayList<>();
        list.add((RelativeLayout) rootView.findViewById(R.id.rl_small_help_bus));
        list.add((RelativeLayout) rootView.findViewById(R.id.rl_bus_help_bus));
        list.add((RelativeLayout) rootView.findViewById(R.id.rl_bus2_help_bus));
        rootView.setVisibility(GONE);
    }

    private void setListeners(){
        if(cur < list.size()){
            RelativeLayout rl;
            if(cur > 0) {
                rl = list.get(cur-1);
                rl.setVisibility(GONE);
            }
            rl = list.get(cur);
            rl.setVisibility(VISIBLE);
            rl.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    cur++;
                    setListeners();
                }
            });
        }else{
            hide();
        }

    }


    public void show(){
        rootView.setVisibility(VISIBLE);
        cur = 0;
        setListeners();
    }

    public void hide(){
        rootView.setVisibility(GONE);
    }



}
