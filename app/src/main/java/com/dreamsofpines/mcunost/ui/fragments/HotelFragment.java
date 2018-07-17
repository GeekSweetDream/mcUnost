package com.dreamsofpines.mcunost.ui.fragments;

import android.animation.ObjectAnimator;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.network.api.RequestSender;
import com.dreamsofpines.mcunost.data.storage.models.Hotel;
import com.dreamsofpines.mcunost.ui.adapters.recyclerHotel.HotelAdapter;
import com.dreamsofpines.mcunost.ui.customView.ViewFragmentPattern;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThePupsick on 24.02.2018.
 */

public class HotelFragment extends Fragment {

    private View view,field, err;
    private RecyclerView rec;
    private HotelAdapter mAdapter;
    private List<Hotel> hotelList;
    private String idCity;
    private AVLoadingIndicatorView avl;
    private int pos;
    private ViewFragmentPattern fragment;
    private float x,y;
    private int idHotel;
    private String nameHotel;


    private OnClickListener listener;

    public interface OnClickListener{
        void onClick(String idHotel, String hotel);
    }

    public void setOnClickListener(OnClickListener listener){
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.fragment_quantity_dinner,container,false);
        field = (View) inflater.inflate(R.layout.fragment_hotel_recycler,container,false);
        bindView();
        getInformationFromBundle();
        onPressBackListener();
        settingFragment();

        avl.show();

        HotelTask hotelTask = new HotelTask();
        hotelTask.execute(getArguments());

        return view;
    }

    private void getInformationFromBundle() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            x = bundle.getFloat("x");
            y = bundle.getFloat("y");
            idHotel = -1;
            if(bundle.getString("idHotel")!=null) {
                idHotel = Integer.valueOf(bundle.getString("idHotel"));
                nameHotel = bundle.getString("nameHotel");
            }
        }
    }

    private void settingFragment(){
        fragment.setTitleInToolbar("Отель: ");
        fragment.setViewInLayout(field);
        fragment.setPositionX(x);
        fragment.setPositionY(y);
        if(nameHotel!=null) fragment.setTextInToolbar(nameHotel);
        fragment.setIconInToolbar("icon_hotel");
        fragment.setOnBackgroundClickListener(()->{
            fragment.hide();
            if(mAdapter == null) {
                listener.onClick("-1","-1");
            }else {
                Hotel hotel = hotelList.get(mAdapter.getCurPosition());
                listener.onClick(hotel.getId(), hotel.getName());
            }
        });
        new Handler().postDelayed(()->fragment.show(),200);
    }


    private void onPressBackListener(){
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener((view1, i,keyEvent)->{
            if( i == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                fragment.hide();
                if(mAdapter == null) {
                    listener.onClick("-1","-1");
                }else {
                    Hotel hotel = hotelList.get(mAdapter.getCurPosition());
                    listener.onClick(hotel.getId(), hotel.getName());
                }
                return true;
            }
            return false;
        });
    }

    private void bindView(){
        rec     = (RecyclerView) field.findViewById(R.id.hotel_recycler);
        avl     = (AVLoadingIndicatorView) field.findViewById(R.id.hotel_indicator);
        fragment = (ViewFragmentPattern) view.findViewById(R.id.fragment);
    }


    private void updateUI(){
        if(null == rec.getLayoutManager()) {
            LinearLayoutManager lr = new LinearLayoutManager(getActivity());
            rec.setLayoutManager(lr);
        }
        if (pos == 0) hotelList.get(0).setChecked(true);
        fragment.setTextInToolbar(hotelList.get(pos).getName());
        mAdapter = new HotelAdapter();
        mAdapter.setContext(getContext());
        mAdapter.setOnClickBoxListener((position) ->{
            fragment.setTextInToolbar(hotelList.get(position).getName());
        });
        mAdapter.setCurPosition(pos);
        rec.setAdapter(mAdapter);
        mAdapter.setHotelList(hotelList);
    }

    private class HotelTask extends AsyncTask<Bundle,Void,Boolean>{

        @Override
        protected Boolean doInBackground(Bundle... bundles) {
            Boolean success = true;
            hotelList = new ArrayList<>();
            String response = RequestSender.GetHotel(bundles[0].getString("idCity"));
            try {
                JSONObject js = new JSONObject(response);
                String resultResponce = js.getString("result");
                if (resultResponce.equalsIgnoreCase("success")) {
                    JSONArray hotelJsonArray = js.getJSONArray("data");
                    int length = hotelJsonArray.length();
                    pos = 0;
                    for(int i = 0; i < length; ++i) {
                        JSONObject hotelJs = hotelJsonArray.getJSONObject(i);
                        Hotel hotel = new Hotel(
                                hotelJs.getString("id"),
                                hotelJs.getString("name"),
                                "");
                        if(Integer.valueOf(hotel.getId())==idHotel) {
                            hotel.setChecked(true);
                            pos = i;
                        }
                        hotelList.add(hotel);
                    }
//                    if(bundles[0] == null){
//                        int i = 0;
//                        pos = i;
//                        hotelList.get(i).setChecked(true);
//                    }
                }else{
                    success = false;
                    Log.i("HotelFragment:","Bad answer! Error message:"+js.getString("mess"));
                    /* error answer (add log.i())*/
                }
            } catch (JSONException e) {
                success = false;
                Log.i("HotelFragment:","JsonExeption from parsing json pack_excur! Error message:"+e.getMessage());
            } catch (Exception e){
                success = false;
                Log.i("HotelFragment:"," Error! Error message:"+e.getMessage());
            }
            return success;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if(success) {
                updateUI();
                if(hotelList.size()==0) {
                    rec.setVisibility(View.GONE);
                }else{
                    ObjectAnimator animator = ObjectAnimator.ofFloat(rec,"alpha",0,1);
                    animator.setDuration(700);
                    animator.start();
                    rec.setVisibility(View.VISIBLE);
                }
                avl.hide();
            }else{
                avl.show();
//                err.setAnimation(jumpOn);
//                err.setClickable(true);
//                err.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(),"Oops! Проблемы с соединением, попробуйте позже! :)",Toast.LENGTH_LONG)
                        .show();
            }
        }
    }


}
