package com.viswateja.tileservice;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class LocationService {
    private LocationManager locationManager;
    private LocationListener locationListener;
    private String locationProvider = LocationManager.NETWORK_PROVIDER;

    public Number[] getCurrentLocation(Context context) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                System.out.println("Location: " + location.getLatitude() + ", " + location.getLongitude());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                System.out.println("Status: " + status);
            }

            @Override
            public void onProviderEnabled(String provider) {
                System.out.println("Provider enabled: " + provider);
            }

            @Override
            public void onProviderDisabled(String provider) {
                System.out.println("Provider disabled: " + provider);
            }
        };

        if ((context
                .checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                ||
                (context.checkSelfPermission(
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            System.out.println("Location permission not granted");
            Toast.makeText(context, "Location permission not granted", Toast.LENGTH_LONG).show();
            return null;
        }

        locationManager.requestLocationUpdates(locationProvider, 0, 0, locationListener);

        Location location;

        /**
         * Check if location service is on and location is available
         */

        Boolean isLocationEnabled = locationManager.isProviderEnabled(locationProvider);

        if (isLocationEnabled) {
            while (true) {
                location = locationManager.getLastKnownLocation(locationProvider);
                if (location != null) {
                    break;
                }
            }
            if (location == null) {
                System.out.println("Location not found");
                Toast.makeText(context, "Location not found", Toast.LENGTH_LONG).show();
                return null;
            }
            return new Number[] { location.getLatitude(), location.getLongitude() };
        } else {
            System.out.println("Location service is off");
            Toast.makeText(context, "Location service is off", Toast.LENGTH_LONG).show();
            return null;
        }

    }

    public void stopLocationUpdates() {
        locationManager.removeUpdates(locationListener);
    }

    public boolean isLocationServiceEnabled(Context context) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(locationProvider);
    }

}
