package com.example.projectx_02tugas05_1918010;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class AlertReceiver extends BroadcastReceiver {
    //ketika menerima broadcast dari alarm, maka akan segera memunculkan notifikasi
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper notificationHelper = new
                NotificationHelper(context);
        NotificationCompat.Builder nb =
                notificationHelper.getChannelNotification();
        notificationHelper.getManager().notify(1, nb.build());
    }
}
