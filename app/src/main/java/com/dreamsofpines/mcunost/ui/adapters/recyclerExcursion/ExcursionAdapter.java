package com.dreamsofpines.mcunost.ui.adapters.recyclerExcursion;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.storage.help.menu.InformExcursion;

import java.util.List;

/**
 * Created by ThePupsick on 05.08.17.
 */

public class ExcursionAdapter extends RecyclerView.Adapter<ExcursionHolder> {

    private Activity mActivity;
    private List<InformExcursion> mExcursionList;
    public static OnItemTouchListener mListener;

    public interface OnItemTouchListener{
        void onTouched(View itemView,int position);
    }

    public void setOnTouchListener(OnItemTouchListener listener){
        this.mListener = listener;
    }

    public ExcursionAdapter(List<InformExcursion> excursionList) {
        mExcursionList = excursionList;
    }

    public void setActivity(Activity activity) {
        mActivity = activity;
    }

    @Override
    public ExcursionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mActivity);
        View view = inflater.inflate(R.layout.fragment_excursion,parent,false);
        return new ExcursionHolder(view);
    }

    @Override
    public void onBindViewHolder(ExcursionHolder holder, int position) {
        InformExcursion inf = mExcursionList.get(position);
        holder.bindExcursion(inf);
    }


    @Override
    public int getItemCount() {
        return mExcursionList.size();
    }
}
