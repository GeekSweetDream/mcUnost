package com.dreamsofpines.mcunost.ui.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.network.api.RequestSender;
import com.dreamsofpines.mcunost.data.storage.models.Excursion;
import com.dreamsofpines.mcunost.data.storage.models.Order;
import com.dreamsofpines.mcunost.ui.adapters.recyclerOrder.OrderAdapter;
import com.dreamsofpines.mcunost.ui.dialog.ShortInfoOrderDialog;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by ThePupsick on 19.08.17.
 */

public class MyOrderFragment extends Fragment implements ShortInfoOrderDialog.OnClickChatListener{

    private FragmentManager fm;
    private InfoOrder iO;
    private View view,errorView,status_bar;
    private TextView textView,title, empty;
    private RecyclerView rec;
    private AVLoadingIndicatorView avl;
    private Button resend;
    private CheckBox ch1, ch2, ch3;
    private List<Order> ord;
    private Animation anim;
    public static OnClickChatListener listener;
    public interface OnClickChatListener{
        void onClick(Bundle bundle);
    }

    public void setOnClickChatListener(OnClickChatListener listener){
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_order,container,false);
        bindView();
        title.setText("Заявки");
        Button help = (Button) getActivity().findViewById(R.id.button_help);
        help.setVisibility(View.GONE);
        avl.show();
        rec.setVisibility(View.GONE);
        anim = AnimationUtils.loadAnimation(getContext(), R.anim.jump_from_down_without_alpha);
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetOrderTask getOrderTask = new GetOrderTask();
                getOrderTask.execute();
                avl.show();
                errorView.setVisibility(View.GONE);
            }
        });
        GetOrderTask getOrderTask = new GetOrderTask();
        getOrderTask.execute();
        return view;
    }

    private void bindView(){
        textView   = (TextView) view.findViewById(R.id.title_my_order);
        rec        = (RecyclerView) view.findViewById(R.id.recycler_my_order);
        title      = (TextView) getActivity().findViewById(R.id.title_tour);
        empty      = (TextView) view.findViewById(R.id.empty_orders);
        avl        = (AVLoadingIndicatorView) view.findViewById(R.id.load_indicator);
        errorView  = (View) view.findViewById(R.id.error_message);
        resend     = (Button) errorView.findViewById(R.id.category_resend_butt);
        status_bar = (View) view.findViewById(R.id.status_bar);
        ch1        = (CheckBox) status_bar.findViewById(R.id.checkBox);
        ch2        = (CheckBox) status_bar.findViewById(R.id.checkBox2);
        ch3        = (CheckBox) status_bar.findViewById(R.id.checkBox3);
    }

    private void setListenerView(){
        ch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    ch3.setChecked(false);
                    ch2.setChecked(false);
                    chooseStatusOrder(1);
                }else{
                    ch3.setChecked(!ch2.isChecked());
                    chooseStatusOrder(3);
                }
            }
        });
        ch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    ch3.setChecked(false);
                    ch1.setChecked(false);
                    chooseStatusOrder(2);
                }else{
                    ch3.setChecked(!ch1.isChecked());
                    chooseStatusOrder(3);
                }
            }
        });
        ch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    ch1.setChecked(false);
                    ch2.setChecked(false);
                    chooseStatusOrder(3);
                } else {
                    ch3.setChecked(!(ch1.isChecked() || ch2.isChecked()));
                    chooseStatusOrder(3);
                }
            }
        });
    }

    private void chooseStatusOrder(int chB){
        List<Order> newOrd = new ArrayList<>();
        switch (chB){
            case 1: {
                for(Order obj: ord){
                    String stat = obj.getStatus();
                    if(stat.equalsIgnoreCase("В обработке") || stat.equalsIgnoreCase("Заказан")){
                        newOrd.add(obj);
                    }
                }
                break;
            }
            case 2:{
                for(Order obj: ord){
                    String stat = obj.getStatus();
                    if(stat.equalsIgnoreCase("Выполнен")){
                        newOrd.add(obj);
                    }
                }
                break;
            }
            case 3:{
                newOrd = ord;
                break;
            }
        }
        updateUI(newOrd);
    }

    private void updateUI(final List<Order> orders){
        if(null == rec.getLayoutManager()) {
            LinearLayoutManager lr = new LinearLayoutManager(getActivity());
            lr.setReverseLayout(true);
            lr.setStackFromEnd(true);
            rec.setLayoutManager(lr);
        }
        OrderAdapter mAdapter = new OrderAdapter();
        mAdapter.setActivity(getActivity());
        mAdapter.setOnClickOrderListener(new OrderAdapter.OnClickOrderListener() {
                @Override
                public void OnClicked(View itemView, int position) {
                    ShortInfoOrderDialog sD = ShortInfoOrderDialog.newInstance(orders.get(position),MyOrderFragment.this);
                    sD.show(getFragmentManager(), "ShortInfo");
                }
        });
        rec.setAdapter(mAdapter);
        mAdapter.setOrderList(orders);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        fm = getChildFragmentManager();
    }

