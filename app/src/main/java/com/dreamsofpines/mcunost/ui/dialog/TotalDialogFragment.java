package com.dreamsofpines.mcunost.ui.dialog;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
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
import com.dreamsofpines.mcunost.data.network.api.Constans;
import com.dreamsofpines.mcunost.data.network.api.RequestSender;
import com.dreamsofpines.mcunost.data.storage.models.Excursion;
import com.dreamsofpines.mcunost.data.storage.models.Order;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by ThePupsick on 31.08.17.
 */

public class TotalDialogFragment extends DialogFragment {
    private String totalCost, date, pupil,teacher;
    private Order mOrder;
    private TextView data, pupilView, teacherView, costView,exc,din,hotel,bus,travel;
    private LinearLayout linSmal, linMore;
    private Button cancel,ok, more,back;
    private Animation slideLF,slideLS, slideRF,slideRS;
    private AVLoadingIndicatorView ind;

    public OnClickButton mListener;

    public interface OnClickButton{
        void OnClick(boolean accepted, Order order);
    }

    public static TotalDialogFragment newInstance(OnClickButton callback, Order order){
        TotalDialogFragment tD = new TotalDialogFragment();
        tD.initialize(callback,order);
        return tD;
    }

    public void initialize(OnClickButton callback,Order order){
        this.mListener = callback;
        this.pupil = order.getPupils();
        this.teacher = order.getTeachers();
//        this.date = order.getDate();
        this.mOrder = order;
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
        View view = inflater.inflate(R.layout.dialog_total_cost,container,false);
        bindView(view);
        data.setText(date);
        pupilView.setText(pupil);
        teacherView.setText(teacher);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.OnClick(true,mOrder);
                dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.OnClick(false,null);
                dismiss();
            }
        });
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hotel.setText(mOrder.getHotel());
                travel.setText(mOrder.isAddTrain()?"Поезд":"Самолет");
                din.setText(mOrder.getCountBr()+"-Завтрак\n"+mOrder.getCountLu()+"-Обед\n"+mOrder.getCountDin()+"-Ужин");
                bus.setText(mOrder.getCountBusMeet()+"-Первый и последний день\n"+mOrder.getCount4Bus()+"-4 часовой автобус\n"+mOrder.getCountAllDayBus()+"-Полный день");
                String excursion ="";
                List<Excursion> list = mOrder.getExcursionList();
                for(int i = 0; i<list.size();++i){
                    excursion+=(i+1)+". "+list.get(i).getName()+"\n";
                }
                exc.setText(excursion);
                slideLF = AnimationUtils.loadAnimation(getContext(),R.anim.slide_left_1_obj);
                slideLS = AnimationUtils.loadAnimation(getContext(),R.anim.slide_left_2_obj);
                linSmal.setAnimation(slideLF);
                linSmal.setVisibility(View.GONE);
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

                linSmal.setVisibility(View.VISIBLE);
                linSmal.setAnimation(slideRF);

            }
        });
        ind.show();
        ok.setClickable(false);
        ok.setAlpha((float) 0.5);
        CalculateTask calculateTask = new CalculateTask();
        Log.i("mm",""+mOrder.getJson());
        calculateTask.execute(mOrder.getJson()); // Добавить сюда json
        return view;
    }



    private void bindView(View view){
        data        = (TextView) view.findViewById(R.id.dialog_total_date);
        pupilView   = (TextView) view.findViewById(R.id.dialog_total_quan_pupil);
        teacherView = (TextView) view.findViewById(R.id.dialog_total_quan_teacher);
        costView    = (TextView) view.findViewById(R.id.dialog_total_cost);
        ok          = (Button) view.findViewById(R.id.dialog_total_ok_butt);
        cancel      = (Button) view.findViewById(R.id.dialog_total_cancel_butt);
        back        = (Button) view.findViewById(R.id.back);
        ind         = (AVLoadingIndicatorView) view.findViewById(R.id.total_indicator);
        more        = (Button) view.findViewById(R.id.but_more_desc);
        bus         = (TextView) view.findViewById(R.id.bus_name);
        travel      = (TextView) view.findViewById(R.id.travel_name);
        hotel       = (TextView) view.findViewById(R.id.hotel_name);
        din         = (TextView) view.findViewById(R.id.din_name);
        exc         = (TextView) view.findViewById(R.id.exc_name);
        linSmal     = (LinearLayout) view.findViewById(R.id.lin_small);
        linMore     = (LinearLayout) view.findViewById(R.id.lin_more);
    }

    private class CalculateTask extends AsyncTask<JSONObject,Void,Boolean> {

        private String cost;

        @Override
        protected Boolean doInBackground(JSONObject... jsonObjects) {
            Boolean success = true;
            String response = RequestSender.POST(getContext(), Constans.URL.CALCULATOR.CALCULATE,jsonObjects[0],false);
            try{
                JSONObject js = new JSONObject(response);
                if(js.getString("result").equalsIgnoreCase("success")) {
                    cost = js.getString("data");
                }else{
                    success = false;
                }
            }catch (Exception e ){
                Log.i("CalculatorFragment","Error parsing answer! Error text: " + e.getMessage());
                success = false;
            }
            return success;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if(success){
                costView.setText(cost);
                ind.hide();
                costView.setVisibility(View.VISIBLE);
                mOrder.setCost(cost);
                ok.setClickable(true);
                ok.setAlpha((float) 1);
            }else{
                Toast.makeText(getContext(),"Ooops! Что-то пошло не так, попробуйте позднее! =)",Toast.LENGTH_LONG)
                        .show();
            }
        }
    }



}

