package com.dreamsofpines.mcunost.ui.fragments;

import android.animation.ObjectAnimator;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
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
import com.dreamsofpines.mcunost.data.storage.models.Excursion;
import com.dreamsofpines.mcunost.ui.adapters.recyclerExcursion.ExcursionAdapter;
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

    private View view,err;
    private Button accept, cancel, resend;
    private RecyclerView rec;
    private List<Excursion> all, add;
    private ExcursionAdapter mAdapter;
    private Animation jumpOn;
    private TextView txtChoose;
    private AVLoadingIndicatorView avl;
    private NestedScrollView nestedScroll;
    private String idCity;

    private int maxExc, curExc;

    private OnClickListener listener;

    public interface OnClickListener{
        void onClick(boolean accept, List<Excursion>list);
    }

    public void setOnClickListener(OnClickListener listener){
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.fragment_excursion_rec,container,false);
        bindView();
        setListeners();
        Bundle bundle = getArguments();
        maxExc = bundle.getInt("maxday");
        curExc = bundle.getInt("size") == 0? maxExc : bundle.getInt("size");
        idCity = bundle.getString("idcity");
        jumpOn = AnimationUtils.loadAnimation(getContext(),R.anim.jump_from_down_without_alpha);
        accept.setVisibility(View.GONE);
        cancel.setVisibility(View.GONE);
        nestedScroll.setVisibility(View.GONE);
        avl.show();
        txtChoose.setText(curExc +"/"+maxExc*2);
        TextView title = (TextView) getActivity().findViewById(R.id.title_tour);
        title.setText("Экскурсии");
        Button help = (Button) getActivity().findViewById(R.id.button_help);
        help.setVisibility(View.GONE);

        ExcurTask excurTask = new ExcurTask();
        excurTask.execute(bundle);
        return view;
    }

    private void bindView(){
        accept       = (Button) view.findViewById(R.id.exc_accept);
        cancel       = (Button) view.findViewById(R.id.exc_cancel);
        rec          = (RecyclerView) view.findViewById(R.id.exc_rec);
        err          = (View) view.findViewById(R.id.error_message);
        resend       = (Button) err.findViewById(R.id.category_resend_butt);
        avl          = (AVLoadingIndicatorView) view.findViewById(R.id.exc_indicator);
        nestedScroll = (NestedScrollView) view.findViewById(R.id.exc_nested);
        txtChoose    = (TextView) view.findViewById(R.id.txt_exc_choose);

    }

    private void setListeners(){
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add = new ArrayList<>();
                for (Excursion exc : all) {
                    if (exc.isChecked()) add.add(exc);
                }
                if(curExc > maxExc*2) {
                    WarningDialog wD = WarningDialog.newInstance();
                    wD.setOkPressListener(new WarningDialog.okListner() {
                        @Override
                        public void okPress() {
                            listener.onClick(true, add);
                        }
                    });
                    wD.show(getFragmentManager(),"wD");
                }else{
                    listener.onClick(true, add);
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(false,null);
            }
        });
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                err.setVisibility(View.GONE);
                accept.setVisibility(View.GONE);
                cancel.setVisibility(View.GONE);
                nestedScroll.setVisibility(View.GONE);
                avl.show();
                ExcurTask excurTask = new ExcurTask();
                excurTask.execute(getArguments());
            }
        });

    }
    private void updateUI(){
        if(null == rec.getLayoutManager()) {
            LinearLayoutManager lr = new LinearLayoutManager(getActivity());

            rec.setLayoutManager(lr);
        }
        mAdapter = new ExcursionAdapter();
        mAdapter.setContext(getContext());
        mAdapter.setOnClickSwitchListener(new ExcursionAdapter.OnClickSwitchListener() {
            @Override
            public void onClicked(boolean add, int position) {
                if(add) {
                    curExc++;
                }else{
                    curExc--;
                }
                all.get(position).setChecked(add);
                txtChoose.setText(curExc +"/"+maxExc*2);
            }
        });
        rec.setAdapter(mAdapter);
        mAdapter.setExcursionList(all);
//        mAdapter.notifyDataSetChanged();
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
                    if(bundles[0].getInt("size")==0){
                        for(int i = 0; i < maxExc && (i < all.size());++i) {
                            all.get(i).setChecked(true);
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
                    cancel.setVisibility(View.VISIBLE);
                }else{
                    sort(all);
                    ObjectAnimator animator = ObjectAnimator.ofFloat(nestedScroll,"alpha",0,1);
                    animator.setDuration(700);
                    animator.start();
                    nestedScroll.setVisibility(View.VISIBLE);
                    accept.setVisibility(View.VISIBLE);
                    cancel.setVisibility(View.VISIBLE);
                    if(all.size()<maxExc){
                        curExc = all.size();
                    }
                    rec.setVisibility(View.VISIBLE);
                }
                avl.hide();
                err.setVisibility(View.GONE);
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
