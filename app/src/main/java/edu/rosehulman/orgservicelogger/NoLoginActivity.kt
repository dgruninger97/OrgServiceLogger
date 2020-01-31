package edu.rosehulman.orgservicelogger

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import edu.rosehulman.orgservicelogger.data.Notification
import edu.rosehulman.orgservicelogger.home.HomeFragment
import edu.rosehulman.orgservicelogger.notifications.CreateNotificationService
import kotlinx.android.synthetic.main.activity_main.*

class NoLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(activity_main_toolbar)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(
                    Notification.TYPE_REMINDER,
                    "Reminder",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            channel.description = "Reminder description"
            val notificationService =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationService.createNotificationChannel(channel)
        }

        val notificationId = intent.getStringExtra("notification")
        val fragment = HomeFragment()
        if (notificationId != null) {
            fragment.arguments = Bundle().apply {
                putString("notification", notificationId)
            }
        }

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.activity_main_frame, fragment)
        transaction.commit()

        if (notificationId == null) {
            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = CreateNotificationService.createIntent(this, "sample_notification")
            val pendingIntent = PendingIntent.getService(this, 0, intent, 0)
            alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + 2000,
                pendingIntent
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
}
