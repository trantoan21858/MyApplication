package com.toantd2000.myapplication;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class NLService extends NotificationListenerService {
    public NLService() {
    }

    private String TAG = this.getClass().getSimpleName();
    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Notification notification =
                new NotificationCompat.Builder(this, "demo NotificationListenerService")
                        .setContentTitle("NotificationListenerService running")
                        .setChannelId("NLS_CHANNEL_ID")
                        // Create the notification to display while the service
                        // is running
                        .build();
        startForeground(2812, notification);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onListenerConnected() {
        super.onListenerConnected();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn, RankingMap rankingMap) {
        super.onNotificationPosted(sbn, rankingMap);
        String title = sbn.getNotification().extras.getString("android.title");
        String text = sbn.getNotification().extras.getString("android.text");
//        Toast.makeText(this, "toantd2000 " + title + " " + text, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.setAction("com.toantd2000.myapplication.UPDATE");
        intent.putExtra("title", title);
        intent.putExtra("text", text);
        sendBroadcast(intent);
    }

    private void createNotificationChannel() {
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel("NLS_CHANNEL_ID", "nls", importance);
            channel.setDescription("description");
            // Register the channel with the system. You can't change the importance
            // or other notification behaviors after this.
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

    }
}