package com.example.zooui;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

//import androidx.annotation.RequiresApi;
//import androidx.core.app.NotificationCompat;

public class AlarmNotificationService extends IntentService {

    private NotificationManager alarmNotificationManager;

    //Notification ID for Alarm
    public static final int NOTIFICATION_ID = 1;

    public AlarmNotificationService() {
        super("AlarmNotificationService");
    }

    @Override
    public void onHandleIntent(Intent intent) {
        //get edittext custom message value from AlarmActivity
        String notify = intent.getExtras().getString("msg");
        //Send notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            sendNotification(notify);
        }
    }

    //handle notification
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void sendNotification(String msg) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        String NOTIFICATION_CHANNEL_ID = "01";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            @SuppressLint("WrongConstant") NotificationChannel notificationChannel
                    = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_MAX);
            // Configure the notification channel.
            notificationChannel.setDescription("Sample Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        Intent intent = new Intent(this, MapsActivity.class);
        if (msg.equals("Multi-Animal Show")){
            intent.putExtra("KEY_SENDER", "Multi-Animal Show");
        }

        else if (msg.equals("Animal Feeding Session")){
            intent.putExtra("KEY_SENDER", "Animal Feeding Session");
        }

        int id = (int) System.currentTimeMillis(); // Generate a unique id

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(intent);

        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(id,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // PendingIntent contentIntent initialization code...
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, AlarmActivity.class), PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Reminder")
                .setContentIntent(resultPendingIntent)
                .setContentText(msg);

        notificationManager.notify(id, notificationBuilder.build());
    }

}