package com.viswateja.tileservice;

import android.app.Service;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.service.quicksettings.TileService;

import androidx.annotation.RequiresApi;

public class StateModel {
    final boolean enabled;
    final String label;
    final Icon icon;

    public StateModel(boolean e, String l, Icon i) {
        enabled = e;
        label = l;
        icon = i;
    }
}

