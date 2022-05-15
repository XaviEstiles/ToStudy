package com.example.tostudy;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.UiModeManager;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;

import com.example.tostudy.broadcastreciver.TemporizadorEveBroadcastReceiver;
import com.example.tostudy.broadcastreciver.TemporizadorObjBroadcastReceiver;
import com.example.tostudy.data.Database;

public class ToStudyApplication extends Application {
    public static final String IDCHANNEL = "345767";
    SharedPreferences prefs;
    private UiModeManager uiModeManager;

    @Override
    public void onCreate() {
        super.onCreate();

        uiModeManager = (UiModeManager) getSystemService(UI_MODE_SERVICE);
        uiModeManager.setNightMode(UiModeManager.MODE_NIGHT_NO);

        Database.create(this);
        createNotificationChannel();
        prefs = this.getSharedPreferences("com.example.tostudy.PREFERENCES_FILE_KEY", Context.MODE_PRIVATE);

        TemporizadorObjBroadcastReceiver temporizadorObjBroadcastReceiver = new TemporizadorObjBroadcastReceiver();
        IntentFilter intentFilterObj = new IntentFilter("com.example.temporizador_inten_obj");
        registerReceiver(temporizadorObjBroadcastReceiver,intentFilterObj);

        TemporizadorEveBroadcastReceiver temporizadorEveBroadcastReceiver = new TemporizadorEveBroadcastReceiver();
        IntentFilter intentFilterEve = new IntentFilter("com.example.temporizador_inten_eve");
        registerReceiver(temporizadorEveBroadcastReceiver,intentFilterEve);
    }
    private void createNotificationChannel() {
        //Se crea una clase Notificationchannel, es necesario que la API sea 26 o mas,
        //porque no se ha incluido en la librería de soporte
        //Si tenemos el minSdk 26 podemos quitar el if

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            //1. Definir la importancia
            int importace = NotificationManager.IMPORTANCE_DEFAULT;
            //2. Definir el nombre del canal
            String nameChannel = getString(R.string.name_channel);
            //3. Se crea el canal
            NotificationChannel notificationChannel = new NotificationChannel(IDCHANNEL, nameChannel, importace);

            //3.1. OPCIONAL, configurar el modo de luces, sonido, vibración
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);

            //4. Añadir este canal a NotificationManager
            getSystemService(NotificationManager.class).createNotificationChannel(notificationChannel);
        }
    }
}
