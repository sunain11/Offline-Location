package com.example.sunain.offlinelocation;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements LocationListener {
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    Button star, dis;
    TextView stlong, stlat, curlong, curlat, dist;
    Double stlongi, stlati, curlongi, curlati, dista;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stlong = (TextView) findViewById(R.id.textView3);
        stlat = (TextView) findViewById(R.id.textView5);
        curlong = (TextView) findViewById(R.id.textView7);
        curlat = (TextView) findViewById(R.id.textView9);
        dist = (TextView) findViewById(R.id.textView11);
        star = (Button) findViewById(R.id.button2);
        dis = (Button) findViewById(R.id.button3);
        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stlong.setText(curlong.getText());
                stlat.setText(curlat.getText());
                stlongi = Double.parseDouble(stlong.getText().toString());
                stlati = Double.parseDouble(stlat.getText().toString());
            }
        });
        dis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curlongi = Double.parseDouble(curlong.getText().toString());
                curlati = Double.parseDouble(curlat.getText().toString());
                dista = distance(stlati, stlongi, curlati, curlongi);
                dist.setText("" + dista);
            }
        });
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

    }
    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
    @Override
    public void onLocationChanged(Location location) {
        curlong.setText(""+ location.getLongitude());
        curlat.setText(""+location.getLatitude());
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Log.d("Latitude","status");
    }

    @Override
    public void onProviderEnabled(String s) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onProviderDisabled(String s) {
        Log.d("Latitude","disable");
    }
}
