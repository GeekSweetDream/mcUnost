package com.dreamsofpines.mcunost.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.storage.help.menu.InformExcursion;
import com.dreamsofpines.mcunost.ui.activities.CategoriesActivity;
import com.squareup.picasso.Picasso;

/**
 * Created by ThePupsick on 04.08.17.
 */

public class ViewHolder extends RecyclerView.ViewHolder {

    public TextView mTittle;
    public ImageView mImageView;
    public InformExcursion mInformExcursion;
    private Context mContext;

    public ViewHolder(final View itemView) {
        super(itemView);
        mTittle = (TextView) itemView.findViewById(R.id.tittle_category);
        mImageView = (ImageView) itemView.findViewById(R.id.img_category);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ViewAdapter.listener != null)
                    ViewAdapter.listener.onItemClick(itemView,getLayoutPosition());
            }
        });
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public void bindExcursion(InformExcursion inf){
        mInformExcursion = inf;
        mTittle.setText(mInformExcursion.getTittle());
        Picasso.with(mContext).load("file:///android_asset/moscow_small.png").into(mImageView);
        //+mInformExcursion.getNameImage()
    }
}
