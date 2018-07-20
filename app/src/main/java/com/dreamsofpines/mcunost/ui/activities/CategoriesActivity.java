package com.dreamsofpines.mcunost.ui.activities;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamsofpines.mcunost.Manifest;
import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.storage.help.menu.LeftMenu;
import com.dreamsofpines.mcunost.data.storage.preference.GlobalPreferences;
import com.dreamsofpines.mcunost.mcUnost;
import com.dreamsofpines.mcunost.ui.dialog.ChooseCityDialogFragment;
import com.dreamsofpines.mcunost.ui.dialog.CityDialogFragment;
import com.dreamsofpines.mcunost.ui.fragments.CategoriesFragment;
import com.dreamsofpines.mcunost.ui.fragments.ConstructorFragment;
import com.dreamsofpines.mcunost.ui.fragments.InformExcursionFragment;
import com.dreamsofpines.mcunost.ui.fragments.MainLogoFragment;
import com.dreamsofpines.mcunost.ui.fragments.PackExcursionFragment;
import com.dreamsofpines.mcunost.ui.fragments.RegistrFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.yandex.metrica.YandexMetrica;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


/**
 * Created by ThePupsick on 15.07.17.
 */

public class CategoriesActivity extends AppCompatActivity  {

    private final FragmentManager fm = getSupportFragmentManager();
    private CategoriesFragment fragment;
    private Button button;
    private LeftMenu leftMenu;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private LocationRequest mLocationRequest;
    private InformExcursionFragment iE;
    private RegistrFragment rF;

    private ConstructorFragment fr;
    private int fl = 0;


    @Override
    protected void onResume() {
        super.onResume();
//        YandexMetrica.getReporter(getBaseContext(), mcUnost.API_key).onResumeSession();
    }

    @Override
    protected void onPause() {
//        YandexMetrica.getReporter(getBaseContext(), mcUnost.API_key).onPauseSession();
        super.onPause();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        bindView();
        fl = 0;

        leftMenu  = new LeftMenu(this,fm,getBaseContext());
        leftMenu.build(this);
        leftMenu.setOnCityChangedListener(new LeftMenu.OnCityChanged() {
            @Override
            public void onChanged(String city) {
                if(fr != null){
                    fr.updateCity();
                }
            }
        });
        leftMenu.setGetPageList(new LeftMenu.getTourPage() {
            @Override
            public void getPage() {
                if(fr == null) {
                    fr = new ConstructorFragment();
                    fr.setFragmentManager(fm);
                }
                fm.beginTransaction()
                        .replace(R.id.frame_layout,fr)
                        .commit();
            }
        });
        settingView();

        if (Build.VERSION.SDK_INT >= 23) {
            setLocationPremissions();
        } else {
            bindLocationManager();
        }

    }


    protected void createLocationRequest(){
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);
    }

    private void bindView(){
        fragment = (CategoriesFragment) fm.findFragmentById(R.id.frame_layout);
        button = (Button) findViewById(R.id.button_menu);
    }

    private void settingView(){
        fr = new ConstructorFragment();
        fr.setFragmentManager(fm);
        fm.beginTransaction()
                .add(R.id.frame_layout, fr)
                .addToBackStack(null)
                .commit();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                leftMenu.openMenu();
            }
        });

    }


    private void setLocationPremissions(){
        Log.i("Myapp",""+(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED));
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},23);
        }else{
            bindLocationManager();
        }
    }


    private void bindLocationManager(){
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    String str = decodeCoordinat(location);
//                    if(str.equalsIgnoreCase("Не задан")){
//                        CityDialogFragment dF = CityDialogFragment.newInstance(
//                                        new CityDialogFragment.OnCityChangedListener() {
//                                            @Override
//                                            public void onChange(String city) {
//                                                GlobalPreferences.setPrefUserCity(getApplicationContext(),city);
//                                                setUpdateCity();
//                                            }
//                                        }, GlobalPreferences.getPrefUserCity(getApplicationContext()));
//                        dF.show(getSupportFragmentManager(),"dF");
//                        Toast.makeText(getBaseContext(),"Город был неопределен, выберите город вручную!",Toast.LENGTH_LONG)
//                                .show();
//                    }
                    GlobalPreferences.setPrefUserCity(getApplicationContext(),str);
                    setUpdateCity();
                }
        });
    }

    private String decodeCoordinat(Location location){
        String city = GlobalPreferences.getPrefUserCity(getBaseContext());
        if(null!=location) {
            Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
            List<Address> addresses;
            try {
                addresses = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                if (addresses.size() > 0) {
                    city = addresses.get(0).getLocality();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return city;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 23:{
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    bindLocationManager();
                }
            }
        }
    }

//    @Override
//    public boolean dispatchKeyEvent(KeyEvent event) {
//        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN && fm.getBackStackEntryCount() == 1) {
//            ++fl;
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    fl = 0;
//                }
//            },3000);
//            Toast.makeText(getApplicationContext(), "Если хотите выйти нажмите кнопку назад еще раз!",Toast.LENGTH_LONG).show();
//            if(fl == 2) finishAffinity();
//            return true;
//        }else{
//            return super.dispatchKeyEvent(event);
//        }
//    }

    public void setUpdateBadge(){
        leftMenu.updateNewOrder();
    }
    public void setUpdateCity(){
        leftMenu.updateCity();
    }
    public void updateAllMenu(){leftMenu.build(this);}

}
