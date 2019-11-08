package com.exsample.androidsamples.common

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber

class MyFirebaseMessagingService: FirebaseMessagingService() {

    override fun onNewToken(token: String) {
       Timber.d("token:$token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Timber.d("remoteMessage:$remoteMessage")
    }
}