//    private Bundle createBundle(int position,List<Order> ord){
//        Bundle bundle = new Bundle();
//        bundle.putString("idord",ord.get(position).getId());
//        bundle.putString("date", ord.get(position).getDate());
//        bundle.putString("cost", ord.get(position).getCost());
//        bundle.putString("school", ord.get(position).getPupils());
//        bundle.putString("teacher", ord.get(position).getTeachers());
//        bundle.putString("tour", ord.get(position).getTour());
//        bundle.putString("manager", ord.get(position).getManager());
//        bundle.putString("phone", ord.get(position).getPhone());
//        bundle.putString("status", ord.get(position).getStatus());
//        return bundle;
//    }

    @Override
    public void onClick(int idOrd) {
        Bundle bundle = new Bundle();
        bundle.putInt("idOrder",idOrd);
        if(listener != null){
            listener.onClick(bundle);
        }
    }

    private class GetOrderTask extends AsyncTask<Void,Void,Boolean>{

        @Override
        protected Boolean doInBackground(Void... voids) {
            Boolean success = true;
            ord = new ArrayList<>();
            String response = RequestSender.GetAllOrdersById(getContext());
            try {
                JSONObject js = new JSONObject(response);
                String resultResponce = js.getString("result");
                if (resultResponce.equalsIgnoreCase("success")) {
                    JSONArray ordersJsonArray = js.getJSONArray("data");
                    int length = ordersJsonArray.length();
                    for(int i = 0; i < length; ++i) {
                        JSONObject ordJs = ordersJsonArray.getJSONObject(i);
                        String managerName = "Неизвестно";
                        String managerPhone = "Неизвестно";
                        if(!ordJs.getString("manager").equalsIgnoreCase("null")) {
                            JSONObject manager = ordJs.getJSONObject("manager");
                            managerName = manager.getString("name");
                            managerPhone = manager.getString("phone");
                        }
                        Order order = new Order();
//                        Order order = new Order(
//                                ordJs.getString("nameTour"),
//                                ordJs.getString("dateTravelTour"),
//                                ordJs.getString("cost"),
//                                ordJs.getString("quantityChildren"),
//                                ordJs.getString("quantityTeacher"),
//                                ordJs.getJSONObject("status").getString("status"),
//                                managerName,
//                                managerPhone);
                        order.setId(ordJs.getString("id"));
//                        order.setDateCreate(translateDateInToString(ordJs.getLong("dateCreateOrder")));
                        order.setIdHotel(ordJs.getString("idHotel"));
                        order.setCountBr(ordJs.getInt("countBr"));
                        order.setCountLu(ordJs.getInt("countLu"));
                        order.setCountDin(ordJs.getInt("countDin"));
                        order.setAddTrain(ordJs.getInt("addTrain")==1);
                        order.setCount4Bus(ordJs.getString("count4Bus"));
                        order.setCountBusMeet(ordJs.getString("countMeetBus"));
                        order.setCountAllDayBus(ordJs.getString("countAllDayBus"));
                        order.setHotel(ordJs.getString("nameHotel"));
                        JSONArray jsArray = ordJs.getJSONArray("list");
                        List<Excursion> list = new ArrayList<>();
                        for(int j = 0; j < jsArray.length();++j){
                            list.add(new Excursion(jsArray.getJSONObject(j).getString("id"),
                                    jsArray.getJSONObject(j).getString("name"),true,1));
                        }
                        order.setExcursionList(list);
                        ord.add(order);
                    }
                }else{
                    success = false;
                    Log.i("PackExcur:","Bad answer! Error message:"+js.getString("mess"));
                    /* error answer (add log.i())*/
                }
            } catch (JSONException e) {
                success = false;
                Log.i("PackExcur:","JsonExeption from parsing json pack_excur! Error message:"+e.getMessage());
            } catch (Exception e){
                success = false;
                Log.i("PackExcur:"," Error! Error message:"+e.getMessage());
            }
            return success;
        }

        private String translateDateInToString(long date){
            return new SimpleDateFormat("dd.MM.yyyy").format(new Date(date));
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if(success) {
                if(ord.size()==0) {
                    rec.setVisibility(View.GONE);
                    empty.setVisibility(View.VISIBLE);
                }else{
                    Collections.sort(ord, new Comparator<Order>() {
                        @Override
                        public int compare(Order order, Order t1) {
                            return Integer.valueOf(order.getId()).compareTo(Integer.valueOf(t1.getId()));
                        }
                    });
                    status_bar.setVisibility(View.VISIBLE);
                    setListenerView();
                    ch1.setChecked(true);
                    rec.setVisibility(View.VISIBLE);
                }
                avl.hide();
                if(errorView.getVisibility() != View.GONE) {
                    errorView.setVisibility(View.GONE);
                }

            }else{
                avl.show();
                errorView.setAnimation(anim);
                errorView.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(),"Oops! Проблемы с соединением, попробуйте позже!",Toast.LENGTH_LONG)
                        .show();
            }
        }
    }
}

