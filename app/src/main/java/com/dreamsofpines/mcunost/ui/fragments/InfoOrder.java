package com.dreamsofpines.mcunost.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.dreamsofpines.mcunost.R;

/**
 * Created by ThePupsick on 20.08.17.
 */

public class InfoOrder extends Fragment {


    public static OnClickCloseListener mListener;

    public interface OnClickCloseListener{
        void onClicked();
    }

    public void setOnClicCloseListener(OnClickCloseListener listener){
        mListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info_order,container,false);
        ImageView img = (ImageView) view.findViewById(R.id.img_back_info_order);
        img.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        Button butClose = (Button) view.findViewById(R.id.butt_close_info_order);
        butClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClicked();
            }
        });
        return view;
    }
}
