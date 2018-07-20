package com.dreamsofpines.mcunost.ui.customView;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.ui.fragments.LoginRegistrationFragment;


public class ViewLoginRegistration extends FrameLayout {

    private FragmentManager mFragmentManager;
    private LoginRegistrationFragment mLoginRegistrationFragment;
    private final String tag = "login";
    private boolean hide = false;
    private LoginRegistrationFragment.OnClickAlphaListener successRegisterListener; // для того, чтобы пробросить наружу

    public ViewLoginRegistration(@NonNull Context context) {
        super(context);
        init(context);
    }

    public ViewLoginRegistration(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ViewLoginRegistration(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        inflate(context, R.layout.reg_log_frame,this);
        setVisibility(GONE);
    }

    public void show(){
        if(mLoginRegistrationFragment == null){
            mLoginRegistrationFragment = new LoginRegistrationFragment();
            mLoginRegistrationFragment.setListener((success)-> {
                hide = true;
                mFragmentManager.popBackStack();
                if(successRegisterListener != null) successRegisterListener.onClick(success);
            });
        }
        mFragmentManager.addOnBackStackChangedListener(()-> setVisibility(hide?GONE:VISIBLE));
        if(mFragmentManager != null){
            setVisibility(VISIBLE);
            hide = false;
            mFragmentManager.beginTransaction()
                    .add(R.id.scene,mLoginRegistrationFragment)
                    .addToBackStack(tag)
                    .commit();
        }
        new Handler().postDelayed(()-> mLoginRegistrationFragment.show(0),200);
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        mFragmentManager = fragmentManager;
    }

    public void setSuccessRegisterListener(LoginRegistrationFragment.OnClickAlphaListener successRegisterListener) {
        this.successRegisterListener = successRegisterListener;
    }
}
