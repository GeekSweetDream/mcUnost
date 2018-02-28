package com.dreamsofpines.mcunost.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.ui.fragments.ConstructorFragment;
import com.squareup.picasso.Picasso;

public class LogoActivity extends AppCompatActivity {

    private Button mButton;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar_FullScreen);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main_logo);
        mButton = (Button) findViewById(R.id.rel_main_logo);
        img = (ImageView) findViewById(R.id.img_main_logo);
        Picasso.with(getBaseContext()).load("file:///android_asset/main.jpg").into(img);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogoActivity.this, CategoriesActivity.class);
                startActivity(intent);
            }
        });

    }

}
