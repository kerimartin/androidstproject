package com.example.tiltdetection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.solver.state.State;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.app.Service;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.security.Provider;


public class MainActivity extends AppCompatActivity implements SensorEventListener {
    TextView textView;
    View layout;
    View upperView;
    TextView modeIdentifier;
    CalendarView calendarviewlight;
    CalendarView calendarviewdark;

    SensorManager sensorManager;
    Sensor sensor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout = findViewById(R.id.mainBody);
        upperView = findViewById(R.id.upperBar);
        modeIdentifier = findViewById(R.id.tv2);
        textView = findViewById(R.id.tv1);
        calendarviewlight = findViewById(R.id.calendar);
        calendarviewdark = findViewById(R.id.calendar2);

        sensorManager = (SensorManager) getSystemService(Service.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        getWindow().setStatusBarColor(Color.parseColor("#CD5858"));
        getWindow().setNavigationBarDividerColor(Color.parseColor("#CD5858"));

    }


    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, sensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            textView.setText("" + event.values[0]);
        }

        float luxData = event.values[0];

        if(luxData > 20000f)
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        if (luxData > 20000f) {
            upperView.setBackgroundColor(Color.parseColor("#CD5858"));
            modeIdentifier.setText("Light mode");
            modeIdentifier.setTextColor(Color.parseColor("#3E3E3E"));
            layout.setBackgroundColor(Color.parseColor("#F4F4F4"));
            textView.setTextColor(Color.parseColor("#403F3F"));
            //calendarview.setBackgroundColor(Color.parseColor("#F4F4F4"));
            calendarviewlight.setVisibility(View.VISIBLE);
            calendarviewdark.setVisibility(View.INVISIBLE);
            getWindow().setStatusBarColor(Color.parseColor("#CD5858"));
        } else {
            getWindow().setStatusBarColor(Color.parseColor("#363636"));
            upperView.setBackgroundColor(Color.parseColor("#363636"));
            modeIdentifier.setText("Dark mode");
            modeIdentifier.setTextColor(Color.parseColor("#FFFFFF"));
            layout.setBackgroundColor(Color.parseColor("#878787"));
            textView.setTextColor(Color.parseColor("#F0EEEE"));
            //calendarviewlight.setBackgroundColor(Color.parseColor("#878787"));
            calendarviewlight.setVisibility(View.INVISIBLE);
            calendarviewdark.setVisibility(View.VISIBLE);
            getWindow().setNavigationBarDividerColor(Color.parseColor("#878787"));
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }
}