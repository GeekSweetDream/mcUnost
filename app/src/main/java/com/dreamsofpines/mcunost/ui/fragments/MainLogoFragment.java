package com.dreamsofpines.mcunost.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.network.api.Constans;
import com.squareup.picasso.Picasso;

/**
 * Created by ThePupsick on 24.02.2018.
 */

public class MainLogoFragment extends Fragment {

    private View view;
    private Button mButton;
    private ImageView img1,img2;
    private FragmentManager fm;
    private Context context;
    private ImageSwitcher mImageSwitcher;
    private boolean finish;

    public void setFragmentManager(FragmentManager fm){
        this.fm = fm;
    }
    public void setContext(Context context){
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.fragment_main_logo,container,false);
        mButton = (Button) view.findViewById(R.id.rel_main_logo);
        mImageSwitcher= (ImageSwitcher) view.findViewById(R.id.main_switcher);
        Animation inAnimation = new AlphaAnimation(0, 1);
        inAnimation.setDuration(2000);
        Animation outAnimation = new AlphaAnimation(1, 0);
        outAnimation.setDuration(2000);
        mImageSwitcher.setInAnimation(inAnimation);
        mImageSwitcher.setOutAnimation(outAnimation);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish = true;
                ConstructorFragment fr = new ConstructorFragment();
                fr.setFragmentManager(fm);
                fm.beginTransaction()
                    .add(R.id.frame_layout,fr)
                    .addToBackStack(null)
                    .commit();

            }
        });
        finish = false;
        changePicture();
        return view;
    }

    private void changePicture(){
        if(!finish) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mImageSwitcher.showNext();
                }
            },3000);
        }
    }




}
