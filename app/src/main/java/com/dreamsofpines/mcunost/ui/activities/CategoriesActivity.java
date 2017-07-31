package com.dreamsofpines.mcunost.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.storage.help.menu.LeftMenu;

/**
 * Created by ThePupsick on 15.07.17.
 */

public class CategoriesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        LeftMenu leftMenu = new LeftMenu(this);
        leftMenu.build(this);
    }
}
