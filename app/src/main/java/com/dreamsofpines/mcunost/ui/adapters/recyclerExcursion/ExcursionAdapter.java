package com.dreamsofpines.mcunost.ui.adapters.recyclerExcursion;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.storage.models.Excursion;
import com.dreamsofpines.mcunost.ui.customView.MyImageView;
import com.dreamsofpines.mcunost.ui.customView.ViewTextWithCheckbox;

import java.util.List;

/**
 * Created by ThePupsick on 24.02.2018.
 */

public class ExcursionAdapter extends RecyclerView.Adapter{

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
        view = LayoutInflater.from(mContext).inflate(R.layout.item_exc_rec,parent,false);
        return new ExcursionHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Excursion excursion = excursionList.get(position);
        ((ExcursionHolder) holder).view.setTitle(excursion.getName());
        ((ExcursionHolder) holder).view.setCheckBox(excursion.isChecked());
        ((ExcursionHolder) holder).view.setOnClickCheckBoxListener((View view, boolean on)->{
            boxListener.onClicked(on,position);
        });
    }

    @Override
    public int getItemCount() {
        return excursionList.size();
    }

    public void setExcursionList(List<Excursion> excursionList) {
        this.excursionList = excursionList;
    }

    public class ExcursionHolder extends RecyclerView.ViewHolder {

        public ViewTextWithCheckbox view;

        public ExcursionHolder(View itemView) {
            super(itemView);
            view  = (ViewTextWithCheckbox) itemView.findViewById(R.id.txt_exc_name);
        }
    }




}
