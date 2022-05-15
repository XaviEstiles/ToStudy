package com.example.tostudy.broadcastreciver;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;

public class TemporizadorServiceEve extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Intent intent = new Intent("com.example.temporizador_inten_eve");
        sendBroadcast(intent);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return true;
    }
}