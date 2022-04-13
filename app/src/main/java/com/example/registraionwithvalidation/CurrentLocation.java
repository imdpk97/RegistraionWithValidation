package com.example.registraionwithvalidation;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class CurrentLocation extends AppCompatActivity {
    Button btLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    TextView tvLatitude;
    TextView tvLongitude;
    Location location1;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.currentlocation);
        this.btLocation = (Button) findViewById(R.id.location);
        this.tvLatitude = (TextView) findViewById(R.id.tv_latitude);
        this.tvLongitude = (TextView) findViewById(R.id.tv_longitude);
        this.fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(CurrentLocation.this);
        Toast.makeText(getApplicationContext(), "Click", Toast.LENGTH_SHORT).show();
        if (ActivityCompat.checkSelfPermission(CurrentLocation.this, "android.permission.ACCESS_FINE_LOCATION") == 0 && ActivityCompat.checkSelfPermission(CurrentLocation.this, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
            CurrentLocation.this.getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(CurrentLocation.this, new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"}, 100);
        }

        btLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(getIntent());
            }
        });

    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] + grantResults[1] == 0) {
            getCurrentLocation();
        } else {
            Toast.makeText(getApplicationContext(), "Permission denied.", Toast.LENGTH_LONG).show();
        }
    }

    /* access modifiers changed from: private */
    public void getCurrentLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled("gps") || locationManager.isProviderEnabled("network")) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            this.fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                public void onComplete(Task<Location> task) {
                    Location location = task.getResult();
                    location1 = location;
                    if (location != null) {
                        CurrentLocation.this.tvLatitude.setText(String.valueOf(location.getLatitude()));
                        CurrentLocation.this.tvLongitude.setText(String.valueOf(location.getLongitude()));
                        return;
                    }
                    if (ActivityCompat.checkSelfPermission(CurrentLocation.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(CurrentLocation.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    CurrentLocation.this.fusedLocationProviderClient.requestLocationUpdates(new LocationRequest().setPriority(100).setInterval(10000).setFastestInterval(1000).setNumUpdates(1), new LocationCallback() {
                        public void onLocationResult(LocationResult locationResult) {
                            Location location1 = locationResult.getLastLocation();
                            CurrentLocation.this.tvLatitude.setText(String.valueOf(location1.getLatitude()));
                            CurrentLocation.this.tvLongitude.setText(String.valueOf(location1.getLongitude()));
                        }
                    }, Looper.myLooper());
                }
            });
        } else {
            startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS").setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

    public void mapview(Location currentLocation) {


    }


}
