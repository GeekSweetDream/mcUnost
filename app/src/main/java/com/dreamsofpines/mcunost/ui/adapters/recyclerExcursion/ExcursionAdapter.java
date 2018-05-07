package com.dreamsofpines.mcunost.ui.adapters.recyclerExcursion;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.storage.models.Excursion;

import java.util.List;

/**
 * Created by ThePupsick on 24.02.2018.
 */

public class ExcursionAdapter extends RecyclerView.Adapter<ExcursionHolder>{

    private List<Excursion> excursionList;
    private Context mContext;
    private View view;
    private int cur, max;

    public static OnClickSwitchListener boxListener;

    public interface OnClickSwitchListener{
        void onClicked(boolean add, int position);
    }

    public void setOnClickSwitchListener(OnClickSwitchListener listener){
        this.boxListener = listener;
    }

    public ExcursionAdapter(){};

    public int getCur() {
        return cur;
    }

    public void setCur(int cur) {
        this.cur = cur;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setContext(Context context){
        this.mContext = context;
    }

    @Override
    public ExcursionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.item_exc_rec,parent,false);
        return new ExcursionHolder(view);
    }

    @Override
    public void onBindViewHolder(ExcursionHolder holder, int position) {
        Excursion excursion = excursionList.get(position);
        holder.bindHolder(mContext,view,excursion.isChecked(),excursion.getName(),position);
    }

    @Override
    public int getItemCount() {
        return excursionList.size();
    }

    public void setExcursionList(List<Excursion> excursionList) {
        this.excursionList = excursionList;
    }



}
