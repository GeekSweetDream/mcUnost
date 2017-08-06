package com.dreamsofpines.mcunost.ui.activities;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
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
        Button hide = (Button) findViewById(R.id.skiptxt);
        hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogoActivity.this, CategoriesActivity.class);
                startActivity(intent);
            }
        });

    }
}
