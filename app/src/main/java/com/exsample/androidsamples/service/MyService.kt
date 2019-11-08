package com.exsample.androidsamples.service

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.exsample.androidsamples.R

class MyService: Service() {

    override fun onBind(intent: Intent?): IBinder? = MyBinder(this)

    fun showNotification() {
        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).notify(0, NotificationCompat.Builder(this, "1")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("タイトル")
            .setContentText("ボディ")
            .setAutoCancel(true)
            .build())
    }

    class MyBinder(val service: MyService) : Binder()
}