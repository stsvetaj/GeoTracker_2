package com.example.xxx.geotracker;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStreamWriter;


public class MainActivity extends Activity  implements LocationListener{

    LocationManager locationManager;

    TextView viewLat;
    TextView viewLong;

    String strLat;
    private View handler;


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

        String strLat = String.format("%f", location.getLatitude());
        String strLong = String.format("%f", location.getLongitude());

        viewLat.setText(strLat);
        viewLong.setText(strLong);

    }

    final int Two_SECONDS = 2000;
    public void scheduleSendData() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                writeTofile();
                handler.postDelayed(this, Two_SECONDS);
            }
        }, Two_SECONDS);
    }

    public void btnstop() {
        handler.removeCallbacks(null);
    }


    private void writeTofile(Location location) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("GPSTracker.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write("Latitude "+location.getLatitude());
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
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
