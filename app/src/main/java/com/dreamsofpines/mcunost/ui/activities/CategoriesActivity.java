package com.dreamsofpines.mcunost.ui.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.database.MyDataBase;
import com.dreamsofpines.mcunost.data.storage.help.menu.InformExcursion;
import com.dreamsofpines.mcunost.data.storage.help.menu.LeftMenu;
import com.dreamsofpines.mcunost.ui.adapters.ViewAdapter;
import com.dreamsofpines.mcunost.ui.fragments.CategoriesFragment;
import com.dreamsofpines.mcunost.ui.fragments.InformExcursionFragment;
import com.dreamsofpines.mcunost.ui.fragments.PackExcursionFragment;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.fragment;
import static android.R.attr.left;

/**
 * Created by ThePupsick on 15.07.17.
 */

public class CategoriesActivity extends AppCompatActivity {

    private final FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        final LeftMenu leftMenu = new LeftMenu(this);
        leftMenu.build(this,0);
        CategoriesFragment fragment = (CategoriesFragment) fm.findFragmentById(R.id.frame_layout);
        if(fragment == null){
            fragment = new CategoriesFragment();
            fm.beginTransaction()
                    .add(R.id.frame_layout,fragment)
                    .commit();
        }
        fragment.setOnClickRecyclerListener(new CategoriesFragment.OnClickRecyclerListener(){
            @Override
            public void onClicked(Bundle bundle) {
                    changeFragmentPackExc(bundle);
                }
        });
        Button button = (Button) findViewById(R.id.button_menu);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                leftMenu.openMenu();
            }
        });
    }

    public void changeFragmentPackExc(Bundle bundle){
        PackExcursionFragment fr = new PackExcursionFragment();
        fr.setArguments(bundle);
        fr.setOnClickListener(new PackExcursionFragment.OnClickRecyclerListener(){
            @Override
            public void onClicked(Bundle bundle) {
                changeFragmentInfExc(bundle);
            }
        });
        fm.beginTransaction()
                .replace(R.id.frame_layout,fr)
                .addToBackStack(null)
                .commit();

    }

    public void changeFragmentInfExc(Bundle bundle){
        InformExcursionFragment fr = new InformExcursionFragment();
        fr.setArguments(bundle);
        fm.beginTransaction()
                .replace(R.id.frame_layout,fr)
                .addToBackStack(null)
                .commit();

    }

}
