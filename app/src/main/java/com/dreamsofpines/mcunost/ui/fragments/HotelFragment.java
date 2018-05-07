package com.dreamsofpines.mcunost.ui.fragments;

import android.animation.ObjectAnimator;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

    private View view, err;
    private RecyclerView rec;
    private HotelAdapter mAdapter;
    private Button cancel,resend,accept;
    private List<Hotel> hotelList;
    private Animation jumpOn;
    private String idCity;
    private AVLoadingIndicatorView avl;
    private int pos;

    private OnClickListener listener;

    public interface OnClickListener{
        void onClick(boolean accept,String idHotel, String hotel);
    }

    public void setOnClickListener(OnClickListener listener){
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.fragment_hotel_recycler,container,false);
        bindView();
        setListeners();
        accept.setVisibility(View.GONE);
        cancel.setVisibility(View.GONE);
        avl.show();
        err.setVisibility(View.GONE);
        jumpOn = AnimationUtils.loadAnimation(getContext(),R.anim.jump_from_down_without_alpha);
        TextView title = (TextView) getActivity().findViewById(R.id.title_tour);
        title.setText("Отели");
        Button help = (Button) getActivity().findViewById(R.id.button_help);
        help.setVisibility(View.GONE);

        HotelTask hotelTask = new HotelTask();
        hotelTask.execute(getArguments());
        return view;
    }

    private void bindView(){
        rec     = (RecyclerView) view.findViewById(R.id.hotel_recycler);
        accept  = (Button) view.findViewById(R.id.hotel_accept);
        cancel  = (Button) view.findViewById(R.id.hotel_cancel);
        avl     = (AVLoadingIndicatorView) view.findViewById(R.id.hotel_indicator);
        err     = (View) view.findViewById(R.id.error_message);
        resend  = (Button) err.findViewById(R.id.category_resend_butt);
    }

    private void setListeners(){
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = 0;
                Hotel hotel = null;
                for(Hotel h:hotelList) {
                    if(h.isChecked()) {
                        hotel = h;
                        ++count;
                    }
                }
                if(count<=1) {
                    if (hotel != null) {
                        listener.onClick(true, hotel.getId(), hotel.getName());
                    } else {
                        listener.onClick(false, null, null);
                    }
                }else{
                    Toast.makeText(getContext(),"Выберите только один отель!",Toast.LENGTH_LONG).show();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(false,null,null);
            }
        });
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                err.setVisibility(View.GONE);
                HotelTask hotelTask = new HotelTask();
                hotelTask.execute(getArguments());
            }
        });
    }

    private void updateUI(){
        if(null == rec.getLayoutManager()) {
            LinearLayoutManager lr = new LinearLayoutManager(getActivity());
            rec.setLayoutManager(lr);
        }
        mAdapter = new HotelAdapter();
        mAdapter.setContext(getContext());
        mAdapter.setOnClickBoxListener(new HotelAdapter.OnClickBoxListener() {
            @Override
            public void onClicked(boolean add, int position) {
                    hotelList.get(position).setChecked(add);
            }
        });
        rec.setAdapter(mAdapter);
        mAdapter.setHotelList(hotelList);
        mAdapter.notifyDataSetChanged();
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
                    for(int i = 0; i < length; ++i) {
                        JSONObject hotelJs = hotelJsonArray.getJSONObject(i);
                        Hotel hotel = new Hotel(
                                hotelJs.getString("id"),
                                hotelJs.getString("name"),
                                "");
                        if(hotel.getId().equalsIgnoreCase(bundles[0].getString("id"))) {
                            hotel.setChecked(true);
                            pos = i;
                        }
                        hotelList.add(hotel);

                    }
                    if(bundles[0] == null){
                        int i = 0;
                        pos = i;
                        hotelList.get(i).setChecked(true);
                    }
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
                    cancel.setVisibility(View.VISIBLE);
                }else{
                    ObjectAnimator animator = ObjectAnimator.ofFloat(rec,"alpha",0,1);
                    animator.setDuration(700);
                    animator.start();
                    accept.setVisibility(View.VISIBLE);
                    cancel.setVisibility(View.VISIBLE);
                    rec.setVisibility(View.VISIBLE);
                }
                avl.hide();
//                if(errorView.getVisibility() != View.GONE) {
//                    errorView.setVisibility(View.GONE);
//                }
            }else{
                avl.show();
                err.setAnimation(jumpOn);
                err.setClickable(true);
                err.setVisibility(View.VISIBLE);
                cancel.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(),"Oops! Проблемы с соединением, попробуйте позже! :)",Toast.LENGTH_LONG)
                        .show();
            }
        }
    }


}
