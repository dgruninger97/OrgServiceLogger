package edu.rosehulman.orgservicelogger

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import edu.rosehulman.orgservicelogger.data.Notification
import edu.rosehulman.orgservicelogger.data.retrieveFutureNotifications
import edu.rosehulman.orgservicelogger.home.HomeFragment
import edu.rosehulman.orgservicelogger.notifications.NotificationLauncher
import kotlinx.android.synthetic.main.activity_main.*

class NoLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(activity_main_toolbar)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val reminderChannel =
                NotificationChannel(
                    Notification.TYPE_REMINDER,
                    "Reminder",
                    NotificationManager.IMPORTANCE_HIGH
                )
            reminderChannel.description = "Reminder to attend event"
            val notificationService =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationService.createNotificationChannel(reminderChannel)

            val confirmChannel =
                NotificationChannel(
                    Notification.TYPE_CONFIRM,
                    "Confirm",
                    NotificationManager.IMPORTANCE_HIGH
                )
            confirmChannel.description = "Reminder to confirm event attendance"
            notificationService.createNotificationChannel(confirmChannel)

            val needsReplacementChannel =
                NotificationChannel(
                    Notification.TYPE_NEEDS_REPLACEMENT,
                    "Needs Replacement",
                    NotificationManager.IMPORTANCE_HIGH
                )
            needsReplacementChannel.description =
                "A member of your organization needs a replacement"
            notificationService.createNotificationChannel(needsReplacementChannel)
        }

        val fragment = HomeFragment("sample_person", "soup_kitchen")
        val notificationId = intent.getStringExtra(Constants.CLICKED_NOTIFICATION_KEY)
        if (notificationId != null) {
            fragment.arguments = Bundle().apply {
                putString(Constants.CLICKED_NOTIFICATION_KEY, notificationId)
            }
        }

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.activity_main_frame, fragment)
        transaction.commit()

        if (notificationId == null) {
            retrieveFutureNotifications("sample_person") { notifications: List<Notification> ->
                for (notification in notifications) {
                    NotificationLauncher.launchNotification(this, notification.id!!, notification.time)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
}
