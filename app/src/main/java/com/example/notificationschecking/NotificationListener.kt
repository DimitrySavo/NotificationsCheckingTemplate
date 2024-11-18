package com.example.notificationschecking

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log

class NotificationListener : NotificationListenerService() {
    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)

        sbn?.let {
            val packageName = it.packageName
            val title = it.notification.extras.getString("android.title")
            val text = it.notification.extras.getCharSequence("android.text")

            Log.d("Notification title", "title: $title")
            Log.d("Notification text", "text: $text")
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        super.onNotificationRemoved(sbn)
    }
}