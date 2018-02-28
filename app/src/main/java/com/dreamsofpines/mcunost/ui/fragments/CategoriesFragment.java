package com.dreamsofpines.mcunost.ui.fragments;

import android.app.ProgressDialog;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.network.api.Constans;
import com.dreamsofpines.mcunost.data.network.api.RequestSender;
import com.dreamsofpines.mcunost.data.database.MyDataBase;
import com.dreamsofpines.mcunost.data.storage.help.menu.InformExcursion;
import com.dreamsofpines.mcunost.data.storage.preference.GlobalPreferences;
import com.dreamsofpines.mcunost.ui.adapters.recyclerCategory.ViewAdapter;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThePupsick on 04.08.17.
 */

public class CategoriesFragment extends Fragment {

    private RecyclerView categoryView;
    private ViewAdapter mAdapter;
    private MyDataBase db;
    private View errorMessage;
    private Button resend;
    private Animation jumpOn, jumpOff;
    private AVLoadingIndicatorView avi;
    private List<InformExcursion> excursions, mCategory;
    private OnClickRecyclerListener mListener;

    public interface OnClickRecyclerListener{
        void onClicked(Bundle bundle);
    }

    public void setOnClickRecyclerListener(OnClickRecyclerListener listener){
        this.mListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.fragment_categories,container,false);
        categoryView = (RecyclerView) view.findViewById(R.id.category_recycler_view);
        categoryView.setLayoutManager(new LinearLayoutManager(getActivity()));
        avi = (AVLoadingIndicatorView) view.findViewById(R.id.category_indicator);
        errorMessage = (View) view.findViewById(R.id.error_message);
        jumpOn = AnimationUtils.loadAnimation(getContext(),R.anim.jump_from_down_without_alpha);
        resend = (Button) errorMessage.findViewById(R.id.category_resend_butt);
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                errorMessage.setVisibility(View.GONE);
                CategoryTask categoryTask = new CategoryTask();
                categoryTask.execute();
            }
        });
        avi.show();
        CategoryTask categoryTask = new CategoryTask();
        categoryTask.execute();
        TextView title = (TextView) getActivity().findViewById(R.id.title_tour);
        title.setText("Направление");
        return view;
    }

    private void updateUI(final List<InformExcursion> excursions){
        mCategory = findExcurWithCity();
        mAdapter = new ViewAdapter(mCategory,getContext());
        mAdapter.setOnItemListener(new ViewAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View itemView, int position) {
                Bundle save = new Bundle();
                save.putString("pack_exc",mCategory.get(position).getTittle());
                save.putString("id",mCategory.get(position).getId());
                if(mListener!=null){
                    mListener.onClicked(save);
                }
            }
        });
        categoryView.setAdapter(mAdapter);
    }

    private List<InformExcursion> findExcurWithCity(){
        mCategory = new ArrayList<>();
        for (InformExcursion inf: excursions ) {
            if(!inf.getTittle().equalsIgnoreCase(GlobalPreferences.getPrefUserCity(getContext()))) {
                mCategory.add(inf);
            }
        }
        return mCategory;
    }

    public void updateAdapter(){
        mCategory = findExcurWithCity();
        mAdapter.updateCity(mCategory);
        mAdapter.notifyDataSetChanged();
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
                    Log.i("Myapp", "Count responce json category " + categoryJsonArray.length());
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
                errorMessage.setVisibility(View.VISIBLE);
                errorMessage.setAnimation(jumpOn);
                categoryView.setVisibility(View.GONE);
            }else {
                if(errorMessage.getVisibility() != View.GONE) {
                    errorMessage.setVisibility(View.GONE);
                }
                avi.hide();
                categoryView.setVisibility(View.VISIBLE);
                categoryView.setClickable(true);
                excursions = categoryList;
                updateUI(categoryList);
            }
        }
    }
}
