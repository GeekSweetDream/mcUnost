package com.dreamsofpines.mcunost.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.dreamsofpines.mcunost.R;

/**
 * Created by ThePupsick on 31.07.17.
 */

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Button mLogUp = (Button) findViewById(R.id.butt_log_up);
        final View mShLogUp = (View) findViewById(R.id.sh_log_up);
        mLogUp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        mLogUp.setBackgroundColor(getResources().getColor(R.color.md_yellow_600));
                        break;
                    case MotionEvent.ACTION_UP:
                        mLogUp.setBackgroundColor(getResources().getColor(R.color.md_blue_600));
                        break;
                }

                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
}
