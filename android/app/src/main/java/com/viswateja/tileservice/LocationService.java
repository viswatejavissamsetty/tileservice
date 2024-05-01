package com.viswateja.tileservice;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class LocationService {
    private LocationManager locationManager;
    private LocationListener locationListener;

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

        if (context
                .checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            System.out.println("Location permission not granted");
            return null;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location == null) {
            System.out.println("Location not found");
            return null;
        }

        return new Number[] { location.getLatitude(), location.getLongitude() };
    }

    public void stopLocationUpdates() {
        locationManager.removeUpdates(locationListener);
    }
}