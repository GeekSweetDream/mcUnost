package com.dreamsofpines.mcunost.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.storage.help.menu.InformExcursion;
import com.dreamsofpines.mcunost.ui.activities.CategoriesActivity;

/**
 * Created by ThePupsick on 04.08.17.
 */

public class ViewHolder extends RecyclerView.ViewHolder {

    public TextView mTittle;
    public InformExcursion mInformExcursion;

    public ViewHolder(final View itemView) {
        super(itemView);
        mTittle = (TextView) itemView.findViewById(R.id.tittle_category);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ViewAdapter.listener != null)
                    ViewAdapter.listener.onItemClick(itemView,getLayoutPosition());
            }
        });
    }

    public void bindExcursion(InformExcursion inf){
        mInformExcursion = inf;
        mTittle.setText(mInformExcursion.getTittle());
    }
}
