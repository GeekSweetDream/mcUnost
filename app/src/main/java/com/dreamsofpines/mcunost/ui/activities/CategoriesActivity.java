package com.dreamsofpines.mcunost.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
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
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamsofpines.mcunost.Manifest;
import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.database.MyDataBase;
import com.dreamsofpines.mcunost.data.storage.help.menu.InformExcursion;
import com.dreamsofpines.mcunost.data.storage.help.menu.LeftMenu;
import com.dreamsofpines.mcunost.data.storage.preference.GlobalPreferences;
import com.dreamsofpines.mcunost.ui.adapters.ViewAdapter;
import com.dreamsofpines.mcunost.ui.fragments.CategoriesFragment;
import com.dreamsofpines.mcunost.ui.fragments.InformExcursionFragment;
import com.dreamsofpines.mcunost.ui.fragments.PackExcursionFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.R.attr.button;
import static android.R.attr.fragment;
import static android.R.attr.left;
import static com.dreamsofpines.mcunost.R.id.city1;
import static com.dreamsofpines.mcunost.R.id.city3;

/**
 * Created by ThePupsick on 15.07.17.
 */

public class CategoriesActivity extends AppCompatActivity {

    private final FragmentManager fm = getSupportFragmentManager();
    private LocationManager locationManager;
    private LocationListener locationListener;
    private CategoriesFragment fragment;
    private Button button, butOk;
    private LeftMenu leftMenu;
    private View chooseCity, notification;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private LocationRequest mLocationRequest;
    private LocationCallback mLocationCallback;
    private Activity mActivity;
    private TextView cityName,city1,city2,city3;
    private RelativeLayout rl,rl1, mess, timerNotify;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        bindView();
        settingView();
        leftMenu  = new LeftMenu(this);
        leftMenu.build(this,0);
        mActivity = this;
        if(GlobalPreferences.getPrefUserCity(mActivity).equalsIgnoreCase("Не задан")) {
            if (Build.VERSION.SDK_INT >= 23) {
                setLocationPremissions();
            } else {
                bindLocationManager();
            }
        }
    }

    protected void createLocationRequest(){
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);
    }

    private void bindView(){
        fragment = (CategoriesFragment) fm.findFragmentById(R.id.frame_layout);
        button = (Button) findViewById(R.id.button_menu);
        chooseCity = (View) findViewById(R.id.view_choose_city);
        cityName = (TextView) chooseCity.findViewById(R.id.mycity);
        butOk = (Button) chooseCity.findViewById(R.id.butt_ok_city);
        rl = (RelativeLayout) chooseCity.findViewById(R.id.another_city);
        rl1 = (RelativeLayout) chooseCity.findViewById(R.id.rl_city);
        mess = (RelativeLayout) chooseCity.findViewById(R.id.mess_city);
        city1 = (TextView) chooseCity.findViewById(R.id.city1);
        city2 = (TextView) chooseCity.findViewById(R.id.city2);
        city3 = (TextView) chooseCity.findViewById(R.id.city3);
        notification = (View) findViewById(R.id.order_notification);
        timerNotify = (RelativeLayout) findViewById(R.id.timer_order);
    }

    private void settingView(){

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(timerNotify.getVisibility() != View.VISIBLE) {
                    timerNotify.setVisibility(View.VISIBLE);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            timerNotify.setVisibility(View.INVISIBLE);
                        }
                    }, 5000);
                }else{
                    timerNotify.setVisibility(View.INVISIBLE);
                }
            }
        });

        timerNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
            }
        });

        if(fragment == null){
            fragment = new CategoriesFragment();
            fm.beginTransaction()
                    .add(R.id.frame_layout,fragment)
                    .commit();
        }

        fragment.setOnClickRecyclerListener(new CategoriesFragment.OnClickRecyclerListener(){
            @Override
            public void onClicked(Bundle bundle) {
                timerNotify.setVisibility(View.INVISIBLE);
                changeFragmentPackExc(bundle);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                leftMenu.openMenu();
            }
        });

        city1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityName.setText(city1.getText().toString());
                rl.setVisibility(View.INVISIBLE);
                butOk.setVisibility(View.VISIBLE);
            }
        });

        city2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityName.setText(city2.getText().toString());
                rl.setVisibility(View.INVISIBLE);
                butOk.setVisibility(View.VISIBLE);
            }
        });

        city3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityName.setText(city3.getText().toString());
                rl.setVisibility(View.INVISIBLE);
                butOk.setVisibility(View.VISIBLE);
            }
        });

        rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl.setVisibility(rl.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
                butOk.setVisibility(butOk.getVisibility() == View.INVISIBLE ? View.VISIBLE : View.INVISIBLE);
            }
        });

        butOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseCity.setVisibility(View.INVISIBLE);
                GlobalPreferences.setPrefUserCity(mActivity,cityName.getText().toString());
                leftMenu.updateCity();
            }
        });

        chooseCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
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
//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
//            locationListener = new MyLocationListener();
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1,locationListener);
//        }
//        if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

//            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//                        Toast.makeText(getBaseContext(), "" + (location == null), Toast.LENGTH_SHORT).show();
//        mLocationCallback = new LocationCallback(){
//            @Override
//            public void onLocationResult(LocationResult locationResult) {
//                for(Location location: locationResult.getLocations()) {
//                    cityName.setText(decodeCoordinat(location));
//                    butOk.setClickable(true);
//                    rl.setVisibility(View.INVISIBLE);
//                    mFusedLocationProviderClient.removeLocationUpdates(mLocationCallback);
//                }
//            }
//        };
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(null == location) {
                        Handler handler = new Handler();
                        mess.setVisibility(View.VISIBLE);
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mess.setVisibility(View.INVISIBLE);
                            }
                        },8000);
                    }
//                    }else{
//                        Toast.makeText(mActivity,"Тоби пизда",Toast.LENGTH_SHORT).show();
//                        createLocationRequest();
//                        mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
//                    }
                    showViewWithCity(location);
                }
        });
    }

    public void showViewWithCity(Location location){
        chooseCity.setVisibility(View.VISIBLE);
        cityName.setText(decodeCoordinat(location));
        butOk.setClickable(true);
    }

    private String decodeCoordinat(Location location){
        String city = GlobalPreferences.getPrefUserCity(mActivity);
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

//    private class MyLocationListener implements LocationListener{
//        @Override
//        public void onLocationChanged(Location location) {
//            String city = "";
//            Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
//            List<Address> addresses;
//            try {
//                addresses = gcd.getFromLocation(location.getLatitude(),location.getLongitude(),1);
//                if(addresses.size() > 0){
//                    city = addresses.get(0).getLocality();
//                }
//                locationManager.removeUpdates(locationListener);
//            }catch (IOException e){
//                e.printStackTrace();
//            }
//            Toast.makeText(getBaseContext(),city,Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onStatusChanged(String s, int i, Bundle bundle) {}
//
//        @Override
//        public void onProviderEnabled(String s) {}
//
//        @Override
//        public void onProviderDisabled(String s) {}
//    }


}
