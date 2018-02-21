package com.dreamsofpines.mcunost.ui.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamsofpines.mcunost.R;

import org.w3c.dom.Text;

import static android.R.attr.order;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by ThePupsick on 20.08.17.
 */

public class InfoOrder extends Fragment{

    private Bundle mBundle;
    private TextView date, cost, schools, teachers, manager, status, phone, title;
    private ImageView img;
    private Button butClose, buttChat;
    private View view;

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
        view = inflater.inflate(R.layout.fragment_info_order,container,false);
        Activity activity = getActivity();
        bindView();
        setListener();
        mBundle = getArguments();
        date.setText(mBundle.getString("date"));
        cost.setText(mBundle.getString("cost"));
        schools.setText(mBundle.getString("school"));
        teachers.setText(mBundle.getString("teacher"));
        manager.setText(mBundle.getString("manager"));
        String st = mBundle.getString("status");
        if(st.equalsIgnoreCase("заказан")){
            status.setTextColor(activity.getResources().getColor(R.color.md_blue_400));
        }else if(st.equalsIgnoreCase("Отменен")){
            status.setTextColor(activity.getResources().getColor(R.color.md_red_600));
        }else if(st.equalsIgnoreCase("выполнен")){
            status.setTextColor(activity.getResources().getColor(R.color.md_green_400));
        }
        status.setText(st);
        phone.setText(mBundle.getString("phone"));
        title.setText(mBundle.getString("tour"));
        return view;
    }

    private void bindView(){
        date = (TextView) view.findViewById(R.id.date_info_order);
        cost = (TextView) view.findViewById(R.id.cost_info_order);
        schools = (TextView) view.findViewById(R.id.quantity_sc_info_order);
        teachers = (TextView) view.findViewById(R.id.quantity_tc_info_order);
        manager = (TextView) view.findViewById(R.id.manager_info_order);
        status = (TextView) view.findViewById(R.id.status_info_order);
        phone = (TextView) view.findViewById(R.id.phone_info_order);
        title = (TextView) view.findViewById(R.id.title_txt_info_order);
        butClose = (Button) view.findViewById(R.id.butt_close_info_order);
        buttChat = (Button) view.findViewById(R.id.butt_chat_info_order);
    }

    private void setListener(){
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i == KeyEvent.KEYCODE_BACK){
                    Log.i("Myapp","Во втором фрагменте");
                    mListener.onClicked();
                    return true;
                }
                return false;
            }
        });

        img.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        butClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClicked();
            }
        });


        buttChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Данный фукнционал еще не добавлен! :)",Toast.LENGTH_LONG)
                        .show();
            }
        });




    }


}

