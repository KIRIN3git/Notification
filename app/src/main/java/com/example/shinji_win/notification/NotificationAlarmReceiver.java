package com.example.shinji_win.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

public class NotificationAlarmReceiver extends BroadcastReceiver {

    public static final String ACTION_NOTIFY_SESSION = "com.example.shinji_win.notification.action.NOTIFY_SESSION";
    public static final String EXTRA_NOTIFICATION_CONTENT = "com.example.shinji_win.notification.NOTIFICATION_CONTENT";

    public static final int NOTIFICATION_ID = 100;

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();

        Log.w( "DEBUG_DATA", "onReceive action = " + action );

        String contentText = intent.getStringExtra(EXTRA_NOTIFICATION_CONTENT);
        notifySession(context, contentText);
    }

    private void notifySession(Context context, String contentText) {

        // プリファランスによる制御
        // ・・・・・

        Intent baseIntent = new Intent(context, MainActivity.class);
        TaskStackBuilder taskBuilder = TaskStackBuilder.create(context).addNextIntent(baseIntent);

        PendingIntent pi = taskBuilder.getPendingIntent(0, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setContentTitle(context.getResources().getString(R.string.app_name))
                .setContentText(contentText)
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
                .setContentIntent(pi)
                .setPriority(Notification.PRIORITY_MAX)
                .setAutoCancel(true);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
