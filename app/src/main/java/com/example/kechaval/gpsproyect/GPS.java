package com.example.kechaval.gpsproyect;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import static com.example.kechaval.gpsproyect.Constantes.SET_CONSTANT_LATITUDE;
import static com.example.kechaval.gpsproyect.Constantes.SET_CONSTANT_LONGITUDE;
import static com.example.kechaval.gpsproyect.Constantes.SET_CONSTANT_SENDING_COORDINATES;

/**
 * Created by KeChaval on 20/06/2018.
 */

public class GPS extends Service implements LocationListener {
    private final Context mContext;

    // flag for GPS status
    boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;

    // flag for GPS status
    boolean canGetLocation = false;

    Location location; // location
    double latitude; // latitude
    double longitude; // longitude

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 3; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000; // 1 minute

    // Declaring a Location Manager
    protected LocationManager locationManager;

    public GPS(Context context) {
        this.mContext = context;
        getLocation();
    }


    public Location getLocation() {
        return location;
    }
}
