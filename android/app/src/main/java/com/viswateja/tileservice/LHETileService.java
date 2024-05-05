package com.viswateja.tileservice;

import android.os.Build;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.util.Arrays;

@RequiresApi(api = Build.VERSION_CODES.N)
public class LHETileService extends TileService {
    private int tapCount = 0;
    private boolean isActive = false;
    private Handler handler = new Handler();
    private final long REVERT_TIME = 1000 * 60;
    private final String DEFAULT_TILE_TEXT = "LHE";
    private Runnable revertRunnable = () -> {
        if (tapCount < 3) {
            revertTile();
        }
    };

    @Override
    public void onClick() {
        tapCount++;
        System.out.println("Tapped " + tapCount);
        showNumberOfClicksLeftOnTile();
        if (isActive) {
            if (tapCount == 2) {
                revertTile();
                stopEmergency();
            }
        } else {
            if (tapCount == 3) {
                activateTile();
                triggerEmergency();
                tapCount = 0;
            } else {
                handler.removeCallbacks(revertRunnable);
                handler.postDelayed(revertRunnable, REVERT_TIME);
            }
        }
    }

    /**
     * Method to trigger emergency. This method will be called when the user taps on
     * the tile 3 times.
     */
    private void triggerEmergency() {
        LocationService locationService = new LocationService();

        if (!locationService.isLocationServiceEnabled(this)) {
            this.redirectToDialPad();
            revertTile();
            return;
        }

        Number[] currentLocation = locationService.getCurrentLocation(this);
        Toast.makeText(this, "Your current location is: " + Arrays.toString(currentLocation), Toast.LENGTH_LONG).show();

        PhoneNumberService phoneNumberService = new PhoneNumberService();
        String phoneNumber = phoneNumberService.getPhoneNumber();

        Toast.makeText(this, "Your phone number is: " + phoneNumber, Toast.LENGTH_LONG).show();

        NotificationService.showNotification(this, "Emergency Triggered",
                "You have triggered an emergency. In case if you need to cancel please double tap on same tile.");

    }

    /**
     * Method to stop the emergency triggered. This method will be called when the
     * user taps on the tile 2 times.
     */
    private void stopEmergency() {
        // Make a different function call to stop emergency
        Toast.makeText(this, "Emergency stopped", Toast.LENGTH_SHORT).show();
    }

    private void showNumberOfClicksLeftOnTile() {
        getQsTile().setLabel("Clicks left: " + (3 - tapCount));
        getQsTile().updateTile();
    }

    /**
     * Method to activate the tile.
     */
    private void activateTile() {
        tapCount = 0;
        isActive = true;
        getQsTile().setState(Tile.STATE_ACTIVE);
        getQsTile().setLabel("Emergency Active");
        getQsTile().updateTile();
        handler.postDelayed(this::revertTile, REVERT_TIME);
    }

    /**
     * Method to revert the activated tile.
     */
    private void revertTile() {
        tapCount = 0;
        isActive = false;
        getQsTile().setState(Tile.STATE_INACTIVE);
        getQsTile().setLabel(DEFAULT_TILE_TEXT);
        getQsTile().updateTile();
        Toast.makeText(this, "Tile reverted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStartListening() {
        super.onStartListening();
    }

    /**
     * Method to redirect to dial pad.
     */
    private void redirectToDialPad() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:108"));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}