package com.dreamsofpines.mcunost.ui.dialog;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamsofpines.mcunost.R;

/**
 * Created by ThePupsick on 12.11.2017.
 */

public class ShortInfoOrderDialog extends DialogFragment{

    private TextView title, date, cost, student, lead, manager, phone,status,idOrd;
    private Bundle mBundle;
    private Button close,chat;
    private View view;

    public static OnClickChatListener listener;

    public interface OnClickChatListener{
        void onClick(int idOrd);
    }


    public static ShortInfoOrderDialog newInstance(Bundle bundle,OnClickChatListener listener){
        ShortInfoOrderDialog sD = new ShortInfoOrderDialog();
        sD.init(bundle,listener);
        return sD;
    }

    public void init(Bundle bundle,OnClickChatListener listener){
        this.mBundle = bundle;
        this.listener = listener;
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
        view = inflater.inflate(R.layout.fragment_info_order,container,false);
        bindView();
        setText();
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null){
                    listener.onClick(Integer.valueOf(mBundle.getString("idord")));
                    dismiss();
                }
            }
        });
        return view;
    }

    private void bindView(){
        title = (TextView) view.findViewById(R.id.title_txt_info_order);
        date = (TextView) view.findViewById(R.id.date_info_order);
        cost = (TextView) view.findViewById(R.id.cost_info_order);
        student = (TextView) view.findViewById(R.id.quantity_sc_info_order);
        lead = (TextView) view.findViewById(R.id.quantity_tc_info_order);
        manager = (TextView) view.findViewById(R.id.manager_info_order);
        phone = (TextView) view.findViewById(R.id.phone_info_order);
        status = (TextView) view.findViewById(R.id.status_info_order);
        close = (Button) view.findViewById(R.id.butt_close_info_order);
        chat = (Button) view.findViewById(R.id.butt_chat_info_order);
        idOrd = (TextView) view.findViewById(R.id.number_ord_info_order);
    }

    private void setText(){
        date.setText(mBundle.getString("date"));
        cost.setText(mBundle.getString("cost"));
        student.setText(mBundle.getString("school"));
        lead.setText(mBundle.getString("teacher"));
        title.setText(mBundle.getString("tour"));
        manager.setText(mBundle.getString("manager"));
        phone.setText(mBundle.getString("phone"));
        status.setText(mBundle.getString("status"));
        idOrd.setText(mBundle.getString("idord"));
    }

}

