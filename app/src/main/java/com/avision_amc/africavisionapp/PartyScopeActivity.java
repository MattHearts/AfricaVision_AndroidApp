package com.avision_amc.africavisionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.TextView;


public class PartyScopeActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor gyroscope;
    private Vibrator vibrator;
    private TextView textView;

    private boolean isHardDetected = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_scope);


        //Initializes views and sensors
        textView = findViewById(R.id.textView);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        //Unregisters the gyroscope sensor listener when the activity is paused
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSensorChanged(SensorEvent event) {
        //Retrieves the gyroscope sensor values
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];


        float threshold = 1.5f; //Adjusts the threshold value as needed for detecting hard movements

        //Checks if the gyroscope values are over the threshold
        if (Math.abs(x) > threshold || Math.abs(y) > threshold || Math.abs(z) > threshold) {
            //If a hard partying is detected and it was not previously detected
            if (!isHardDetected) {
                textView.setText("HARD");
                textView.setTextColor(Color.RED);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    //vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
                    vibrator.vibrate(VibrationEffect.createWaveform(new long[]{0, 200}, 0));
                }

                isHardDetected = true;
            }
        } else {
            // If no hard movement is detected and it was previously detected
            if (isHardDetected) {
                textView.setText("low");
                textView.setTextColor(Color.GREEN);
                vibrator.cancel();
                isHardDetected = false;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not used, but required to implement SensorEventListener
    }


}