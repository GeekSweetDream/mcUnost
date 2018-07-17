package com.dreamsofpines.mcunost.ui.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.network.api.Constans;
import com.dreamsofpines.mcunost.data.network.api.RequestSender;
import com.dreamsofpines.mcunost.data.storage.models.Excursion;
import com.dreamsofpines.mcunost.data.storage.models.Order;
import com.dreamsofpines.mcunost.data.storage.preference.GlobalPreferences;
import com.dreamsofpines.mcunost.ui.customView.InformTextView;
import com.dreamsofpines.mcunost.ui.customView.ViewLoginRegistration;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONObject;

import java.util.List;

public class OrderInformationFragment extends Fragment {


    private View view;
    private Order order;
    private InformTextView cityInf;
    private InformTextView dateInf;
    private InformTextView pupilsInf;
    private InformTextView teacherInf;
    private InformTextView hotelInf;
    private InformTextView travInf;
    private InformTextView dinInf;
    private InformTextView transInf;
    private InformTextView excInf;
    private TextView cost;
    private Button orderBtn;
    private AVLoadingIndicatorView avl;
    private ViewLoginRegistration mViewLoginRegistration;
    private boolean haveCost;
    private static OrderInformationFragment sOrderInformationFragment;
    private FragmentManager fm;

    public static OrderInformationFragment getInstance(Order order, FragmentManager fm){
        if(sOrderInformationFragment == null) {
            sOrderInformationFragment = new OrderInformationFragment();
        }
        sOrderInformationFragment.setOrder(order);
        sOrderInformationFragment.setFm(fm);
        return sOrderInformationFragment;
    }

    private View.OnClickListener btnListener = view1 -> {
        if(GlobalPreferences.getPrefAddUser(getContext()) == 1){
            mViewLoginRegistration.setFragmentManager(fm);
            mViewLoginRegistration.show();
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.fragment_show_order,container,false);
        bindView();
        init();
        return view;
    }


    private void init(){
        avl.setVisibility(View.GONE);
        haveCost = order.getDateCreate()!=null;
        orderBtn.setOnClickListener(btnListener);
        orderBtn.setVisibility(!haveCost?View.VISIBLE:View.GONE); // Костыль для проверки новый заказ или уже сущ в базе
        cost.setVisibility(haveCost?View.VISIBLE:View.GONE);
        cost.setText(haveCost?order.getCost():"0");
        cityInf.setText(order.getTour());
        dateInf.setText(order.getDate());
        pupilsInf.setText(order.getPupils());
        teacherInf.setText(order.getTeachers());
//        hotelInf.setText(order.getHotel().equals("null")? "Нет" : order.getHotel());
        travInf.setText(order.isAddTrain()?"Поезд":"Самолет");
        dinInf.setText(getDinnerString());
        transInf.setText(getBusString());
        dinInf.setText(getDinnerString());
        excInf.setText(getExcString());
        if(!haveCost) {
            avl.setVisibility(View.VISIBLE);
            avl.show();
            CalculateTask calculateTask = new CalculateTask();
            calculateTask.execute(order.getJson());
        }

    }


    private String getDinnerString(){
        return order.getCountBr() + "/" + order.getCountLu() + "/" + order.getCountDin();
    }

    private String getBusString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(order.getCountBusMeet().equals("0")?
                "Нет встречающего автобуса\n"
                :"Есть встречающий автобус\n");
        stringBuilder.append(order.getAddBusDays()>0? "+  доп. автобусы":"");
        return  stringBuilder.toString();
    }

    private String getExcString(){
        StringBuilder stringBuilder = new StringBuilder();
        for (Excursion exc: order.getExcursionList()){
            stringBuilder.append(exc.getName() +"\n");
        }
        return stringBuilder.toString();
    }

    private void bindView(){
        cityInf = (InformTextView) view.findViewById(R.id.inf_city);
        dateInf = (InformTextView) view.findViewById(R.id.inf_date);
        pupilsInf = (InformTextView) view.findViewById(R.id.inf_pupils);
        teacherInf = (InformTextView) view.findViewById(R.id.inf_teacher);
        hotelInf = (InformTextView) view.findViewById(R.id.inf_hotel);
        travInf = (InformTextView) view.findViewById(R.id.inf_trav);
        dinInf = (InformTextView) view.findViewById(R.id.inf_din);
        transInf = (InformTextView) view.findViewById(R.id.inf_trans);
        excInf = (InformTextView) view.findViewById(R.id.inf_exc);
        cost = (TextView) view.findViewById(R.id.text_cost_order);
        avl = (AVLoadingIndicatorView) view.findViewById(R.id.avl);
        orderBtn = (Button) view.findViewById(R.id.btn_order);
        mViewLoginRegistration = (ViewLoginRegistration) view.findViewById(R.id.login_view);
    }


    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if(!hidden){
            init();
        }
    }

    public FragmentManager getFm() {
        return fm;
    }

    public void setFm(FragmentManager fm) {
        this.fm = fm;
    }

    private class CalculateTask extends AsyncTask<JSONObject,Void,Boolean> {

        private String costServer;

        @Override
        protected Boolean doInBackground(JSONObject... jsonObjects) {
            Boolean success = true;
            String response = RequestSender.POST(getContext(), Constans.URL.CALCULATOR.CALCULATE,jsonObjects[0],false);
            try{
                JSONObject js = new JSONObject(response);
                if(js.getString("result").equalsIgnoreCase("success")) {
                    costServer = js.getString("data");
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
                cost.setText(costServer);
                avl.hide();
                cost.setVisibility(View.VISIBLE);
            }else{
                Toast.makeText(getContext(),"Ooops! Что-то пошло не так, попробуйте позднее! =)",Toast.LENGTH_LONG)
                        .show();
            }
        }
    }


}
