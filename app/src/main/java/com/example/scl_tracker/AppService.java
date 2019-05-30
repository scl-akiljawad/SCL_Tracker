package com.example.scl_tracker;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import static com.example.scl_tracker.App.CHANNEL_ID;

public class AppService extends Service {
    Handler handler = new Handler();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Update update = intent.getParcelableExtra("Parcelable");
        AppConstant app = new AppConstant();
        Log.i("service :","working");
        app.execute(update);
        //Toast.makeText(context,"Success",Toast.LENGTH_LONG).show();
        Intent notificationChannel = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notificationChannel,0);
        Notification notification = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setContentTitle(update.getPhone())
                .setContentText("Location :"+update.getGeo_location())
                .setSmallIcon(R.drawable.ic_android)
                .setContentIntent(pendingIntent)
                .setContentIntent(pendingIntent)
                .build();
        Log.i("SERVICE App:","Notification");
//"Latitude :"+ update.getLatitude() +" Longitude :"+update.getLongitude()+
        startForeground(1,notification);
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Destroy:","Destroy called");

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
