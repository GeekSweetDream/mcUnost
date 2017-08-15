package com.dreamsofpines.mcunost.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.storage.preference.GlobalPreferences;

/**
 * Created by ThePupsick on 31.07.17.
 */

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        final Activity act = this;
        final Button mLogUp = (Button) findViewById(R.id.butt_log_up);
        final View mShLogUp = (View) findViewById(R.id.sh_log_up);
        // Вынести в отдельный класс?
        mLogUp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mLogUp.setBackgroundColor(getResources().getColor(R.color.md_yellow_600));
                        break;
                    case MotionEvent.ACTION_UP:
                        float finX = motionEvent.getX(), finY = motionEvent.getY();
                        mLogUp.setBackgroundColor(getResources().getColor(R.color.md_blue_600));
                        if(((finX > mLogUp.getX()) && (finX < (mLogUp.getX()+mLogUp.getWidth()) ) )
                                && ((finY > mLogUp.getX()) && (finY < (mLogUp.getY()+mLogUp.getHeight())))) {
                            if(addUserData(act)) {
                                mLogUp.setBackgroundColor(getResources().getColor(R.color.md_green_600));
                                Intent intent = new Intent(LogInActivity.this, CategoriesActivity.class);
                                startActivity(intent);
                            }
                        }
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

    // Изменить цвет editText, при пустых строках
    private boolean addUserData(Activity activity) {
        boolean fl = true;
//        EditText inpName = (EditText) findViewById(R.id.inputName);
//        EditText inpNumber = (EditText) findViewById(R.id.inputNumber);
//        if(inpName.getText().toString().equalsIgnoreCase("") || inpNumber.getText().toString().equalsIgnoreCase("")) {
//            fl = false;
//        }else{
//            GlobalPreferences.setPrefUserName(activity,inpName.getText().toString());
//            GlobalPreferences.setPrefUserNumber(activity,inpNumber.getText().toString());
//            GlobalPreferences.setPrefAddUser(activity);
//        }
        return fl;
    }
}
