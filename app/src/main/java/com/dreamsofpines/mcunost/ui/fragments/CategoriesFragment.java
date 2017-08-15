package com.dreamsofpines.mcunost.ui.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.database.MyDataBase;
import com.dreamsofpines.mcunost.data.storage.help.menu.InformExcursion;
import com.dreamsofpines.mcunost.ui.adapters.ViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThePupsick on 04.08.17.
 */

public class CategoriesFragment extends Fragment {

    private RecyclerView categoryView;
    private ViewAdapter mAdapter;
    private MyDataBase db;
    public static OnClickRecyclerListener mListener;

    public interface OnClickRecyclerListener{
        void onClicked(Bundle bundle);
    }

    public static void setOnClickRecyclerListener(OnClickRecyclerListener listener){
        CategoriesFragment.mListener = listener;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.fragment_categories,container,false);
        categoryView = (RecyclerView) view.findViewById(R.id.category_recycler_view);
        categoryView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        TextView title = (TextView) getActivity().findViewById(R.id.title_tour);
        title.setText("Направление");
        return view;
    }

    private void updateUI(){
        db = new MyDataBase(getActivity());
        final List<InformExcursion> excursions = db.getCategories();
        mAdapter = new ViewAdapter(excursions);
        mAdapter.setActivity(getActivity());
        mAdapter.setOnItemListener(new ViewAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View itemView, int position) {
                Bundle save = new Bundle();
                save.putString("pack_exc",excursions.get(position).getTittle());
                if(mListener!=null){
                    mListener.onClicked(save);
                }
            }
        });
        categoryView.setAdapter(mAdapter);
    }

}
