package com.example.scl_tracker;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TaskCompleted{
    Context context;
    Button post_data,stop_service,send_message;
    LocationManager locationManager;
    double latitude,longitude;
    private boolean flag=false;
    public static final String PHONE = "01712604316";
    public static final String ADMIN_PHONE = "01872796633";
    public static final String UPDATE_URL = "http://103.15.245.78:8023/scl_tracker_api/public/api/update_location";
    public static final String GET_USER_LOCATION_URL = "http://103.15.245.78:8023/scl_tracker_api/public/api/get_location";
    public static final String GET_ALL_USER_LOCATION_URL = "http://103.15.245.78:8023/scl_tracker_api/public/api/user_list";
    public static final String INSERT_USER_URL = "http://103.15.245.78:8023/scl_tracker_api/public/api/insert_user";

    Handler handler = new Handler();
    Runnable timedTask = new Runnable() {
        @Override
        public void run() {
            Log.i("Again Running : ","run");
            sendPost();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        post_data=findViewById(R.id.post_data);
        stop_service=findViewById(R.id.stopService);
        send_message=findViewById(R.id.sendMessage);
        context=this;
        post_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendPost();
                flag=true;
                Log.i("Running : ","running");
            }
        });

        stop_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent serviceIntent = new Intent(MainActivity.this,AppService.class);
                handler.removeCallbacks(timedTask);
                flag=false;
                stopService(serviceIntent);
            }
        });

        send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessageToAdmin();
            }
        });
    }

    private void sendMessageToAdmin() {
        AppConstantLocationFetch appConstantLocationFetch = new AppConstantLocationFetch(this);
        final GetUserInfo params = new GetUserInfo(GET_USER_LOCATION_URL,PHONE);
        appConstantLocationFetch.execute(params);
    }

    private void sendPost(){
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        //System.out.println("ENTER");
        //Log.i("abar dhukse","abar dhukse");
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context,"Failed",Toast.LENGTH_LONG).show();
            System.out.println("Failed");
        }
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    if(flag){
                        String geolocation="";
                        Log.i("ifelse","ifelse");
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        Geocoder geocoder = new Geocoder(context);
                        try {
                            List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                            geolocation = addressList.get(0).getSubLocality() + "," + addressList.get(0).getLocality();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.i("JSN", String.valueOf(latitude));
                        Log.i("JSN", String.valueOf(longitude));
                        Update params = new Update(UPDATE_URL,PHONE,latitude,longitude,geolocation);
                        Intent serviceIntent = new Intent(MainActivity.this,AppService.class);
                        serviceIntent.putExtra("Parcelable",params);
                        //handler.postDelayed(timedTask,10000);
                        startService(serviceIntent);
                    }
                    else{
                        Intent serviceIntent = new Intent(MainActivity.this,AppService.class);
                        handler.removeCallbacks(timedTask);
                        flag=false;
                        stopService(serviceIntent);
                    }

                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            });
        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    if(flag){
                        String geolocation="";
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        Log.i("Json", String.valueOf(latitude));
                        Log.i("Json", String.valueOf(longitude));
                        Geocoder geocoder = new Geocoder(context);
                        try {
                            List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                            geolocation = addressList.get(0).getSubLocality() + "," + addressList.get(0).getLocality();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Update params = new Update(UPDATE_URL,PHONE,latitude,longitude,geolocation);
                        Intent serviceIntent = new Intent(getApplicationContext(),AppService.class);
                        serviceIntent.putExtra("Parcelable",params);
                        //handler.postDelayed(timedTask,10000);
                        startService(serviceIntent);
                    }else{
                        Intent serviceIntent = new Intent(MainActivity.this,AppService.class);
                        handler.removeCallbacks(timedTask);
                        flag=false;
                        stopService(serviceIntent);
                    }

                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            });
        }
        handler.postDelayed(timedTask,10000);
    }


    @Override
    public void onTaskComplete(GetUserLocation getUserLocation) {
        Log.i("JSON","OnTaskCompleted");
        Log.i("PHONE :",getUserLocation.getPhone());

        String messageToSend = "Current Position of User ID "+getUserLocation.getId()+
                "("+getUserLocation.getPhone()+")"+" is "+getUserLocation.getGeolocation()+
                "(Latitude :"+getUserLocation.getLat()+" , Longitude :"+getUserLocation.getLon()+")";
        SmsManager.getDefault().sendTextMessage(ADMIN_PHONE, null, messageToSend, null,null);
    }
}
