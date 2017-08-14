package com.dreamsofpines.mcunost.ui.activities;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.ui.adapters.SimplePagerAdapter;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

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
