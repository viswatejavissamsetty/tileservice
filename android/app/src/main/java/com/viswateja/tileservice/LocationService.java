package com.viswateja.tileservice;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

public class LocationService extends Activity {
    private LocationManager locationManager;
    private LocationListener locationListener;
    private final String  locationProvider = LocationManager.GPS_PROVIDER;

    public Number[] getCurrentLocation(Context context) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                System.out.println("Location: " + location.getLatitude() + ", " + location.getLongitude());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                System.out.println("Status: " + status);
            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {
                System.out.println("Provider enabled: " + provider);
            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {
                System.out.println("Provider disabled: " + provider);
            }
        };

        boolean isLocationPermissionsGranted = (context
                .checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                ||
                (context.checkSelfPermission(
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);

        if (!isLocationPermissionsGranted) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, 1);
        }

        if (!isLocationPermissionsGranted) {
            System.out.println("Location permission not granted");
            Toast.makeText(context, "Location permission not granted", Toast.LENGTH_LONG).show();
            return null;
        }

        locationManager.requestLocationUpdates(locationProvider, 0, 0, locationListener, Looper.getMainLooper());

        boolean isLocationEnabled = locationManager.isProviderEnabled(locationProvider);

        Location location;

        if (isLocationEnabled) {
            int count = 0;

            while (true) {
                location = locationManager.getLastKnownLocation(locationProvider);
                if (location != null || count == 5) {
                    break;
                }
                // wait for 2 seconds
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    System.out.println("Unable to use thread");
                }
                count++;
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
