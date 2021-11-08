package com.example.tiltdetection;

import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.TextView;

import java.security.Provider;


public class MainActivity extends AppCompatActivity implements SensorEventListener {
    TextView textView;
    View layout;
    View upperView;
    TextView modeIdentifier;

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

        sensorManager = (SensorManager) getSystemService(Service.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

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
        if(event.sensor.getType() == Sensor.TYPE_LIGHT)
        {
            textView.setText("" + event.values[0]);
        }

        float luxData = event.values[0];

        if(luxData > 20000f)
        {
            upperView.setBackgroundColor(Color.parseColor("#CD5858"));
            modeIdentifier.setText("Light mode");
            modeIdentifier.setTextColor(Color.parseColor("#3E3E3E"));
            layout.setBackgroundColor(Color.parseColor("#F4F4F4"));
            textView.setTextColor(Color.parseColor("#403F3F"));
        }
        else
        {
            upperView.setBackgroundColor(Color.parseColor("#363636"));
            modeIdentifier.setText("Dark mode");
            modeIdentifier.setTextColor(Color.parseColor("#FFFFFF"));
            layout.setBackgroundColor(Color.parseColor("#515151"));
            textView.setTextColor(Color.parseColor("#F0EEEE"));
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }
}