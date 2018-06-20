package com.example.kechaval.gpsproyect;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.example.kechaval.gpsproyect.Constantes.SET_CONSTANT_LATITUDE;
import static com.example.kechaval.gpsproyect.Constantes.SET_CONSTANT_LONGITUDE;
import static com.example.kechaval.gpsproyect.Constantes.SET_CONSTANT_SENDING_COORDINATES;

public class GpsActivity extends AppCompatActivity {
    TextView tvLatitud,tvLongitud,tvDir;
    private GPS gps;
    private IntentFilter myFilter;

    BroadcastReceiver BR= new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action= intent.getAction();
            if (action.equals(SET_CONSTANT_SENDING_COORDINATES))
            {
                double latitud = intent.getDoubleExtra(SET_CONSTANT_LATITUDE,0);
                double longitud = intent.getDoubleExtra(SET_CONSTANT_LONGITUDE,0);
                setCoordenadas(latitud,longitud);

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
        myFilter = new IntentFilter();
        myFilter.addAction(SET_CONSTANT_SENDING_COORDINATES);
        initView();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        } else {
            locationStart();
        }


    }

    private void locationStart() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }

        getLatLong();
    }
    public void getLatLong(){
        gps= new GPS(this);
        if(gps.canGetLocation()){
            double latitud = gps.getLatitude();
            double longitud = gps.getLongitude();
            Location location= gps.getLocation();
            setCoordenadas(latitud,longitud);
            setLocation(location);

        }
    }
    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(BR,myFilter);
    }

    @Override
    public void onPause() {
        unregisterReceiver(BR);
        super.onPause();
    }

    private void initView(){
        tvLatitud = findViewById(R.id.tvLatitud);
        tvLongitud = findViewById(R.id.tvLongitud);
        tvDir = findViewById(R.id.tvDir);
    }

    public void setLocation(Location loc) {
        //Obtener la direccion de la calle a partir de la latitud y la longitud
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                    tvDir.setText("Mi direccion es: \n"
                            + DirCalle.getAddressLine(0));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void setCoordenadas(double latitud,double longitud){
        tvLatitud.setText(String.valueOf(latitud));
        tvLongitud.setText(String.valueOf(longitud));
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationStart();
                return;
            }
        }
    }




}
