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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.storage.models.Excursion;
import com.dreamsofpines.mcunost.data.storage.models.Order;

import java.util.List;

/**
 * Created by ThePupsick on 12.11.2017.
 */

public class ShortInfoOrderDialog extends DialogFragment{

    private TextView title, date, cost, student, lead, manager, phone,status,idOrd,
            exc,din,hotel,bus,travel;
    private Button close,chat,more,back;
    private View view;
    private Animation slideLF,slideLS, slideRF,slideRS;
    private LinearLayout linMore,linShort;
    private Order order;

    public static OnClickChatListener listener;

    public interface OnClickChatListener{
        void onClick(int idOrd);
    }


    public static ShortInfoOrderDialog newInstance(Order order, OnClickChatListener listener){
        ShortInfoOrderDialog sD = new ShortInfoOrderDialog();
        sD.init(order,listener);
        return sD;
    }

    public void init(Order order,OnClickChatListener listener){
        this.order = order;
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
                    Toast.makeText(getContext(),"Данный функционал будет добавлен позже!",Toast.LENGTH_LONG).show();
//                    listener.onClick(Integer.valueOf(order.getId()));
//                    dismiss();
                }
            }
        });

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hotel.setText(order.getHotel());
                travel.setText(order.isAddTrain()?"Поезд":"Самолет");
                din.setText(order.getCountBr()+"-Завтрак\n"+order.getCountLu()+"-Обед\n"+order.getCountDin()+"-Ужин");
                bus.setText(order.getCountBusMeet()+"-Первый и последний день\n"+order.getCount4Bus()+"-4 часовой автобус\n"+order.getCountAllDayBus()+"-Полный день");
                String excursion ="";
                List<Excursion> list = order.getExcursionList();
                for(int i = 0; i<list.size();++i){
                    excursion+=(i+1)+". "+list.get(i).getName()+"\n";
                }
                exc.setText(excursion);
                slideLF = AnimationUtils.loadAnimation(getContext(),R.anim.slide_left_1_obj);
                slideLS = AnimationUtils.loadAnimation(getContext(),R.anim.slide_left_2_obj);
                linShort.setAnimation(slideLF);
                linShort.setVisibility(View.GONE);
                linMore.setVisibility(View.VISIBLE);
                linMore.setAnimation(slideLS);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slideRF = AnimationUtils.loadAnimation(getContext(),R.anim.slide_right_1_obj);
                slideRS = AnimationUtils.loadAnimation(getContext(),R.anim.slide_right_2_obj);

                linMore.setAnimation(slideRS);
                linMore.setVisibility(View.GONE);

                linShort.setVisibility(View.VISIBLE);
                linShort.setAnimation(slideRF);
            }
        });
        return view;
    }

    private void bindView(){
        title    = (TextView) view.findViewById(R.id.title_txt_info_order);
        date     = (TextView) view.findViewById(R.id.date_info_order);
        cost     = (TextView) view.findViewById(R.id.cost_info_order);
        student  = (TextView) view.findViewById(R.id.quantity_sc_info_order);
        lead     = (TextView) view.findViewById(R.id.quantity_tc_info_order);
        manager  = (TextView) view.findViewById(R.id.manager_info_order);
        phone    = (TextView) view.findViewById(R.id.phone_info_order);
        status   = (TextView) view.findViewById(R.id.status_info_order);
        close    = (Button) view.findViewById(R.id.butt_close_info_order);
        chat     = (Button) view.findViewById(R.id.butt_chat_info_order);
        idOrd    = (TextView) view.findViewById(R.id.number_ord_info_order);
        linMore  = (LinearLayout) view.findViewById(R.id.lin_more);
        linShort = (LinearLayout) view.findViewById(R.id.lin_short);
        more     = (Button) view.findViewById(R.id.but_more_desc);
        back     = (Button) view.findViewById(R.id.back);
        bus      = (TextView) view.findViewById(R.id.bus_name);
        travel   = (TextView) view.findViewById(R.id.travel_name);
        hotel    = (TextView) view.findViewById(R.id.hotel_name);
        din      = (TextView) view.findViewById(R.id.din_name);
        exc      = (TextView) view.findViewById(R.id.exc_name);

    }

    private void setText(){
        date.setText(order.getDate());
        cost.setText(order.getCost());
        student.setText(order.getPupils());
        lead.setText(order.getTeachers());
        title.setText(order.getTour());
        manager.setText(order.getManager());
        phone.setText(order.getPhone());
        status.setText(order.getStatus());
        idOrd.setText(order.getId());
    }

}

