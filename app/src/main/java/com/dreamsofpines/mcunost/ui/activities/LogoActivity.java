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
import android.widget.ViewSwitcher;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.ui.fragments.ConstructorFragment;
import com.squareup.picasso.Picasso;

public class LogoActivity extends AppCompatActivity {

    private Button mButton;
    private ImageView img1,img2,img3;
    private ImageSwitcher mImageSwitcher;
    private boolean finish;
    private int position;
    private int[] idResImg = {R.drawable.main,R.drawable.moscow,R.drawable.kazan};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar_FullScreen);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main_logo);
        mButton = (Button) findViewById(R.id.rel_main_logo);
//        img1 = (ImageView) findViewById(R.id.img_main_logo1);
//        img2 = (ImageView) findViewById(R.id.img_main_logo2);
//        img3 = (ImageView) findViewById(R.id.img_main_logo3);
        mImageSwitcher = (ImageSwitcher) findViewById(R.id.main_switcher);
//        Picasso.with(getBaseContext()).load("file:///android_asset/main.jpg").into(img1);
//        Picasso.with(getBaseContext()).load("file:///android_asset/moscow.jpg").into(img2);
//        Picasso.with(getBaseContext()).load("file:///android_asset/kazan.jpg").into(img3);
        Animation inAnimation = new AlphaAnimation(0, 1);
        inAnimation.setDuration(2000);
        Animation outAnimation = new AlphaAnimation(1, 0);
        outAnimation.setDuration(2000);
        mImageSwitcher.setInAnimation(inAnimation);
        mImageSwitcher.setOutAnimation(outAnimation);
        mImageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getApplicationContext());
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setLayoutParams(new
                        ImageSwitcher.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                imageView.setBackgroundColor(0xFF000000);
                return imageView;
            }
        });
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish = true;
                Intent intent = new Intent(LogoActivity.this, CategoriesActivity.class);
                startActivity(intent);
            }
        });
        finish = false;
        position = 0;
        mImageSwitcher.setImageResource(idResImg[position]);
        changePicture();
    }


    private void changePicture(){
        if(!finish) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    setPositionNext();
                    mImageSwitcher.setImageResource(idResImg[position]);
                    changePicture();
                }
            },7000);
        }
    }

    public void setPositionNext() {
        position++;
        if (position > idResImg.length - 1) {
            position = 0;
        }
    }

}
