package com.example.shinji_win.notification;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SinpleNotification();

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeNotification();
            }
        });
    }

    public void SinpleNotification(){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");

// Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, MainActivity.class);

//スタックビルダオブジェクトには、開始アクティビティ用の人工バックスタックが含まれます。
//これにより、アクティビティから後方にナビゲートすると、アプリケーションからホーム画面に移動することが保証されます。
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//インテントのバックスタックを追加します（ただしインテント自体は追加しません）
        stackBuilder.addParentStack(MainActivity.class);
//アクティビティを開始するインテントをスタックの先頭に追加します。
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(1, mBuilder.build());

    }

    public void TimeNotification(){

        //呼び出す日時を設定する
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, 5);	//今から5秒後

        //設定した日時で発行するIntentを生成
        Intent intent = new Intent(MainActivity.this, NotificationAlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);

        //日時と発行するIntentをAlarmManagerにセットします
        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pi);

    }
}
