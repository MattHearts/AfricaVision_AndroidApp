package com.avision_amc.africavisionapp;

import static com.avision_amc.africavisionapp.PhoneUtils.makePhoneCall;

import android.Manifest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;// Request code for image capture
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 200;// Request code for location permission

    private static final int REQUEST_CAMERA_PERMISSION = 1;

    private static final String TELEPHONE_NUM_START = "003069000000";
    private String phoneNumber;
    private TextView textViewCountry;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textViewCountry = findViewById(R.id.textViewCountry);

        Button openQRButton = findViewById(R.id.buttonQR);
        openQRButton.setOnClickListener(view -> initiateQRCodeScan());

        Button CommentButton = findViewById(R.id.buttonComment);
        CommentButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, CommentsActivity.class);
            startActivity(intent);
        });

        Button buttonPartyScope = findViewById(R.id.buttonPartyScope);
        buttonPartyScope.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, PartyScopeActivity.class);
            startActivity(intent);
        });

        Button buttonRefresh = findViewById(R.id.buttonRefresh);
        buttonRefresh.setOnClickListener(view -> getCurrentLocation());

        Button buttonVote = findViewById(R.id.buttonVote);
        buttonVote.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, VoteActivity.class);
            startActivity(intent);
        });

        //Checks if location permission is granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        } else {
            //Requests location permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private void initiateQRCodeScan() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setPrompt("Scan the QR code displayed on your TV");
        integrator.initiateScan();
    }

    private void getCurrentLocation() {
        //Checks if GPS provider is enabled and location permission is granted
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            android.location.LocationManager locationManager = (android.location.LocationManager) getSystemService(android.content.Context.LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(android.location.LocationManager.GPS_PROVIDER);

            if (location != null) {
                //Gets the latitude and longitude
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                //Uses Geocoder to get the country based on the coordinates
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    if (!addresses.isEmpty()) {
                        Address address = addresses.get(0);
                        String country = address.getCountryName();
                        textViewCountry.setText(country);
                    } else {
                        textViewCountry.setText("Unable to determine country");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                textViewCountry.setText("Location data unavailable");
            }
        } else {
            textViewCountry.setText("Location permission not granted");
        }
    }

    /*@Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Camera permission granted
                openCamera();
            } else {
                // Camera permission denied
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }

    }
    private void requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        } else {
            // Permission already granted
            openCamera();
        }
    }
    public void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        } else {
            Toast.makeText(this, "Camera not available", Toast.LENGTH_SHORT).show();
        }
    }*/

    //Handles the result of the camera capture
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        //if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
        // Image capture successful
        //} else if (resultCode == RESULT_CANCELED) {
        //    Toast.makeText(this, "Image capture cancelled", Toast.LENGTH_SHORT).show();
        // } else {
        //     Toast.makeText(this, "Failed to capture image", Toast.LENGTH_SHORT).show();
        //}


        //Parses the result from the QR code scanner
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                String qrContent = result.getContents();
                if (qrContent.startsWith("africavision_")) {
                    //Takes the callID from the QR code content
                    String callID = qrContent.substring(qrContent.length() - 2);

                    phoneNumber = TELEPHONE_NUM_START + callID;
                    //Makes a phone call or SMS using the constructed phone number
                    PhoneUtils.VoteChoice(this, callID);

                } else {
                    Toast.makeText(this, "Invalid QR code", Toast.LENGTH_LONG).show();
                }
            }
        } else {

            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}