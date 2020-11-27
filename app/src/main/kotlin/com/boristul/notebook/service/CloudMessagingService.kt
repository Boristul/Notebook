package com.boristul.notebook.service

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class CloudMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d("firebaseService", "Data: ${remoteMessage.data}")
        Log.d("firebaseService", "From: ${remoteMessage.from}")
        Log.d("firebaseService", "Notification: ${remoteMessage.notification?.body}")
    }

    override fun onNewToken(token: String) {
        Log.d("firebaseService", "Refreshed token: $token")
    }
}
