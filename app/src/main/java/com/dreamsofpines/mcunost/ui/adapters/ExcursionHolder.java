package com.dreamsofpines.mcunost.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.storage.help.menu.InformExcursion;

/**
 * Created by ThePupsick on 05.08.17.
 */

public class ExcursionHolder extends RecyclerView.ViewHolder {
    public TextView title;
    public TextView cost;
    public InformExcursion mInformExcursion;

    public ExcursionHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.title_exc);
        cost = (TextView) itemView.findViewById(R.id.cost_exc);
    }

    public void bindExcursion(InformExcursion inf){
        mInformExcursion = inf;
        title.setText(mInformExcursion.getTittle());
        cost.setText(mInformExcursion.getCount()+" \u20BD");
    }
}
