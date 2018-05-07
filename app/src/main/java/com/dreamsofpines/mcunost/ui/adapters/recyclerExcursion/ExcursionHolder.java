package com.dreamsofpines.mcunost.ui.adapters.recyclerExcursion;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.network.api.Constans;
import com.dreamsofpines.mcunost.ui.animation.ResizableWidthAnimation;
import com.dreamsofpines.mcunost.ui.customView.MyImageView;
import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.squareup.picasso.Picasso;

import net.igenius.customcheckbox.CustomCheckBox;

import static android.R.attr.max;
import static com.dreamsofpines.mcunost.R.id.back;

/**
 * Created by ThePupsick on 24.02.2018.
 */

public class ExcursionHolder extends RecyclerView.ViewHolder {

    public static SwitchCompat box;
    private ImageView img;
    private TextView txt;
    private MyImageView back;
    private Animation animation;


    public ExcursionHolder(View itemView) {
        super(itemView);
//        img = (ImageView) itemView.findViewById(R.id.hotel_img);
        txt  = (TextView) itemView.findViewById(R.id.txt_exc_name);
        back = (MyImageView) itemView.findViewById(R.id.back);
    }
    public void bindHolder(final Context context, View item, final boolean isChecked, String name, final int position){
        txt.setText(name);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        back.getLayoutParams().width = 2*metrics.widthPixels/3;
        back.requestLayout();
        if(isChecked) show(context);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!back.isFull()){
                    show(context);
                    ExcursionAdapter.boxListener.onClicked(true, position);
                }else{
                    hide(context);
                    ExcursionAdapter.boxListener.onClicked(false, position);
                }
            }
        });

//        Picasso.with(context).load(Constans.URL.DOWNLOAD.GET_IMG+path_img).into(img);
    }

    private void show(Context context){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        ResizableWidthAnimation resizableWidthAnimation = new ResizableWidthAnimation(back, metrics.widthPixels,context);
        resizableWidthAnimation.setDuration(100);
        back.startAnimation(resizableWidthAnimation);
        back.setFull(true);
    }
    private void hide(Context context){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        ResizableWidthAnimation resizableWidthAnimation = new ResizableWidthAnimation(back, 2*metrics.widthPixels/3,context);
        resizableWidthAnimation.setDuration(100);
        back.startAnimation(resizableWidthAnimation);
        back.setFull(false);
    }

}
