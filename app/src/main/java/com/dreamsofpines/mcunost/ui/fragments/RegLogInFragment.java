package com.dreamsofpines.mcunost.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamsofpines.mcunost.R;

/**
 * Created by ThePupsick on 29.08.17.
 */

public class RegLogInFragment extends Fragment {

    private Button logIn,logUp;
    private FragmentManager fm;
    private RegistrFragment rF;
    private TextView tittle;
    private SettingFragment sF;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_ic,container,false);

        logIn = (Button) view.findViewById(R.id.butt_reg_logIn);
        logUp = (Button) view.findViewById(R.id.butt_reg_logUp);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getContext(),"Данный фукнционал будет добавлен позже! :)",Toast.LENGTH_LONG)
//                        .show();
            }
        });

        logUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null==rF){
                    rF = new RegistrFragment();
                }
                rF.setOnClickRegistrListener(new RegistrFragment.OnClickCancelListener() {
                    @Override
                    public void onClicked(boolean isLogUp) {
                        if(isLogUp) {
                            if (null==sF) {
                                sF = new SettingFragment();
                            }
                            fm.beginTransaction()
                                    .replace(R.id.frame_layout,sF)
                                    .commit();
                        }else{
                            fm.popBackStack();
                        }
                    }
                });
                Animation anim = AnimationUtils.loadAnimation(getContext(),R.anim.jump_from_down);
                fm.beginTransaction()
                        .setCustomAnimations(R.anim.jump_from_down,0)
                        .replace(R.id.frame_layout,rF)
                        .addToBackStack(null)
                        .commit();
            }
        });

        tittle = (TextView) getActivity().findViewById(R.id.title_tour);
        tittle.setText("Вход");
        return view;
    }

    public void setFm(FragmentManager fm) {
        this.fm = fm;
    }
}
