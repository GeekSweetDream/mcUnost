package com.dreamsofpines.mcunost.ui.adapters.recyclerExcursion;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.storage.help.menu.InformExcursion;
import com.squareup.picasso.Picasso;

/**
 * Created by ThePupsick on 05.08.17.
 */

public class ExcursionHolder extends RecyclerView.ViewHolder {
    public TextView title;
    public TextView cost;
    public ImageView mImageView;
    public InformExcursion mInformExcursion;
    private Context mContext;

    public void setContex(Context context) {
        mContext = context;
    }

    public ExcursionHolder(final View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.title_exc);
        cost = (TextView) itemView.findViewById(R.id.cost_exc);
        mImageView = (ImageView) itemView.findViewById(R.id.img_exc);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ExcursionAdapter.mListener != null)
                    ExcursionAdapter.mListener.onTouched(itemView,getLayoutPosition());
            }
        });
    }

    public void bindExcursion(InformExcursion inf){
        mInformExcursion = inf;
        title.setText(mInformExcursion.getTittle());
        cost.setText(mInformExcursion.getCount()+" \u20BD");
        Picasso.with(mContext).load("file:///android_asset/"+mInformExcursion.getNameImage()+".png").into(mImageView);
    }
}
