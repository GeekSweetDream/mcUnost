package com.dreamsofpines.mcunost.ui.activities;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.ui.adapters.SimplePagerAdapter;

import static android.R.attr.start;

public class LogoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar_FullScreen);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        ViewPager viewPager = (ViewPager) findViewById(R.id.vpager);
        if(viewPager != null)
            viewPager.setAdapter(new SimplePagerAdapter(this));

        TextView hide = (TextView) findViewById(R.id.skiptxt);
        hide.setOnTouchListener(new View.OnTouchListener() {                            // Можно ли вынести в отдельный класс?
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent = new Intent(LogoActivity.this, CategoriesActivity.class);
                startActivity(intent);
                return false;
            }
        });

        TextView logIn = (TextView) findViewById(R.id.entertxt);
        logIn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent intent = new Intent(LogoActivity.this, LogInActivity.class);
                startActivity(intent);
                return false;
            }
        });

    }
}
