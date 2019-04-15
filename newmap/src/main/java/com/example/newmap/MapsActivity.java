package com.example.newmap;

import android.location.Location;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private final int requstCode = 1000;
    TextView textView;
    private LocationCallback mLocationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_maps);
                StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                        .permitDiskReads()
                        .permitDiskWrites()
                        .permitNetwork().build());

                Button button2 = findViewById(R.id.button2);
                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
            public void onClick(View v) {
                stopLocationUpdates();
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager() .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mLocationCallback = new LocationCallback() {

            @Override
            public void onLocationResult(LocationResult locationResult) {
                if(locationResult !=null) {
                    for(final Location location : locationResult.getLocations()) {
                        final double Latitude =location.getLatitude();
                        final double Longitude=location.getLongitude();
                        LatLng currentLocation =new LatLng(location.getLatitude(), location.getLongitude());
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,15.0f));
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {

                                    GMailSender gMailSender = new GMailSender("sae1013@gmail.com", "sae563365");

                                    //GMailSender.sendMail(제목, 본문내용, 받는사람);

                                    gMailSender.sendMail("현재위치테스트", "위도:"+Latitude+"경도:"+Longitude ,"jmw93@naver.com");



                                } catch (SendFailedException e) {

                                    e.printStackTrace();

                                } catch (MessagingException e) {

                                    e.printStackTrace();
                                } catch (Exception e) {

                                    e.printStackTrace();

                                }
                            }
                        }).start();


                    }
                }
            }
        };


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LocationRequest request = new LocationRequest();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setInterval(10000);
        request.setFastestInterval(5000);

        mFusedLocationClient.requestLocationUpdates(
                request,
                mLocationCallback,
                null);



    }

    private void stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }

}
