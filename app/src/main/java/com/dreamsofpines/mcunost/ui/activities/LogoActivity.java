package com.dreamsofpines.mcunost.ui.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.storage.preference.GlobalPreferences;
import com.dreamsofpines.mcunost.ui.customView.ViewLoginRegistration;

public class LogoActivity extends AppCompatActivity {

    private Button mButtonEnter;
    private Button mButtonLogin;
    private ImageSwitcher mImageSwitcher;
    private ViewLoginRegistration mViewLoginRegistration;
    private boolean finish;
    private int position;
    private int[] idResImg = {R.drawable.main,R.drawable.moscow,R.drawable.kazan};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar_FullScreen);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main_logo);
        bindView();
        setListeners();
        settingImageSwitcher();
        mButtonLogin.setVisibility(GlobalPreferences.getPrefAddUser(getApplicationContext()) == 1 ?
                View.GONE : View.VISIBLE);
        finish = false;
        position = 0;
        mImageSwitcher.setImageResource(idResImg[position]);
        changePicture();
    }

    private void bindView(){
        mButtonEnter = (Button) findViewById(R.id.rel_main_logo);
        mImageSwitcher = (ImageSwitcher) findViewById(R.id.main_switcher);
        mViewLoginRegistration = (ViewLoginRegistration) findViewById(R.id.login_view);
        mButtonLogin = (Button) findViewById(R.id.login_btn);
    }

    private void settingImageSwitcher(){
        mImageSwitcher.setInAnimation(getAlphaAnimation(0,1));
        mImageSwitcher.setOutAnimation(getAlphaAnimation(1,0));
        mImageSwitcher.setFactory(()->{
            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new
                    ImageSwitcher.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            imageView.setBackgroundColor(0xFF000000);
            return imageView;
        });
    }

    private void setListeners(){
        mButtonEnter.setOnClickListener((view)->{
            finish = true;
            Intent intent = new Intent(LogoActivity.this, MainActivityWithTabs.class);
            startActivity(intent);
        });

        mButtonLogin.setOnClickListener((view)->{
            mViewLoginRegistration.setFragmentManager(getSupportFragmentManager());
            mViewLoginRegistration.show();
        });

    }

    private Animation getAlphaAnimation(int before, int after){
        Animation animation = new AlphaAnimation(before, after);
        animation.setDuration(2000);
        return animation;
    }

    private void changePicture(){
        if(!finish) {
            new Handler().postDelayed(()->{
                    setPositionNext();
                    mImageSwitcher.setImageResource(idResImg[position]);
                    changePicture();},5000);
        }
    }

    public void setPositionNext() {
        position++;
        if (position > idResImg.length - 1) {
            position = 0;
        }
    }

}
