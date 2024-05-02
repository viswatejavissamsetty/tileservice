package com.viswateja.tileservice;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

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
    private static final long TIME_INTERVAL = 1000;
    /**
     * Maximum number of clicks for a triple-click
     */
    private static final int MAX_CLICKS = 3;
    /**
     * Delay in milliseconds for auto-inactive after triple click
     */
    private static final long AUTO_INACTIVE_DELAY = 1000 * 30;

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
            System.out.println("Triple-click detected");

            // Get the location and log it in the console
            LocationService locationService = new LocationService();
            Number[] currentLocation = locationService.getCurrentLocation(this);

            if (currentLocation != null) {
                System.out.println("Location: " + currentLocation[0] + ", " + currentLocation[1]);
            }

             locationService.stopLocationUpdates();

            // logic to get user phone number
            FileOperationsService fileOperationsService = new FileOperationsService();

            System.out.println(fileOperationsService.hadFile("phonenumber.txt"));

            String phoneNumber = fileOperationsService.readFromFile("phonenumber.txt");
            if (phoneNumber != null) {
                System.out.println("Phone number: " + phoneNumber);
            } else {
                System.out.println("Phone number not found");
            }

            if (currentLocation != null) {
                // Logic to show data in toast message
                String data = "Location: " + currentLocation[0] + ", " + currentLocation[1] + "\nPhone number: " + phoneNumber;
                Toast.makeText(this, data, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Location not found", Toast.LENGTH_LONG).show();
            }

            this.playTone();

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

    // Called when the user removes your tile.
    @Override
    public void onTileRemoved() {
        super.onTileRemoved();
    }

    private void playTone() {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.emergency_alarm1);
        mediaPlayer.start();
    }
}
