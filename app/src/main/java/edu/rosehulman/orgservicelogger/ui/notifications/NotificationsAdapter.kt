package edu.rosehulman.orgservicelogger.ui.notifications

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.rosehulman.orgservicelogger.R

class NotificationsAdapter(private val context: Context) :
    RecyclerView.Adapter<NotificationViewHolder>() {

    enum class Notification {
        Confirm, Reminder, NeedsReplacement
    }

    private val notifications = arrayListOf(
        Notification.Confirm, Notification.Reminder, Notification.NeedsReplacement
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.notification_view, parent, false)
        val holder = NotificationViewHolder(view)
        view.setOnClickListener {
            when (notifications[holder.adapterPosition]) {
                Notification.Confirm -> {
                    Log.d("OSL", "Clicked Confirm")
                }
                Notification.Reminder -> {
                    Log.d("OSL", "Clicked Reminder")
                }
                Notification.NeedsReplacement -> {
                    Log.d("OSL", "Clicked NeedsReplacement")
                }
            }
        }
        return holder
    }

    override fun getItemCount() = notifications.size

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        when (notifications[position]) {
            Notification.Confirm -> {
                holder.icon.setImageResource(R.drawable.ic_notification_confirm)
                holder.title.setText("Confirm Event Attendance")
                holder.description.setText("Ryves Hall 5:30-7:30pm Thursday 12/19/2019")
            }
            Notification.Reminder -> {
                holder.icon.setImageResource(R.drawable.ic_notification_reminder)
                holder.title.setText("Event Reminder")
                holder.description.setText("Ryves Hall 5:30-7:30pm Thursday 12/19/2019")
            }
            Notification.NeedsReplacement -> {
                holder.icon.setImageResource(R.drawable.ic_notification_replacement)
                holder.title.setText("Aden needs a replacement")
                holder.description.setText("Legion Dishes 6:00-9:00m Friday 12/20/2019")
            }
        }
    }
}
