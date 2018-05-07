package com.dreamsofpines.mcunost.ui.fragments;

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
import com.dreamsofpines.mcunost.Parser.StringParser;
import com.dreamsofpines.mcunost.data.network.api.RequestSender;
import com.dreamsofpines.mcunost.data.storage.models.InformExcursion;
import com.dreamsofpines.mcunost.data.storage.preference.GlobalPreferences;
import com.dreamsofpines.mcunost.ui.adapters.recyclerex.ExcursionAdapter;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by ThePupsick on 05.08.17.
 */

public class PackExcursionFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private String packExcur,idCategory,idRegion;
    private ExcursionAdapter mAdapter;
    private TextView emptyRecycle;
    private AVLoadingIndicatorView avl;
    private View errorMessage;
    private Button resend;
    private Animation jumpOn, jumpOff;
    public OnClickRecyclerListener mListener;

    public interface OnClickRecyclerListener{
        void onClicked(Bundle bundle);
    }

    public void setOnClickListener(OnClickRecyclerListener listener){
        this.mListener = listener;
    }
    
    @Override
    public void onResume() {
        packExcur = getArguments().getString("pack_exc");
        idCategory = getArguments().getString("id");
        idRegion = ""+StringParser.convertCityInToInteger(GlobalPreferences.getPrefUserCity(getContext()));
        PackExcurTask task = new PackExcurTask();
        task.execute();
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pack_exc,container,false);
        bindActivity(view);
        mRecyclerView.setVisibility(View.GONE);
        emptyRecycle.setVisibility(View.GONE);
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avl.show();
                PackExcurTask task = new PackExcurTask();
                task.execute();
            }
        });
        avl.show();
        TextView title = (TextView) getActivity().findViewById(R.id.title_tour);
        title.setText("Туры");
        return view;
    }



    private void bindActivity(View view){
        mRecyclerView = (RecyclerView) view.findViewById(R.id.pack_exc_recycler);
        emptyRecycle = (TextView) view.findViewById(R.id.empty_pack_exc);
        errorMessage = (View) view.findViewById(R.id.error_message);
        resend = (Button) errorMessage.findViewById(R.id.category_resend_butt);
        avl = (AVLoadingIndicatorView) view.findViewById(R.id.category_indicator);
    }

    private void updateUI(List<InformExcursion> pack ){
        if(pack.size()!=0) {
            mRecyclerView.setVisibility(View.VISIBLE);
            emptyRecycle.setVisibility(View.INVISIBLE);
            if (null == mRecyclerView.getLayoutManager()) {
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            }
            if (null == mAdapter) {
                mAdapter = new ExcursionAdapter(pack);
                mAdapter.setActivity(getActivity());
            }
            setListenerAdapter(pack);
            mAdapter.setExcursionList(pack);
            if (null == mRecyclerView.getAdapter()) {
                mRecyclerView.setAdapter(mAdapter);
            }
            mAdapter.notifyDataSetChanged();
        }else{
            mRecyclerView.setVisibility(View.GONE);
            emptyRecycle.setVisibility(View.VISIBLE);
        }
    }


    // useless
//    public void updateAdapter() {
//        idRegion = ""+StringParser.convertCityInToInteger(GlobalPreferences.getPrefUserCity(getContext()));
//        avl.show();
//        PackExcurTask packExcurTask = new PackExcurTask();
//        packExcurTask.execute();
//    }


    private void setListenerAdapter(final List<InformExcursion> listExcur) {
        mAdapter.setOnTouchListener(new ExcursionAdapter.OnItemTouchListener() {
            @Override
            public void onTouched(View itemView, int position) {
                Bundle save = new Bundle();
                save.putString("idPack",listExcur.get(position).getId());
                save.putString("pack_exc", listExcur.get(position).getTittle());
                save.putString("sh_desc", listExcur.get(position).getShortDesc());
                save.putString("description", listExcur.get(position).getDescription());
                save.putString("cost", listExcur.get(position).getCount());
                save.putString("day", listExcur.get(position).getDay());
                save.putString("img", listExcur.get(position).getNameImage());
                save.putString("inTour",listExcur.get(position).getInTour());
                save.putString("addService",listExcur.get(position).getAddService());
                Log.i("dd",listExcur.get(position).getId()+" " +listExcur.get(position).getTittle());
                if (mListener != null)
                    mListener.onClicked(save);
            }
        });
    }

    /*
          Get package tour from web-site 
     */

    private class PackExcurTask extends AsyncTask<Void, Void, Boolean> {

        private List<InformExcursion> PackExcurList;

        @Override
        protected Boolean doInBackground(Void... voids) {
            Boolean success = true;
            PackExcurList = new ArrayList<>();
            String response = RequestSender.GetPackExcur(idRegion,idCategory);
            try {
                JSONObject js = new JSONObject(response);
                String resultResponce = js.getString("result");
                if (resultResponce.equalsIgnoreCase("success")) {
                    JSONArray PackExcurJsonArray = js.getJSONArray("data");
                    Log.i("Myapp", "Count response json category " + PackExcurJsonArray.length());
                    int length = PackExcurJsonArray.length();
                    for(int i = 0; i < length; ++i) {
                        JSONObject pack = PackExcurJsonArray.getJSONObject(i);
                        PackExcurList.add(new InformExcursion(
                                pack.getString("id"),
                                pack.getString("name"),
                                pack.getString("cost"),
                                pack.getString("quantityDay"),
                                pack.getString("description"),
                                pack.getString("shortDescription"),
                                pack.getString("pathImage"),
                                pack.getString("inTour"),
                                pack.getString("addService"))
                        );

                    }
                }else{
                    success = false;
                    Log.i("PackExcur","Error answer! Error message: "+js.getString("mess"));
                    /* error answer (add log.i())*/
                }
            } catch (JSONException e) {
                Log.i("PackExcur:","JsonExeption from parsing json pack_excur! Error message:"+e.getMessage());
                success = false;
            } catch (Exception e){
                Log.i("PackExcur:"," Error with answer! Error message:"+e.getMessage());
                packExcur = null;
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
                jumpOn = AnimationUtils.loadAnimation(getContext(),R.anim.jump_from_down_without_alpha);
                errorMessage.setAnimation(jumpOn);
                mRecyclerView.setVisibility(View.GONE);
            }else{
                avl.hide();
                errorMessage.setVisibility(View.GONE);
                Collections.sort(PackExcurList, new Comparator<InformExcursion>() {
                    @Override
                    public int compare(InformExcursion t1, InformExcursion t2) {
                        return Integer.valueOf(t1.getDay()).compareTo(Integer.valueOf(t2.getDay()));
                    }
                });
                int d = 0;
                for(int i =0; i<PackExcurList.size();++i){
                    InformExcursion obj = PackExcurList.get(i);
                    int dd = Integer.valueOf(obj.getDay());
                    if(d<dd){
                        d = dd;
                        PackExcurList.add(i,new InformExcursion(""+d));
                    }

                }
                for (InformExcursion obj: PackExcurList) {
                }

                updateUI(PackExcurList);
            }
        }
    }


}