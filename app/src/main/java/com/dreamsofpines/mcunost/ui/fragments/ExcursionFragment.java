package com.dreamsofpines.mcunost.ui.fragments;

import android.animation.ObjectAnimator;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
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
import com.dreamsofpines.mcunost.data.storage.models.Excursion;
import com.dreamsofpines.mcunost.ui.adapters.recyclerExcursion.ExcursionAdapter;
import com.dreamsofpines.mcunost.ui.customView.ViewFragmentPattern;
import com.dreamsofpines.mcunost.ui.dialog.WarningDialog;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.media.CamcorderProfile.get;

/**
 * Created by ThePupsick on 03.03.2018.
 */

public class ExcursionFragment extends Fragment {

    private View view,field;
    private ViewFragmentPattern fragment;
    private RecyclerView rec;
    private List<Excursion> all;
    private ExcursionAdapter mAdapter;
    private AVLoadingIndicatorView avl;
    private String idCity;

    private float x,y;
    private int maxExc, curExc;
    private boolean isEmptyExcurList;

    private OnClickListener listener;

    public interface OnClickListener{
        void onClick(List<Excursion>list);
    }

    public void setOnClickListener(OnClickListener listener){
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.fragment_quantity_dinner,container,false);
        field = (View) inflater.inflate(R.layout.fragment_excursion_rec,container,false);
        bindView();
        getInfoFromBundle();
        setListeners();
        setListenerBack();
        settingFragment();

        Bundle bundle = getArguments();
        avl.show();

        ExcurTask excurTask = new ExcurTask();
        excurTask.execute(bundle);
        return view;
    }

    private void getInfoFromBundle(){
        Bundle bundle = getArguments();
        x = bundle.getFloat("x");
        y = bundle.getFloat("y");
        maxExc = bundle.getInt("maxday");
        curExc = bundle.getInt("size") == 0? maxExc : bundle.getInt("size");
        isEmptyExcurList = bundle.getInt("size") == 0;
        idCity = bundle.getString("idcity");
    }

    private void settingFragment(){
        fragment.setViewInLayout(field);
        fragment.setPositionX(x);
        fragment.setPositionY(y);
        fragment.setTitleInToolbar("Экскурсии:");
        fragment.setTextInToolbar( curExc + "/" + maxExc*2);
        fragment.setIconInToolbar("icon_exc");
        fragment.setOnBackgroundClickListener(()->{
            fragment.hide();
            listener.onClick(getAdd());
        });
        new Handler().postDelayed(()-> fragment.show(),200);
    }

    private void setListenerBack(){
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener((view, i,keyEvent)->{
            if( i == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                fragment.hide();
                listener.onClick(getAdd());
                return true;
            }
            return false;
        });
    }


    private void bindView(){
        rec          = (RecyclerView) field.findViewById(R.id.exc_rec);
        avl          = (AVLoadingIndicatorView) field.findViewById(R.id.exc_indicator);
        fragment     = (ViewFragmentPattern) view.findViewById(R.id.fragment);
    }

    private void setListeners(){

    }
    private void updateUI(){
        if(null == rec.getLayoutManager()) {
            LinearLayoutManager lr = new LinearLayoutManager(getActivity());

            rec.setLayoutManager(lr);
        }
        mAdapter = new ExcursionAdapter();
        mAdapter.setContext(getContext());
        mAdapter.setOnClickSwitchListener((boolean add, int position) ->{
                curExc += (add? 1 : -1);
                all.get(position).setChecked(add);
                fragment.setTextInToolbar(curExc+"/"+maxExc*2);
        });
        rec.setAdapter(mAdapter);
        mAdapter.setExcursionList(all);
        fragment.setTextInToolbar(curExc+"/"+maxExc*2);
    }

    private List<Excursion> getAdd(){
        List<Excursion> add = new ArrayList<>();
        for (Excursion exc:all){
            if(exc.isChecked()) add.add(exc);
        }
        return add;
    }


    private class ExcurTask extends AsyncTask<Bundle,Void,Boolean> {

        @Override
        protected Boolean doInBackground(Bundle... bundles) {
            Boolean success = true;
            all = new ArrayList<>();
            String response = RequestSender.GetExcursion(idCity);
            try {
                JSONObject js = new JSONObject(response);
                String resultResponce = js.getString("result");
                if (resultResponce.equalsIgnoreCase("success")) {
                    final JSONArray excJsonArray = js.getJSONArray("data");
                    int length = excJsonArray.length();
                    for(int i = 0; i < length; ++i) {
                        JSONObject excJs = excJsonArray.getJSONObject(i);
                        Excursion exc = new Excursion(
                                excJs.getString("id"),
                                excJs.getString("name"),
                                false,
                                excJs.getInt("ratio"));
                        all.add(exc);
                    }
                    if(isEmptyExcurList){
                        curExc = 0;
                        for(int i = 0; i < maxExc && (i < all.size());++i) {
                            all.get(i).setChecked(true);
                            ++curExc;
                        }
                    }else{
                        for(int i = 0; i<bundles[0].getInt("size");++i){
                            for(Excursion exc:all){
                                if(exc.getId().equalsIgnoreCase(bundles[0].getString(String.valueOf(i)))){
                                    exc.setChecked(true);
                                }
                            }
                        }
                    }
                }else{
                    success = false;
                    Log.i("ExcursionFragment:","Bad answer! Error message:"+js.getString("mess"));
                    /* error answer (add log.i())*/
                }
            } catch (JSONException e) {
                success = false;
                Log.i("ExcursionFragment:","JsonExeption from parsing json excur! Error message:"+e.getMessage());
            } catch (Exception e){
                success = false;
                Log.i("ExcursionFragment:"," Error! Error message:"+e.getMessage());
            }
            return success;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if(success) {
                updateUI();
                if(all.size()==0) {
                }else{
                    sort(all);
                    if(all.size()<maxExc){
                        curExc = all.size();
                    }
                    rec.setVisibility(View.VISIBLE);
                }
                avl.hide();
            }else{
                avl.show();
                Toast.makeText(getContext(),"Oops! Проблемы с соединением, попробуйте позже! :)",Toast.LENGTH_LONG)
                        .show();
            }
        }
    }

    private void sort(List<Excursion> list ){
        Collections.sort(list,new Comparator<Excursion>() {
            @Override
            public int compare(Excursion excursion, Excursion t1) {
                int a = excursion.getRatio();
                int b = t1.getRatio();
                if(a > b){
                    return 1;
                }else if(a < b){
                    return -1;
                }else{
                    return 0;
                }
            }
        });

    }


}
