package com.boristul.notebook.service

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class CloudMessagingService : FirebaseMessagingService() {

    companion object {
        private const val FIREBASE_TAG = "firebaseService"
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(FIREBASE_TAG, "Data: ${remoteMessage.data}")
        Log.d(FIREBASE_TAG, "From: ${remoteMessage.from}")
        Log.d(FIREBASE_TAG, "Notification: ${remoteMessage.notification?.body}")
    }

    override fun onNewToken(token: String) {
        Log.d(FIREBASE_TAG, "Refreshed token: $token")
    }
}
