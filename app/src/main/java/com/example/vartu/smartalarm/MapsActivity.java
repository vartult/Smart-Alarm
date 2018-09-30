package com.example.vartu.smartalarm;

import android.Manifest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;

import android.content.Intent;
import android.content.pm.PackageManager;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import android.location.LocationListener;

import android.location.LocationManager;

import android.os.Build;

import android.os.LocaleList;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;

import android.support.v4.app.ActivityCompat;

import android.support.v4.app.FragmentActivity;

import android.os.Bundle;

import android.support.v4.content.ContextCompat;

import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;

import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.OnMapReadyCallback;

import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {


    private GoogleMap mMap;

    LocationManager locationManager;

    LocationListener locationListener;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()

                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);


        //ALARM SET PROCCESS
        final EditText timr=(EditText) findViewById(R.id.time);
        Button submit=(Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int alarmtime= Integer.parseInt(timr.getText().toString())*60;
                Intent i= new Intent(MapsActivity.this,Alarm.class);
                AlarmManager am=(AlarmManager)getSystemService(ALARM_SERVICE);
                PendingIntent pi=PendingIntent.getBroadcast(getApplicationContext(),0,i,0);
                am.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+alarmtime*1000,pi);

            }
        });





        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            Toast.makeText(getApplicationContext(), "NETWORK0", Toast.LENGTH_SHORT).show();
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    mMap.clear();
                    LatLng start=new LatLng(location.getLatitude(),location.getLongitude());
                    Toast.makeText(getApplicationContext(), "NETWORK1", Toast.LENGTH_SHORT).show();
                    Geocoder geocoder=new Geocoder(getApplicationContext());
                    try {

                        List<Address> addresses =geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                        String str = addresses.get(0).getSubLocality()+",";
                        str+=addresses.get(0).getLocality()+ ",";
                        str+=addresses.get(0).getCountryName();
                        mMap.addMarker(new MarkerOptions().position(start).title(str));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(start,17));
                        TextView starting=findViewById(R.id.start);
                        starting.setText(str);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(getApplicationContext(), "NETWORK", Toast.LENGTH_SHORT);

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        }



    }

    @Override

    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;


    }

}