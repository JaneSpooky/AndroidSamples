package com.exsample.androidsamples.common

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.exsample.androidsamples.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import timber.log.Timber

class MyFirebaseMessagingService: FirebaseMessagingService() {

    override fun onNewToken(token: String) {
       Timber.d("token:$token")

    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
//        Timber.d("remoteMessage:$remoteMessage")
//        if (remoteMessage.data.isEmpty())
//            return
//        val json = remoteMessage.data["json"] ?: ""
//
//        val post = Gson().fromJson(json, Post::class.java)
//
//
//        val title = remoteMessage.data["title"] ?: ""
//        val message = remoteMessage.data["message"] ?: ""
//        val type = remoteMessage.data["type"] ?: ""
//        if (type.isEmpty())
//            showNotification(title, message)
//        else
//            when (type) {
//                "sendData" -> sendData()
//                "closeEvent" -> close()
//            }
    }

    private fun showNotification(title: String, message: String) {
        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).notify(0, NotificationCompat.Builder(this, "1")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .build())
    }

    private fun sendData() {
        // TODO:sendData
    }

    private fun close() {
        // TODO:close
    }
}