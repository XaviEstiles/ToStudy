package com.example.tostudy.broadcastreciver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.navigation.NavDeepLinkBuilder;

import com.example.tostudy.R;
import com.example.tostudy.ToStudyApplication;

import java.util.Random;

public class TemporizadorObjBroadcastReceiver extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        PendingIntent pendingIntent = new NavDeepLinkBuilder(context)
                .setGraph(R.navigation.nav_graph)
                .setDestination(R.id.objetivoFragment)
                .createPendingIntent();

        Notification.Builder builder = new Notification.Builder(context, ToStudyApplication.IDCHANNEL)
                .setSmallIcon(R.drawable.ic_objetivo)
                .setAutoCancel(true)
                .setContentTitle("Objetivo")
                .setContentText("¿Es la fecha has cumplido el objetivo?")
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(new Random().nextInt(),builder.build());
    }
}
