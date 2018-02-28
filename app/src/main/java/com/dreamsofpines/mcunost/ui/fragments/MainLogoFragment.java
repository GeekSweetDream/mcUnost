package com.dreamsofpines.mcunost.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    private ImageView img;
    private FragmentManager fm;
    private Context context;

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
        img = (ImageView) view.findViewById(R.id.img_main_logo);
        Picasso.with(context).load("file:///android_asset/main.jpg").into(img);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConstructorFragment fr = new ConstructorFragment();
                fr.setFragmentManager(fm);
                fm.beginTransaction()
                    .add(R.id.frame_layout,fr)
                    .addToBackStack(null)
                    .commit();

            }
        });

        return view;
    }


}
