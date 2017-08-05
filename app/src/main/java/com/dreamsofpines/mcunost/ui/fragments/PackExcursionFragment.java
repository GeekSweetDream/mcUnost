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
import android.widget.Toast;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.database.MyDataBase;
import com.dreamsofpines.mcunost.data.storage.help.menu.InformExcursion;
import com.dreamsofpines.mcunost.ui.adapters.ExcursionAdapter;

import java.util.List;

import static android.R.id.list;

/**
 * Created by ThePupsick on 05.08.17.
 */

public class PackExcursionFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private String packExcur;
    private ExcursionAdapter mAdapter;
    private MyDataBase db;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        packExcur = getArguments().getString("pack_exc");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pack_exc,container,false);
        if(savedInstanceState != null) {
        }
        mRecyclerView = (RecyclerView) view.findViewById(R.id.pack_exc_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI(packExcur);
        return view;
    }

    private void updateUI(String packExcur){
        db = new MyDataBase(getActivity());
        final List<InformExcursion> listExcur = db.getPackExcursion(packExcur);
        Log.i("Myapp","Размер Массива: " + listExcur.size());
        mAdapter = new ExcursionAdapter(listExcur);
        mAdapter.setActivity(getActivity());
        mRecyclerView.setAdapter(mAdapter);
    }
}
