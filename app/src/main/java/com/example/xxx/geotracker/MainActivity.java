package com.example.xxx.geotracker;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStreamWriter;


public class MainActivity extends Activity  implements LocationListener{

    LocationManager locationManager;

    TextView viewLat;
    TextView viewLong;

    private static final String TAG = "myApp";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3, 1, this);

        viewLat = (TextView) findViewById(R.id.viewlatitude);
        viewLong = (TextView) findViewById(R.id.viewlongtitude);

    }


    @Override
    public void onLocationChanged(Location location) {

        final String strLat = String.format("%f", location.getLatitude());
        final String strLong = String.format("%f", location.getLongitude());

        viewLat.setText(strLat);
        viewLong.setText(strLong);


        writeTofile("Latitude" +strLat);
        Log.v(TAG, strLat);

    }

    private static final Handler handler = new Handler();

    private void writeTofile(final String data) {
        final Runnable program = new Runnable() {
            @Override
            public void run() {
                try {
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("sdcard/GPSTracker.txt", Context.MODE_APPEND));
                    outputStreamWriter.write(data);
                    outputStreamWriter.close();
                }
                catch (IOException e) {
                    Log.e("Exception", "File write failed: " + e.toString());
                }
                handler.postDelayed(this, 2000);

            }
        };
        handler.postDelayed(program, 1000);
    }




    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

        Toast.makeText(getBaseContext(), "Gps turned on ", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onProviderDisabled(String provider){

        Toast.makeText(getBaseContext(), "Gps turned off ", Toast.LENGTH_LONG).show();
    }
}
