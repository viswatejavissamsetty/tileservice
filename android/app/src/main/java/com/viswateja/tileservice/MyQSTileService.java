package com.viswateja.tileservice;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.widget.Toast;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MyQSTileService extends TileService {
    // Called when the user adds your tile.
    @Override
    public void onTileAdded() {
        super.onTileAdded();
    }

    // Called when your app can update your tile.
    @Override
    public void onStartListening() {
        super.onStartListening();
    }

    // Called when your app can no longer update your tile.
    @Override
    public void onStopListening() {
        super.onStopListening();
    }

    /**
     * Time interval in milliseconds for triple-click detection
     */
    private static final long TIME_INTERVAL = 500;
    /**
     * Maximum number of clicks for a triple-click
     */
    private static final int MAX_CLICKS = 3;
    /**
     * Delay in milliseconds for auto-inactive after triple click
     */
    private static final long AUTO_INACTIVE_DELAY = 1000 * 5;

    /**
     * Number of clicks detected
     */
    private int clickCount = 0;
    /**
     * Time of the last click
     */
    private long lastClickTime = 0;

    // Called when the user taps on your tile in an active or inactive state.
    @Override
    public void onClick() {
        super.onClick();
        long currentTime = System.currentTimeMillis();

        // Check if the time difference between the last click and the current click is
        // within the interval
        if (currentTime - lastClickTime < TIME_INTERVAL) {
            clickCount++;

            // Check if triple-click is detected
            if (clickCount == MAX_CLICKS) {
                performTripleClickAction();
                clickCount = 0; // Reset click count
            }
        } else {
            // Reset click count if the time interval has passed since the last click
            clickCount = 1;
        }

        // Update last click time
        lastClickTime = currentTime;
    }

    /**
     * Perform the action you want when a triple-click is detected.
     */
    private void performTripleClickAction() {
        Tile tile = getQsTile();
        if (tile != null) {
            tile.setState(Tile.STATE_ACTIVE);
            tile.updateTile();

            Toast.makeText(this, "Triple-click detected", Toast.LENGTH_LONG).show();

            // Write your code here to perform the action you want when a triple-click is
            // detected.
            // this.makeEmergencyApiCall();
            // this.logLocation();

            // FileOperationsService fileOperationsService = new FileOperationsService();
            // String fileName = "phonenumber.txt";
            // String phoneNumber = fileOperationsService.readFromFile(fileName);
            // if (phoneNumber == null) {
            // Toast.makeText(this, "Phone number not found", Toast.LENGTH_SHORT).show();
            // }

            // System.out.println("Phone number: " + phoneNumber);
            // LocationManager locationManager = (LocationManager)
            // getSystemService(Context.LOCATION_SERVICE);
            // LocationListener locationListener = new LocationListener() {
            // public void onLocationChanged(Location location) {
            // // Called when a new location is found by the network location provider.
            // System.out.println("Location: " + location.getLatitude() + ", " +
            // location.getLongitude());
            // try {
            // URL url = new URL(getString(R.string.emergency_api_url));
            // HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // connection.setRequestMethod("POST");
            // connection.setRequestProperty("Content-Type", "application/json");
            // connection.setDoOutput(true);
            // DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            // String requestBody = "{\"phoneNumber\": \"" + phoneNumber + "\", \"gps\": ["
            // + location.getLatitude() + ", " + location.getLongitude() + "]}";
            // wr.writeBytes(requestBody);
            // wr.flush();
            // wr.close();

            // int responseCode = connection.getResponseCode();
            // System.out.println("Response Code: " + responseCode);

            // BufferedReader in = new BufferedReader(new
            // InputStreamReader(connection.getInputStream()));
            // String inputLine;
            // StringBuffer response = new StringBuffer();
            // while ((inputLine = in.readLine()) != null) {
            // response.append(inputLine);
            // }
            // in.close();

            // System.out.println("Response: " + response.toString());
            // } catch (MalformedURLException e) {
            // e.printStackTrace();
            // } catch (IOException e) {
            // e.printStackTrace();
            // }
            // }

            // public void onStatusChanged(String provider, int status, Bundle extras) {
            // }

            // public void onProviderEnabled(String provider) {
            // }

            // public void onProviderDisabled(String provider) {
            // }
            // };

        }

        // Schedule auto-inactive after 5 seconds
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Auto-inactive the tile after 5 seconds
                Tile inactiveTile = getQsTile();
                if (inactiveTile != null) {
                    Toast.makeText(MyQSTileService.this, "Auto-inactive", Toast.LENGTH_SHORT).show();
                    inactiveTile.setState(Tile.STATE_INACTIVE);
                    inactiveTile.updateTile();
                }
            }
        }, AUTO_INACTIVE_DELAY);
    }

    // private void logLocation() {
    // LocationManager locationManager = (LocationManager)
    // getSystemService(Context.LOCATION_SERVICE);

    // LocationListener locationListener = new LocationListener() {
    // public void onLocationChanged(Location location) {
    // // Called when a new location is found by the network location provider.
    // System.out.println("Location: " + location.getLatitude() + ", " +
    // location.getLongitude());
    // }

    // public void onStatusChanged(String provider, int status, Bundle extras) {
    // }

    // public void onProviderEnabled(String provider) {
    // }

    // public void onProviderDisabled(String provider) {
    // }
    // };

    // try {
    // locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,
    // 0, locationListener);
    // } catch (SecurityException e) {
    // System.out.println("Location permission denied");
    // }
    // }

    // private void makeEmergencyApiCall() throws IOException {
    // // URL: https://dev-emergency.lifehealthemergency.com/api/emergency
    // // Method: POST
    // // Body: {phoneNumber: "1234567890", gps: [12.3456, 78.9012]}

    // // get url for andtrid strings
    // String apiUrl = getString(R.string.emergency_api_url);
    // System.out.println("URL: " + apiUrl);

    // URL url = new URL(apiUrl);
    // HttpURLConnection connection = (HttpURLConnection) url.openConnection();

    // String requestBody = "{\"phoneNumber\": \"1234567890\", \"gps\": [12.3456,
    // 78.9012]}";

    // }

    // Called when the user removes your tile.
    @Override
    public void onTileRemoved() {
        super.onTileRemoved();
    }
}
