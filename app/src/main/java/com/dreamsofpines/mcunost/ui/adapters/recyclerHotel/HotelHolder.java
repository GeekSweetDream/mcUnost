package com.dreamsofpines.mcunost.ui.adapters.recyclerHotel;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.network.api.Constans;
import com.squareup.picasso.Picasso;

import net.igenius.customcheckbox.CustomCheckBox;

/**
 * Created by ThePupsick on 24.02.2018.
 */

public class HotelHolder extends RecyclerView.ViewHolder {

    private CustomCheckBox box;
    private ImageView img;
    private TextView txt;


    public HotelHolder(View itemView) {
        super(itemView);
        img = (ImageView) itemView.findViewById(R.id.hotel_img);
        box = (CustomCheckBox) itemView.findViewById(R.id.hotel_box);
        txt = (TextView) itemView.findViewById(R.id.hotel_txt);
    }
    public void bindHolder(Context context,boolean isChecked, String name,String path_img,final int position){
        box.setChecked(isChecked);
        txt.setText(name);
        box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                box.setChecked(!box.isChecked());
                HotelAdapter.boxListener.onClicked(itemView,position);
            }
        });
        Picasso.with(context).load(Constans.URL.DOWNLOAD.GET_IMG+path_img).into(img);
    }
}
