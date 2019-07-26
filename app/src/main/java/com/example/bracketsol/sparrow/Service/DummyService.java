package com.example.bracketsol.sparrow.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.bracketsol.sparrow.Activities.HomeActivity;
import com.example.bracketsol.sparrow.R;

import java.util.Calendar;

public class DummyService extends Service {
    private static final String TAG = "DummyService_test";
    PowerManager.WakeLock wakeLock;
    @Override
    public void onCreate() {
        super.onCreate();
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                "MyApp::MyWakelockTag");
        wakeLock.acquire();
        initNotification();
    }

    /**
     * init notification
     */
    private void initNotification() {
        int id = Calendar.MILLISECOND;
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String CHANNEL_ID = "Sirius";
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, "Hesecurity", NotificationManager.IMPORTANCE_MIN);
            mChannel.setDescription("Running");
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            try {
                mNotificationManager.createNotificationChannel(mChannel);
            } catch (Exception e) {
                Log.i(TAG , "Floating service notification channel error : ");
            }
            startForeground(id, new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_man)
                    .setContentTitle("Beacon Alerts")
                    .setContentText("Ensure security")
                    .setContentIntent(pendingIntent)
                    .setOngoing(true)
                    .setChannelId(CHANNEL_ID)
                    .build());
        } else {
            startForeground(id, new NotificationCompat.Builder(this, "test")
                    .setSmallIcon(R.drawable.ic_man)
                    .setContentTitle("Beacon Alerts")
                    .setContentText("Ensure security")
                    .setContentIntent(pendingIntent)
                    .setPriority(Notification.PRIORITY_MIN)
                    /*.setContentIntent(pendingIntent)*/
                    .build());
        }
    }

    /**
     *
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     *
     * @param intent
     * @return
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * destroy called
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
        stopSelf();
        wakeLock.release();
    }
}