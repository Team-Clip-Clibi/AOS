package com.example.fcm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseMessage : FirebaseMessagingService() {
    companion object {
        var intentProvider: FirebaseIntentProvider? = null
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        message.notification?.let { fcm ->
            val title = fcm.title ?: "OneThing"
            val body = fcm.body ?: "ERROR"
            showNotification(title = title, body = body)
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    private fun showNotification(title: String, body: String) {
        val notificationManger =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(
            BuildConfig.FCM_ID,
            BuildConfig.FCM_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManger.createNotificationChannel(channel)
        val intent = intentProvider?.getFCMIntent(this)
            ?: Intent()
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val notification = Notification.Builder(this , BuildConfig.FCM_ID)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.ic_onething)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        notificationManger.notify(0, notification)
    }
}