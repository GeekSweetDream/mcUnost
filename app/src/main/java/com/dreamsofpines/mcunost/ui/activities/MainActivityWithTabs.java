package com.dreamsofpines.mcunost.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.storage.mBarItem;
import com.dreamsofpines.mcunost.data.utils.FragmentQueueUtils;
import com.dreamsofpines.mcunost.data.storage.help.menu.BottomNavigationViewHelper;
import com.dreamsofpines.mcunost.ui.fragments.ChatsFragment;
import com.dreamsofpines.mcunost.ui.fragments.ConstructorFragment;
import com.dreamsofpines.mcunost.ui.fragments.LentaFragment;
import com.dreamsofpines.mcunost.ui.fragments.NewConstructorFragment;
import com.dreamsofpines.mcunost.ui.fragments.OrdersFragment;
import com.dreamsofpines.mcunost.ui.fragments.SettingFragment;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class MainActivityWithTabs extends AppCompatActivity{


    private FragmentManager mFragmentManager = getSupportFragmentManager();
    private Fragment prevFragment = ChatsFragment.getInstance();


    private BottomNavigationViewEx.OnNavigationItemSelectedListener  mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.navigation_lenta: {
                    loadFragment("lenta",LentaFragment.getInstance(mFragmentManager));
                    return true;
                }
                case R.id.navigation_chat:{
                    loadFragment("chats", ChatsFragment.getInstance());
                    return true;
                }
                case R.id.navigation_create_order:{
                    loadFragment("constructor",NewConstructorFragment.getInstance(mFragmentManager));
                    return true;
                }
                case R.id.navigation_orders:{
                    loadFragment("orders", OrdersFragment.getInstance(mFragmentManager));
                    return true;
                }
                case R.id.navigation_setting:{
                    loadFragment("setting", SettingFragment.getInstance());
                    return true;
                }
                default:{
                    return false;
                }
            }
        }
    };



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tabs);
        BottomNavigationViewEx bottomNavigationView = (BottomNavigationViewEx) findViewById(R.id.bottom_navigation_view);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        bottomNavigationView.setCurrentItem(2);
    }

    private void loadFragment(String tag,Fragment fragment){


        if(mFragmentManager.findFragmentByTag(tag) == null) {
            mFragmentManager.beginTransaction()
                    .add(R.id.frame_main,fragment,tag)
                    .hide(fragment)
                    .commit();
        }

//        if(isHaveOpenedFragment(tag)) {
//            mFragmentManager.beginTransaction()
//                    .hide(prevFragment)
//                    .commit();
//        }else{
            mFragmentManager.beginTransaction()
                    .hide(prevFragment)
                    .show(fragment)
                    .commit();
//        }

//        while(mFragmentManager.popBackStackImmediate());
        prevFragment = fragment;
//        showOpenedFragment(tag);
    }

    private boolean isHaveOpenedFragment(String tag){
        switch (tag){
            case "orders":{
                return OrdersFragment.getInstance(mFragmentManager)
                        .getCurrentOrderInforamtionFr() != null;
            }
            default:{
                return false;
            }
        }

    }

    private void showOpenedFragment(String tag){
        switch (tag){
            case "lenta":{
//                while(addFragments(FragmentQueueUtils.getValueLenta()));
                break;
            }
            case "constructor":{
//                while(addFragments(FragmentQueueUtils.getValueConstructor()));
                break;
            }
            case "orders":{
                addFragments(OrdersFragment.getInstance(mFragmentManager)
                        .getCurrentOrderInforamtionFr());
                break;
            }
            default:{
                break;
            }
        }

    }

    private boolean addFragments(Fragment fragment){
        if(fragment == null) return false;
        mFragmentManager.beginTransaction()
                .add(R.id.frame_main,fragment)
                .addToBackStack("")
                .commit();
        return true;
    }

}
