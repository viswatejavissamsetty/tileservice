package com.viswateja.tileservice;

import android.content.res.Resources;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class EmergencyCallService {
    private final String emergencyApiURL = Resources.getSystem().getString(R.string.emergency_api_url);

    private void makeEmergencyApiCall(String phoneNumber, Number[] gps) throws IOException {
        // URL: https://dev-emergency.lifehealthemergency.com/api/emergency
        // Method: POST
        // Body: {phoneNumber: "1234567890", gps: [12.3456, 78.9012]}

        // get url for android strings
        System.out.println("URL: " + emergencyApiURL);

        URL url = new URL(emergencyApiURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        String requestBody = "{\"phoneNumber\": \"" + phoneNumber + "\", \"gps\": [" + gps[0] + ", " + gps[1] + "]}";

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = requestBody.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

    }

    private void deleteCancelApiCall(String phoneNumber) throws IOException {
        // URL: https://dev-emergency.lifehealthemergency.com/api/cancel
        // Method: DELETE
        // Body: {phoneNumber: "1234567890"}

        // get url for android strings
        System.out.println("URL: " + emergencyApiURL);

        URL url = new URL(emergencyApiURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        String requestBody = "{\"phoneNumber\": \"" + phoneNumber + "\"}";

        connection.setRequestMethod("DELETE");
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = requestBody.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

    }
}
