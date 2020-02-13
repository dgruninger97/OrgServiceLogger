package edu.rosehulman.orgservicelogger.notifications

import android.app.IntentService
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.graphics.drawable.toBitmap
import edu.rosehulman.orgservicelogger.NoLoginActivity
import edu.rosehulman.orgservicelogger.R
import edu.rosehulman.orgservicelogger.data.Notification
import edu.rosehulman.orgservicelogger.data.retrieveEvent
import edu.rosehulman.orgservicelogger.data.retrieveNotification

class CreateNotificationService : IntentService("ViewEventService") {
    override fun onHandleIntent(intent: Intent?) {
        if (intent?.action == ACTION_VIEW_EVENT) {
            val notificationId = intent.getStringExtra(EXTRA_NOTIFICATION_ID)
            retrieveNotification(notificationId) {
                showNotification(it)
            }
        }
    }

    private fun showNotification(notification: Notification) {
        notification.getTitle(this) { title ->
            notification.getDescription { description ->
                retrieveEvent(notification.event) { _, series ->
                    val intent = Intent(this, NoLoginActivity::class.java)
                    intent.putExtra("notification", notification.id)
                    val pendingIntent =
                        PendingIntent.getActivity(
                            this,
                            0,
                            intent,
                            PendingIntent.FLAG_UPDATE_CURRENT
                        )

                    val builder = NotificationCompat.Builder(this, notification.type)
                        .setSmallIcon(R.drawable.ic_notification)
                        .setColor(resources.getColor(R.color.colorPrimary))
                        .setContentTitle(title)
                        .setContentText(description)
                        .setStyle(NotificationCompat.BigTextStyle().bigText(description))
                        // TODO: make large icon white when in dark mode
                        .setLargeIcon(resources.getDrawable(notification.getIconRes())!!.toBitmap())
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)

                    if (notification.type == Notification.TYPE_REMINDER) {
                        val directionsIntent = Intent(Intent.ACTION_VIEW)
                        directionsIntent.data =
                            Uri.parse("geo:0,0?q=${series.address.replace(' ', '+')}")
                        val directionsPendingIntent =
                            PendingIntent.getActivity(this, 0, directionsIntent, 0)

                        builder.addAction(
                            android.R.drawable.ic_dialog_map,
                            "Directions",
                            directionsPendingIntent
                        )
                    }

                    val id = notification.id!!.hashCode()
                    NotificationManagerCompat.from(this).notify(id, builder.build())
                }
            }
        }
    }

    companion object {
        private const val ACTION_VIEW_EVENT =
            "edu.rosehulman.orgservicelogger.notifications.action.VIEW_EVENT"

        private const val EXTRA_NOTIFICATION_ID = "notification"

        @JvmStatic
        fun createIntent(context: Context, notificationId: String): Intent {
            return Intent(context, CreateNotificationService::class.java).apply {
                action = ACTION_VIEW_EVENT
                putExtra(EXTRA_NOTIFICATION_ID, notificationId)
            }
        }
    }
}
