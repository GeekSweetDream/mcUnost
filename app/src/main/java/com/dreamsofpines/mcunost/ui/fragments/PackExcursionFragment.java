package com.dreamsofpines.mcunost.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.database.MyDataBase;
import com.dreamsofpines.mcunost.data.storage.help.menu.InformExcursion;
import com.dreamsofpines.mcunost.data.storage.preference.GlobalPreferences;
import com.dreamsofpines.mcunost.ui.adapters.recyclerExcursion.ExcursionAdapter;

import java.util.List;

/**
 * Created by ThePupsick on 05.08.17.
 */

public class PackExcursionFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private String packExcur;
    private ExcursionAdapter mAdapter;
    private TextView emptyRecycle;

    private MyDataBase db;
    public static OnClickRecyclerListener mListener;

    public interface OnClickRecyclerListener{
        void onClicked(Bundle bundle);
    }

    public void setOnClickListener(OnClickRecyclerListener listener){
        this.mListener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        packExcur = getArguments().getString("pack_exc");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pack_exc,container,false);
        bindActivity(view);
        mRecyclerView.setVisibility(View.INVISIBLE);
        emptyRecycle.setVisibility(View.VISIBLE);
        updateUI(packExcur);
        TextView title = (TextView) getActivity().findViewById(R.id.title_tour);
        title.setText("Туры");
        return view;
    }

    private void bindActivity(View view){
        mRecyclerView = (RecyclerView) view.findViewById(R.id.pack_exc_recycler);
        emptyRecycle = (TextView) view.findViewById(R.id.empty_pack_exc);
    }


    private void updateUI(String packExcur){
        db = new MyDataBase(getActivity());
        final List<InformExcursion> listExcur = db.getPackExcursion(packExcur, GlobalPreferences.getPrefUserCity(getContext()));
        Log.i("Myapp","Размер Массива: " + listExcur.size());
        if(listExcur.size()!=0) {
            mRecyclerView.setVisibility(View.VISIBLE);
            emptyRecycle.setVisibility(View.INVISIBLE);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mAdapter = new ExcursionAdapter(listExcur);
            mAdapter.setActivity(getActivity());
            mAdapter.setOnTouchListener(new ExcursionAdapter.OnItemTouchListener() {
                @Override
                public void onTouched(View itemView, int position) {
                    Bundle save = new Bundle();
                    save.putString("pack_exc", listExcur.get(position).getTittle());
                    save.putString("sh_desc", listExcur.get(position).getShortDesc());
                    save.putString("description", listExcur.get(position).getDescription());
                    save.putString("cost", listExcur.get(position).getCount());
                    save.putString("day", listExcur.get(position).getDay());
                    save.putString("img", listExcur.get(position).getNameImage());
                    if (mListener != null)
                        mListener.onClicked(save);
                }
            });
            mRecyclerView.setAdapter(mAdapter);
        }
    }
}
