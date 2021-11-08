package com.example.tiltdetection;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.app.Service;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


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

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MMM-dd", Locale.getDefault());
        String formattedDate = df.format(c);

        layout = findViewById(R.id.mainBody);
        upperView = findViewById(R.id.upperBar);
        modeIdentifier = findViewById(R.id.tv2);
        textView = findViewById(R.id.tv1);
        calendarviewlight = findViewById(R.id.calendar);
        calendarviewdark = findViewById(R.id.calendar2);
        textView.setText(formattedDate);
        sensorManager = (SensorManager) getSystemService(Service.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        getWindow().setStatusBarColor(Color.parseColor("#CD5858"));
        //getWindow().setNavigationBarDividerColor(Color.parseColor("#CD5858"));

        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO)
        {
            upperView.setBackgroundColor(Color.parseColor("#CD5858"));
            modeIdentifier.setText("Calendar");
            modeIdentifier.setTextColor(Color.parseColor("#000000"));
            layout.setBackgroundColor(Color.parseColor("#F4F4F4"));
            textView.setTextColor(Color.parseColor("#000000"));
            calendarviewlight.setVisibility(View.VISIBLE);
            calendarviewdark.setVisibility(View.INVISIBLE);
            getWindow().setStatusBarColor(Color.parseColor("#CD5858"));
        }
        else if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
        {
            getWindow().setStatusBarColor(Color.parseColor("#000000"));
            upperView.setBackgroundColor(Color.parseColor("#000000"));
            modeIdentifier.setText("Calendar");
            modeIdentifier.setTextColor(Color.parseColor("#FFFFFF"));
            layout.setBackgroundColor(Color.parseColor("#1C1C1C"));
            textView.setTextColor(Color.parseColor("#F0EEEE"));
            calendarviewlight.setVisibility(View.INVISIBLE);
            calendarviewdark.setVisibility(View.VISIBLE);
            //getWindow().setNavigationBarDividerColor(Color.parseColor("#3A3A3A"));
        }
    }


    @Override
    protected void onResume()
    {
        super.onResume();
        sensorManager.registerListener(this, sensor, sensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        sensorManager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent event)
    {
        float luxData = event.values[0];

        if(luxData > 20000f)
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {
        // Do something here if sensor accuracy changes.
    }
}