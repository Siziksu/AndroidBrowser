package com.siziksu.browser.ui.common.manager;

import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.siziksu.browser.R;

public class NotificationManager {

    private static final String CHANNEL_1 = "10001";
    private static final int NOTIFICATION_ID = 1001;

    private NotificationManager() {}

    public static void sendSimpleNotification(Context context, String title, PendingIntent pending) {
        String message = "File downloaded";
        Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle().bigText(message);
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_1);
        builder.setSmallIcon(R.drawable.ic_notification)
                .setLargeIcon(icon)
                .setContentTitle(title)
                .setContentText(message)
                .setStyle(style)
                .setAutoCancel(true)
                .setContentIntent(pending)
                .setSound(sound);
        android.app.NotificationManager manager = (android.app.NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = android.app.NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_1, "BROWSER_NOTIFICATION_CHANNEL", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            assert manager != null;
            builder.setChannelId(CHANNEL_1);
            manager.createNotificationChannel(notificationChannel);
        }
        assert manager != null;
        manager.notify(NOTIFICATION_ID, builder.build());
    }
}
