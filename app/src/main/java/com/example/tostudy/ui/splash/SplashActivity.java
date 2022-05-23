package com.example.tostudy.ui.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.tostudy.R;
import com.example.tostudy.ui.login.LoginActivity;
import com.google.firebase.FirebaseApp;

public class SplashActivity extends AppCompatActivity {

    private static final long WAIT_TIME = 500;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        FirebaseApp.initializeApp(getApplicationContext());
        prefs = getSharedPreferences("com.example.tostudy.PREFERENCES_FILE_KEY", Context.MODE_PRIVATE);
    }

    @Override
    protected void onStart() {
        prefs.edit().putString("OrdenarEve",prefs.getString("OrdenarEve","Fecha")).apply();
        prefs.edit().putString("OrdenarObj",prefs.getString("OrdenarObj","Fecha")).apply();
        super.onStart();
        new Handler().postDelayed(() -> startLogin(),WAIT_TIME);
    }

    private void startLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();

    }
}