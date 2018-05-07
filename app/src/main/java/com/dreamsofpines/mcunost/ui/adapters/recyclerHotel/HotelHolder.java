package com.dreamsofpines.mcunost.ui.adapters.recyclerHotel;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.network.api.Constans;
import com.dreamsofpines.mcunost.ui.animation.ResizableWidthAnimation;
import com.dreamsofpines.mcunost.ui.customView.MyImageView;
import com.squareup.picasso.Picasso;

import net.igenius.customcheckbox.CustomCheckBox;

import static com.dreamsofpines.mcunost.R.id.back;
import static com.yandex.metrica.impl.q.a.r;

/**
 * Created by ThePupsick on 24.02.2018.
 */

public class HotelHolder extends RecyclerView.ViewHolder {

    private CustomCheckBox box;
    private TextView txt;
    private MyImageView img;

    public interface addHotelListener{
        boolean add(int positon);
    };
    private addHotelListener listener;
    public void setAddHotelListener(addHotelListener listener){
        this.listener = listener;
    }

    public HotelHolder(View itemView) {
        super(itemView);
        img = (MyImageView) itemView.findViewById(back);
//        box = (CustomCheckBox) itemView.findViewById(R.id.hotel_box);
        txt = (TextView) itemView.findViewById(R.id.hotel_txt);
    }
    public void bindHolder(final Context context, boolean isChecked, String name, String path_img, final int position){
        txt.setText(name);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        img.getLayoutParams().width = 2*metrics.widthPixels/3;
        img.requestLayout();
        if(isChecked && !img.isFull()) {
            show(context);
        }else if(!isChecked && img.isFull()){
            hide(context);
        }
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!img.isFull()) {
                    show(context);
                    HotelAdapter.boxListener.onClicked(true,position);
                }else{
                    hide(context);
                    HotelAdapter.boxListener.onClicked(false,position);
                }
            }
        });
//        box.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                box.setChecked(!box.isChecked());
//                HotelAdapter.boxListener.onClicked(itemView,position);
//            }
//        });
//        Picasso.with(context).load(Constans.URL.DOWNLOAD.GET_IMG+path_img).into(img);
    }

    private void show(Context context){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        ResizableWidthAnimation resizableWidthAnimation = new ResizableWidthAnimation(img, metrics.widthPixels,context);
        resizableWidthAnimation.setDuration(100);
        img.startAnimation(resizableWidthAnimation);
        img.setFull(true);
    }
    private void hide(Context context){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        ResizableWidthAnimation resizableWidthAnimation = new ResizableWidthAnimation(img, 2*metrics.widthPixels/3,context);
        resizableWidthAnimation.setDuration(100);
        img.startAnimation(resizableWidthAnimation);
        img.setFull(false);
    }


}
