package com.example.bracketsol.sparrow.MessageActivity;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.bracketsol.sparrow.Activities.HomeActivity;
import com.example.bracketsol.sparrow.MyApp;
import com.example.bracketsol.sparrow.R;
import com.example.bracketsol.sparrow.Utils.Prefs;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import static com.android.volley.VolleyLog.TAG;

public class MessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        showNotifications(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
        Log.i(TAG,"dat:"+remoteMessage.getData());
        Log.i(TAG,"sender_name:"+remoteMessage.getData().get("sender_name"));

        //Displaying data in log
        //It is optional
        Log.e(TAG, "The message is " + remoteMessage.getNotification().getTitle() );
        // Log.e(TAG, "From: " + remoteMessage.getFrom());
        //Log.e(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody().toString());
        Log.e(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        Log.e(TAG, "Notification Message type: " + remoteMessage.getData().get("type"));
        Log.e(TAG, "Notification Message detail: " + remoteMessage.getData().get("detail"));


    }

    public void showNotifications(String title,String message) {
        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.sparrow_logo)
                .setSound(uri)
                .setContentText(message)
                .setContentTitle(title)
                .setAutoCancel(true);
        Intent intent = new Intent(this, HomeActivity.class);
        @SuppressLint("WrongConstant") PendingIntent pi = PendingIntent.getActivity(this, 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
        mBuilder.setContentIntent(pi);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.build());
    }

    @Override
    public void onNewToken(String token) {

        Log.i(TAG, "Refreshed token: " + token);


        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        Prefs.addingUserUDID(MyApp.getContext(), token);
        //sendRegistrationToServer(token);

    }


}
