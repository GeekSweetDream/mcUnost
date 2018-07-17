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
import android.widget.Toast;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.network.api.Constans;
import com.dreamsofpines.mcunost.data.network.api.RequestSender;
import com.dreamsofpines.mcunost.data.storage.models.InformExcursion;
import com.dreamsofpines.mcunost.ui.adapters.recyclerCategory.ViewAdapter;
import com.dreamsofpines.mcunost.ui.customView.ViewFragmentPattern;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class CategoriesFragment extends Fragment {

    private View view,field;
    private RecyclerView categoryView;
    private ViewAdapter mAdapter;
    private AVLoadingIndicatorView avi;
    private List<InformExcursion> excursions, mCity;
    private OnClickRecyclerListener mListener;
    private String fromCity;
    private ViewFragmentPattern fragment;
    private int idCity;
    private int curPosition;
    private String toCity;
    private float x,y;


    public interface OnClickRecyclerListener{
        void onClicked(Bundle bundle);
    }

    public void setOnClickRecyclerListener(OnClickRecyclerListener listener){
        this.mListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.fragment_quantity_dinner,container,false);
        field = (View) inflater.inflate(R.layout.fragment_categories,container,false);

        bindView();
        getInformationFromBundle();
        settingViewFragmentPattern();

        categoryView.setLayoutManager(new LinearLayoutManager(getActivity()));
        avi.show();

        CategoryTask categoryTask = new CategoryTask();
        categoryTask.execute();

        onPressBackListener(view);

        return view;
    }

    private void settingViewFragmentPattern(){
        fragment.setTitleInToolbar("Куда:");
        fragment.setViewInLayout(field);
        if(toCity!=null) fragment.setTextInToolbar(toCity);
        fragment.setPositionX(x);
        fragment.setPositionY(y);
        fragment.setIconInToolbar("icon_m");
        fragment.setOnBackgroundClickListener(()->{
            fragment.hide();
            mListener.onClicked(saveBundle(mAdapter == null? -1 :mAdapter.getCurrentPosition()));
        });

        new Handler().postDelayed(()->fragment.show(),200);
    }

    private void getInformationFromBundle(){
        Bundle bundle = getArguments();
        if(bundle!= null){
            toCity = bundle.getString("curCity");
            idCity = bundle.getString("curIdCity")==null? -1:Integer.valueOf(bundle.getString("curIdCity"));
            fromCity = bundle.getString("city");
            x = bundle.getFloat("x");
            y = bundle.getFloat("y");
        }
    }


    private void onPressBackListener(View view){
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener((view1, i,keyEvent)->{
            if( i == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                fragment.hide();
                mListener.onClicked(saveBundle(
                        mAdapter == null? -1 :mAdapter.getCurrentPosition()));
                return true;
            }
            return false;
        });
    }



    private void bindView(){
        fragment = (ViewFragmentPattern) view.findViewById(R.id.fragment);
        categoryView = (RecyclerView) field.findViewById(R.id.category_recycler_view);
        avi          = (AVLoadingIndicatorView) field.findViewById(R.id.category_indicator);
    }


    private void updateUI(){
        mCity = findExcurWithCity();
        if(curPosition == 0) mCity.get(curPosition).setCheck(true);
        fragment.setTextInToolbar(mCity.get(curPosition).getTittle());
        mAdapter = new ViewAdapter(mCity,getContext());
        mAdapter.setOnItemListener((position)-> {
            fragment.setTextInToolbar(mCity.get(position).getTittle());
        });
        categoryView.setAdapter(mAdapter);
        mAdapter.setCurrentPosition(curPosition);
    }

    private Bundle saveBundle(int position){
        Bundle save = new Bundle();
        save.putString("pack_exc", position == -1? "-1":mCity.get(position).getTittle());
        save.putString("id", position == -1? "-1":mCity.get(position).getId());
        return save;
    }

    private List<InformExcursion> findExcurWithCity(){
        mCity = new ArrayList<>();
        int i = 0;
        for (InformExcursion inf: excursions ) {
            if(!inf.getTittle().equalsIgnoreCase(fromCity) ){ // удаляю город из которого отправляемся и ставлю чек, если город уже выбран
                if(Integer.valueOf(inf.getId()) == idCity) {
                    inf.setCheck(true);
                    curPosition = i;
                }
                mCity.add(inf);
                ++i;
            }
        }
        return mCity;
    }

    private class CategoryTask extends AsyncTask<Void, Void, Boolean>{

        private List<InformExcursion> categoryList;

        @Override
        protected Boolean doInBackground(Void... voids) {
            Boolean success = true;
            categoryList = new ArrayList<>();
            String response = RequestSender.GET(Constans.URL.TOUR.GET_CITY);
            try {
                JSONObject js = new JSONObject(response);
                String resultResponce = js.getString("result");
                if (resultResponce.equalsIgnoreCase("success")) {
                    JSONArray categoryJsonArray = js.getJSONArray("data");
                    int length = categoryJsonArray.length();
                    for(int i = 0; i < length; ++i) {
                        JSONObject category = categoryJsonArray.getJSONObject(i);
                        categoryList.add(new InformExcursion(
                                category.getString("name"),
                                category.getString("pathImage"))
                        );
                        categoryList.get(i).setId(categoryJsonArray.getJSONObject(i).getString("id"));
                    }
                }else{
                    success = false;
                    Log.i("CategoryFrag","Error answer! Error message: "+js.getString("mess"));
                }
            } catch (Exception e) {
                success = false;
            }
            return success;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if(!success){
                Toast.makeText(getContext(),"Ошибка сети, нет подключения к интернету!",Toast.LENGTH_LONG)
                        .show();
                categoryView.setVisibility(View.GONE);
            }else {
//                if(errorMessage.getVisibility() != View.GONE) {
//                    errorMessage.setVisibility(View.GONE);
//                }
                avi.hide();
                ObjectAnimator animator = ObjectAnimator.ofFloat(categoryView,"alpha",0,1);
                animator.setDuration(700);
                animator.start();
                categoryView.setVisibility(View.VISIBLE);
                categoryView.setClickable(true);
                excursions = categoryList;
                updateUI();
            }
        }
    }
}
